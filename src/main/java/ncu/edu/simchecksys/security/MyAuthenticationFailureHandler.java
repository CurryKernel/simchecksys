package ncu.edu.simchecksys.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author Ke Qiwen
 * @Date 2022/4/1 11:17
 * @Version 1.0
 * @Copyright KernelCurry
 * @description 认证失败的一些处理
 */
@Slf4j
@Component
public class MyAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException e) throws IOException, ServletException {
        Map<String,Object> map = new HashMap<>();
        response.setStatus(401);
        if (e instanceof BadCredentialsException) {
            map.put("status", HttpStatus.UNAUTHORIZED.value());
            map.put("exception",e.getMessage());
            map.put("msg","account and password do not match");
            response.getWriter().write(objectMapper.writeValueAsString(map));
            response.setContentType("text/html;charset=UTF-8");

        } else if (e instanceof UsernameNotFoundException){
            map.put("status",HttpStatus.UNAUTHORIZED.value());
            map.put("exception",e.getMessage());
            map.put("msg","user do not exist");
            response.getWriter().write(objectMapper.writeValueAsString(map));
            response.setContentType("text/html;charset=UTF-8");

        } else if (e instanceof AccountStatusException){
            map.put("status",HttpStatus.FORBIDDEN.value());
            map.put("exception",e.getMessage());
            map.put("msg","account is locked");
            response.getWriter().write(objectMapper.writeValueAsString(map));
            response.setContentType("text/html;charset=UTF-8");

        } else {
            log.error("unset exception:"+e.toString());
            map.put("status",0);
            map.put("exception",e.getMessage());
            map.put("msg","unknown error, contact me");
            response.getWriter().write(objectMapper.writeValueAsString(map));
            response.setContentType("text/html;charset=UTF-8");
        }

    }
}
package ncu.edu.simchecksys.security;

import ncu.edu.simchecksys.constant.DatabaseConstant;
import ncu.edu.simchecksys.entity.User;
import ncu.edu.simchecksys.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author Ke Qiwen
 * @Date 2022/4/1 11:17
 * @Version 1.0
 * @Copyright KernelCurry
 * @description 认证成功的一些处理
 */
@Component
public class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private UserService userService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication)
            throws IOException, ServletException {
        User user = (User) authentication.getPrincipal();
        String role = userService.selectRoleByUsername(user.getUsername()).getName();

        if("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))){//异步请求

            //当前登录用户
            request.getSession().setAttribute("user", user);

            //由前端需要判断是否是重定向
            response.setHeader("REDIRECT", "true");
            /**
             * 根据角色做页面跳转
             */
            if (DatabaseConstant.Role.ROLE_ADMIN.getRole().equals(role)) {
                response.setHeader("CONTENTPATH", "/admin/index");
            } else if (DatabaseConstant.Role.ROLE_TEACHER.getRole().equals(role)) {
                response.setHeader("CONTENTPATH", "/teacher/index");
            } else if (DatabaseConstant.Role.ROLE_STUDENT.getRole().equals(role)) {
                response.setHeader("CONTENTPATH", "/student/index");
            }
        } else{
            /**
             * 否则，则拦截，强制用户登录
             */
            response.sendRedirect("/public/login");
        }
    }
}


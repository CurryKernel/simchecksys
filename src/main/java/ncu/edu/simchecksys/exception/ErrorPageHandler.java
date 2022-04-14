package ncu.edu.simchecksys.exception;

import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

/**
 * @Author Ke Qiwen
 * @Date 2022/3/31 23:51
 * @Version 1.0
 * @Copyright KernelCurry
 */
@Configuration
public class ErrorPageHandler {

    @Bean
    public WebServerFactoryCustomizer webServerFactoryCustomizer(){
        return new WebServerFactoryCustomizer<ConfigurableServletWebServerFactory>() {

            @Override
            public void customize(ConfigurableServletWebServerFactory factory) {
                //401:未授权
                factory.addErrorPages(new ErrorPage(HttpStatus.UNAUTHORIZED,"/public/login"));
                //403:禁止
                factory.addErrorPages(new ErrorPage(HttpStatus.FORBIDDEN,"/public/forbiddenError"));
                //404:页面未找到
                factory.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND,"/public/notFoundError"));
                //500:服务器异常
                factory.addErrorPages(new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR,"/public/serverError"));
                //503:服务不可用
                factory.addErrorPages(new ErrorPage(HttpStatus.SERVICE_UNAVAILABLE,"/public/unavailableError"));
            }
        };
    }
}

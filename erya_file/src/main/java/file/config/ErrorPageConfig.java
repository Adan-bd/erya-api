package file.config;

import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.ErrorPageRegistrar;
import org.springframework.boot.web.server.ErrorPageRegistry;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

@Configuration
public class ErrorPageConfig implements ErrorPageRegistrar {

    @Override
    public void registerErrorPages(ErrorPageRegistry registry) {
        ErrorPage error401Page = new ErrorPage(HttpStatus.UNAUTHORIZED, "/error.html");
        ErrorPage error405Page = new ErrorPage(HttpStatus.METHOD_NOT_ALLOWED, "/error.html");
        ErrorPage error404Page = new ErrorPage(HttpStatus.NOT_FOUND, "/error.html");
        ErrorPage error500Page = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/error.html");
        registry.addErrorPages(error401Page,error405Page, error404Page, error500Page);
    }
//    @Bean
//    public EmbeddedServletContainerCustomizer containerCustomizer() {
//        return new EmbeddedServletContainerCustomizer() {
//            @Override
//            public void customize(ConfigurableEmbeddedServletContainer container) {
//                ErrorPage error401Page = new ErrorPage(HttpStatus.UNAUTHORIZED, "/error.html");
//                ErrorPage error405Page = new ErrorPage(HttpStatus.METHOD_NOT_ALLOWED, "/error.html");
//                ErrorPage error404Page = new ErrorPage(HttpStatus.NOT_FOUND, "/error.html");
//                ErrorPage error500Page = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/error.html");
//
//                container.addErrorPages(error401Page,error405Page, error404Page, error500Page);
//            }
//        };
//    }
}

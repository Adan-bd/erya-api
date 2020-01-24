package spider;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;

//@EnableAsync(proxyTargetClass = true)
@MapperScan("spider.mapper")
@SpringBootApplication
@EnableEurekaClient
@ComponentScan(basePackages = {"common","spider"})
public class SpiderApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpiderApplication.class, args);
    }
}
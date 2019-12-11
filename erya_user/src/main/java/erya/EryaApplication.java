package erya;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@MapperScan("cn.ych.erya.mapper")
@SpringBootApplication
@EnableCaching
public class EryaApplication {
    public static void main(String[] args) {
        SpringApplication.run(EryaApplication.class, args);
    }

}

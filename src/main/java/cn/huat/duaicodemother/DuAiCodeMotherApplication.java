package cn.huat.duaicodemother;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@MapperScan("cn.huat.duaicodemother.mapper")
@EnableAspectJAutoProxy(exposeProxy = true)
public class DuAiCodeMotherApplication {

    public static void main(String[] args) {
        SpringApplication.run(DuAiCodeMotherApplication.class, args);
    }

}

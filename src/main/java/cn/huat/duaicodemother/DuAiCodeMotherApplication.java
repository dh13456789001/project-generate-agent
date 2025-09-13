package cn.huat.duaicodemother;

import dev.langchain4j.community.store.embedding.redis.spring.RedisEmbeddingStoreAutoConfiguration;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication(exclude = {RedisEmbeddingStoreAutoConfiguration.class})
@MapperScan("cn.huat.duaicodemother.mapper")
@EnableAspectJAutoProxy(exposeProxy = true)
public class DuAiCodeMotherApplication {

    public static void main(String[] args) {
        SpringApplication.run(DuAiCodeMotherApplication.class, args);
    }

}

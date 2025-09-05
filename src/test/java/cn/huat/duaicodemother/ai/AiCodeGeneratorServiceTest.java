package cn.huat.duaicodemother.ai;

import cn.huat.duaicodemother.result.HtmlCodeResult;
import cn.huat.duaicodemother.result.MultiFileCodeResult;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class AiCodeGeneratorServiceTest {

    @Resource
    private AiCodeGeneratorService aiCodeGeneratorService;

    @Test
    void generateHtmlCode() {
        HtmlCodeResult result = aiCodeGeneratorService.generateHtmlCode("生成一个简单的HTML页面，包含一个标题和一个段落。不超过20行");
        Assertions.assertNotNull(result);

    }

    @Test
    void generateMultiFileCode() {
        MultiFileCodeResult result = aiCodeGeneratorService.generateMultiFileCode("生成一个简单的HTML页面，包含一个标题和一个段落。不超过50行");
        Assertions.assertNotNull(result);
    }
}
package cn.huat.duaicodemother.langgraph4j.node;

import cn.huat.duaicodemother.langgraph4j.ai.ImageCollectionService;
import cn.huat.duaicodemother.langgraph4j.model.ImageResource;
import cn.huat.duaicodemother.langgraph4j.model.enums.ImageCategoryEnum;
import cn.huat.duaicodemother.langgraph4j.state.WorkflowContext;
import cn.huat.duaicodemother.utils.SpringContextUtil;
import lombok.extern.slf4j.Slf4j;
import org.bsc.langgraph4j.action.AsyncNodeAction;
import org.bsc.langgraph4j.prebuilt.MessagesState;

import javax.swing.*;
import java.util.Arrays;
import java.util.List;

import static org.bsc.langgraph4j.action.AsyncNodeAction.node_async;

@Slf4j
public class ImageCollectorNode {
    public static AsyncNodeAction<MessagesState<String>> create() {
        return node_async(state -> {
            WorkflowContext context = WorkflowContext.getContext(state);
            log.info("执行节点: 图片收集");
            

            String imageListStr = null;
            try {
                String originalPrompt = context.getOriginalPrompt();
                imageListStr = "";
                ImageCollectionService imageCollectionService = SpringContextUtil.getBean(ImageCollectionService.class);
                imageListStr = imageCollectionService.collectImages(originalPrompt);
            } catch (Exception e) {
                log.error("图片收集失败:{}", e.getMessage(), e);
            }

//            // 简单的假数据
//            List<ImageResource> imageList = Arrays.asList(
//                ImageResource.builder()
//                    .category(ImageCategoryEnum.CONTENT)
//                    .description("假数据图片1")
//                    .url("https://www.codefather.cn/logo.png")
//                    .build(),
//                ImageResource.builder()
//                    .category(ImageCategoryEnum.LOGO)
//                    .description("假数据图片2")
//                    .url("https://www.codefather.cn/logo.png")
//                    .build()
//            );
            
            // 更新状态
            context.setCurrentStep("图片收集");
            context.setImageListStr(imageListStr);
//            log.info("图片收集完成，共收集 {} 张图片", imageListStr.size());
            return WorkflowContext.saveContext(context);
        });
    }
}

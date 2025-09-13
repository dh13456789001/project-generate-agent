package cn.huat.duaicodemother.ai;

import cn.huat.duaicodemother.ai.tools.FileWriteTool;
import cn.huat.duaicodemother.config.ReasoningStreamingChatModelConfig;
import cn.huat.duaicodemother.config.RedisChatMemoryStoreConfig;
import cn.huat.duaicodemother.exception.BusinessException;
import cn.huat.duaicodemother.exception.ErrorCode;
import cn.huat.duaicodemother.model.enums.CodeGenTypeEnum;
import cn.huat.duaicodemother.service.ChatHistoryService;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import dev.langchain4j.community.store.memory.chat.redis.RedisChatMemoryStore;
import dev.langchain4j.data.message.ToolExecutionResultMessage;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.chat.StreamingChatModel;
import dev.langchain4j.service.AiServices;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
@Slf4j
public class AiCodeGeneratorServiceFactory {

    @Resource
    private ChatModel chatModel;

    @Resource
    private StreamingChatModel openAiStreamingChatModel;

    @Resource
    private RedisChatMemoryStore redisChatMemoryStore;

    @Resource
    private ChatHistoryService chatHistoryService;

    @Resource
    private StreamingChatModel reasoningStreamingChatModel;

//    @Bean
//    public AiCodeGeneratorService aiCodeGeneratorService() {
//        return AiServices.create(AiCodeGeneratorService.class, chatModel);
//    }
//    @Bean
//    public AiCodeGeneratorService aiCodeGeneratorService() {
//        return AiServices.builder(AiCodeGeneratorService.class)
//                .chatModel(chatModel)
//                .streamingChatModel(streamingChatModel).build();
//    }

    @Bean
    public AiCodeGeneratorService aiCodeGeneratorService() {
        return getAiCodeGeneratorService(0L);
    }



//    public AiCodeGeneratorService getAiCodeGeneratorService(long appId) {
//        MessageWindowChatMemory memoryStore = MessageWindowChatMemory.builder()
//                .chatMemoryStore(redisChatMemoryStore)
//                .id(appId).maxMessages(20).build();
//
//        return AiServices.builder(AiCodeGeneratorService.class)
//                .chatModel(chatModel)
//                .streamingChatModel(streamingChatModel)
//                .chatMemory(memoryStore)
//                .build();
//    }



    /**
     * AI 服务实例缓存
     * 缓存策略：
     * - 最大缓存 1000 个实例
     * - 写入后 30 分钟过期
     * - 访问后 10 分钟过期
     */
    private final Cache<String, AiCodeGeneratorService> serviceCache = Caffeine.newBuilder()
            .maximumSize(1000)
            .expireAfterWrite(Duration.ofMinutes(30))
            .expireAfterAccess(Duration.ofMinutes(10))
            .removalListener((key, value, cause) -> {
                log.debug("AI 服务实例被移除，缓存key: {}, 原因: {}", key, cause);
            })
            .build();

    /**
     * 根据 appId 获取服务
     */
    public AiCodeGeneratorService getAiCodeGeneratorService(long appId) {
        return getAiCodeGeneratorService(appId, CodeGenTypeEnum.HTML);
    }

    /**
     * 根据 appId 获取服务（带缓存）
     */
    public AiCodeGeneratorService getAiCodeGeneratorService(long appId, CodeGenTypeEnum codeGenType) {
        String cacheKey = buildCacheKey(appId, codeGenType);
        return serviceCache.get(cacheKey, (k)->
                createAiCodeGeneratorService(appId, codeGenType)
        );
    }

    /**
     * 创建新的 AI 服务实例
     */
    private AiCodeGeneratorService createAiCodeGeneratorService(long appId, CodeGenTypeEnum codeGenType) {
        log.info("为 appId: {} 创建新的 AI 服务实例", appId);
        // 根据 appId 构建独立的对话记忆
        MessageWindowChatMemory chatMemory = MessageWindowChatMemory
                .builder()
                .id(appId)
                .chatMemoryStore(redisChatMemoryStore)
                .maxMessages(20)
                .build();
        // 从数据库加载对话历史到对话记忆中
        int a = chatHistoryService.loadChatHistoryToMemory(appId, chatMemory, 20);
        log.info("加载对话历史到内存完成，共加载 {} 条记录", a);
        return switch (codeGenType) {
            case VUE_PROJECT ->
                 AiServices.builder(AiCodeGeneratorService.class)
                        .chatModel(chatModel)
                        .streamingChatModel(reasoningStreamingChatModel)
                        .chatMemory(chatMemory)
                        .chatMemoryProvider((memoryId) -> chatMemory)
                        .tools(new FileWriteTool())
                        .hallucinatedToolNameStrategy((toolExecutionRequest)->
                            ToolExecutionResultMessage.from( toolExecutionRequest,"Error：there is no tool called "+toolExecutionRequest.name())
                        )
                        .build();
            case HTML, MULTI_FILE ->
                 AiServices.builder(AiCodeGeneratorService.class)
                        .chatModel(chatModel)
                        .streamingChatModel(openAiStreamingChatModel)
                        .chatMemory(chatMemory)
                        .build();
            default -> throw new BusinessException(ErrorCode.SYSTEM_ERROR, "不支持的代码生成类型" + codeGenType.getValue());
        };

    }

    private String buildCacheKey(long appId, CodeGenTypeEnum codeGenType){
        return appId + "_" + codeGenType.getValue();
    }


}

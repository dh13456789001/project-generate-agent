package cn.huat.duaicodemother.service;

import cn.huat.duaicodemother.model.dto.chathistory.ChatHistoryQueryRequest;
import cn.huat.duaicodemother.model.entity.User;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.core.service.IService;
import cn.huat.duaicodemother.model.entity.ChatHistory;

import java.time.LocalDateTime;

/**
 * 对话历史 服务层。
 *
 * @author <a href="https://github.com/dh13456789001">duhui</a>
 */
public interface ChatHistoryService extends IService<ChatHistory> {


    /**
     * 添加对话消息
     *
     * @param appId       应用ID
     * @param message     对话消息内容
     * @param messageType 对话消息类型
     * @param userId      用户ID
     * @return boolean
     */
    boolean addChatMessage(Long appId, String message, String messageType, Long userId);

    /**
     * 删除应用下的所有对话消息
     *
     * @param appId 应用ID
     * @return boolean
     */
    boolean deleteByAppId(Long appId);

    /**
     * 获取查询包装类
     *
     * @param chatHistoryQueryRequest 对话历史查询QueryWrapper
     * @return QueryWrapper
     */
    QueryWrapper getQueryWrapper(ChatHistoryQueryRequest chatHistoryQueryRequest);

    /**
     * 根据应用ID分页查询对话历史
     *
     * @param appId          应用ID
     * @param pageSize       页面大小
     * @param lastCreateTime 上次创建时间，用于游标查询
     * @param loginUser      登录用户信息
     * @return 对话历史分页数据
     */
    Page<ChatHistory> listAppChatHistoryByPage(Long appId, int pageSize,
                                               LocalDateTime lastCreateTime,
                                               User loginUser);


}

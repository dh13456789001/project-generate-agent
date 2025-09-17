package cn.huat.duaicodemother.service;

import cn.huat.duaicodemother.model.dto.app.AppAddRequest;
import cn.huat.duaicodemother.model.dto.app.AppQueryRequest;
import cn.huat.duaicodemother.model.entity.User;
import cn.huat.duaicodemother.model.vo.AppVO;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.core.service.IService;
import cn.huat.duaicodemother.model.entity.App;
import reactor.core.publisher.Flux;

import java.util.List;

/**
 * 应用 服务层。
 *
 * @author <a href="https://github.com/dh13456789001">duhui</a>
 */
public interface AppService extends IService<App> {

    /**
     * 获取应用封装类
     *
     * @param app
     * @return
     */
    AppVO getAppVO(App app);

    /**
     * 获取应用封装类列表
     *
     * @param appList
     * @return
     */
    List<AppVO> getAppVOList(List<App> appList);


    QueryWrapper getQueryWrapper(AppQueryRequest appQueryRequest);


    /**
     * 聊天生成代码
     * @param appId 应用ID
     * @param message 用户提示词
     * @param loginUser 登录用户
     * @return AI 生成的代码流
     */
    Flux<String> chatToGenCode(Long appId, String message, User loginUser);


    /**
     *
     * @param appId
     * @param loginUser
     * @return
     */
    String deployApp(Long appId, User loginUser);

    void generateAppScreenshotAsync(Long appId, String appUrl);

    Long createApp(AppAddRequest appAddRequest, User loginUser);
}

package cn.huat.duaicodemother.service;

import cn.huat.duaicodemother.model.dto.user.UserQueryRequest;
import cn.huat.duaicodemother.model.vo.LoginUserVO;
import cn.huat.duaicodemother.model.vo.UserVO;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.core.service.IService;
import cn.huat.duaicodemother.model.entity.User;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

/**
 * 用户 服务层。
 *
 * @author <a href="https://github.com/dh13456789001">duhui</a>
 */
public interface UserService extends IService<User> {

    /**
     * 用户注册
     *
     * @param userAccount   用户账户
     * @param userPassword  用户密码
     * @param checkPassword 校验密码
     * @return 新用户 id
     */
    long userRegister(String userAccount, String userPassword, String checkPassword);

    /**
     * 用户密码加密
     *
     * @param userPassword 原密码
     * @return 加密之后的密码
     */
    String getEncryptPassword(String userPassword);


    /**
     * 获取脱敏的已登录用户信息
     *
     * @param user 完整的用户信息
     * @return 脱敏后的用户信息
     */
    LoginUserVO getLoginUserVO(User user);


    /**
     * 用户登录
     *
     * @param userAccount  用户账户
     * @param userPassword 用户密码
     * @param request 请求对象
     * @return 脱敏后的用户信息
     */
    LoginUserVO userLogin(String userAccount, String userPassword, HttpServletRequest request);


    /**
     * 获取当前登录用户
     *
     * @param request 请求对象
     * @return 当前登录的用户信息
     */
    User getLoginUser(HttpServletRequest request);



    /**
     * 用户注销
     *
     * @param request 请求对象
     * @return 是否注销成功
     */
    boolean userLogout(HttpServletRequest request);


    /**
     * 获取脱敏的用户
     *
     * @param user 用户信息
     * @return 脱敏后的用户
     */
    UserVO getUserVO(User user);

    /**
     * 获取脱敏的用户列表
     *
     * @param userList 用户列表
     * @return 脱敏后的用户信息列表
     */
    List<UserVO> getUserVOList(List<User> userList);


    /**
     * 获取条件查询包装类
     *
     * @param userQueryRequest 用户查询请求参数
     * @return 条件查询包装类
     */
    QueryWrapper getQueryWrapper(UserQueryRequest userQueryRequest);
}

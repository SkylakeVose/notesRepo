package cn.piggy.spring6.web;

import cn.piggy.spring6.service.impl.UserServiceImpl;

/**
 * 表示层
 */
public class UserAction {
    UserServiceImpl userServiceImpl = new UserServiceImpl();

    /**
     * 删除用户信息的请求
     */
    public void deleteRequest() {
        userServiceImpl.deleteUser();
    }
}

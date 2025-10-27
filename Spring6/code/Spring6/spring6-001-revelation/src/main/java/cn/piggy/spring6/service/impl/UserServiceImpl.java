package cn.piggy.spring6.service.impl;

import cn.piggy.spring6.dao.impl.UserDaoImplForMySQL;
import cn.piggy.spring6.dao.impl.UserDaoImplForOracle;
import cn.piggy.spring6.service.UserService;

public class UserServiceImpl implements UserService {

//    UserDaoImplForMySQL userDaoImpl = new UserDaoImplForMySQL();
    UserDaoImplForOracle userDaoImpl = new UserDaoImplForOracle();

    @Override
    public void deleteUser() {
        userDaoImpl.deleteById();
    }
}

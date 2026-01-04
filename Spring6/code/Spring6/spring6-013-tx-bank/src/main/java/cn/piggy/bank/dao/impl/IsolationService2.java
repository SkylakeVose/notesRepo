package cn.piggy.bank.dao.impl;

import cn.piggy.bank.dao.AccountDao;
import cn.piggy.bank.pojo.Account;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@Service("i2")
public class IsolationService2 {

    @Resource
    private AccountDao accountDao;

    // 负责insert
//    @Transactional(readOnly = true, timeout = 5)    // 设置事务超时时间为10s
    // 只要发生RuntimeException或者其子类异常的 都回滚
//    @Transactional(rollbackFor = RuntimeException.class)
    @Transactional(noRollbackFor = NullPointerException.class)
    public void save(Account account) throws IOException {
        accountDao.insert(account);
        // 延时
        /*try {
            Thread.sleep(1000 * 10);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }*/

        // 模拟异常
        throw new NullPointerException();
    }
}

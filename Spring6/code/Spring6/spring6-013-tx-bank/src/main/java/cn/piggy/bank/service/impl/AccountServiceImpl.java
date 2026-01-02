package cn.piggy.bank.service.impl;

import cn.piggy.bank.dao.AccountDao;
import cn.piggy.bank.pojo.Account;
import cn.piggy.bank.service.AccountService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("accountService")
public class AccountServiceImpl implements AccountService {

    @Resource(name = "accountDao")
    private AccountDao accountDao;

    // 控制事务，因为要在这个方法中完成转账业务
    @Override
    @Transactional
    public void transfer(String fromActno, String toActno, double money) {
        // 查询转出账户的余额是否充足
        Account fromAct = accountDao.selectByActno(fromActno);
        if(fromAct.getBalance() < money) {
            throw new RuntimeException("余额不足!");
        }
        // 余额充足
        Account toAct = accountDao.selectByActno(toActno);

        // 将内存中两个对象的余额先修改
        fromAct.setBalance(fromAct.getBalance() - money);
        toAct.setBalance(toAct.getBalance() + money);

        // 数据库更新
        int count = accountDao.update(fromAct);

        // 模拟异常
        // int i = 10 / 0;

        count += accountDao.update(toAct);

        if(count != 2) {
            throw new RuntimeException("转账失败，请联系银行.");
        }
    }
}

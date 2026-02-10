package cn.piggy.bank.service.impl;

import cn.piggy.bank.dao.AccountDao;
import cn.piggy.bank.dao.impl.AccountDaoImpl;
import cn.piggy.bank.exceptions.MoneyNotEnoughException;
import cn.piggy.bank.exceptions.TransferException;
import cn.piggy.bank.pojo.Account;
import cn.piggy.bank.service.AccountService;
import cn.piggy.bank.utils.GenerateDaoProxy;
import cn.piggy.bank.utils.SqlSessionUtil;
import org.apache.ibatis.session.SqlSession;

public class AccountServiceImpl implements AccountService {

    // private AccountDao accountDao = new AccountDaoImpl();

    // 使用自己封装的生成工具类
    // private AccountDao accountDao = (AccountDao) GenerateDaoProxy.generate(SqlSessionUtil.openSession(), AccountDao.class);

    // 使用mybatis提供的代理生成实现类实例
    private AccountDao accountDao = SqlSessionUtil.openSession().getMapper(AccountDao.class);

    @Override
    public void transfer(String fromActno, String toActno, double money) throws MoneyNotEnoughException, TransferException {

        // 添加事务控制代码
        SqlSession sqlSession = SqlSessionUtil.openSession();

        // 1. 判断转出账户的余额时候否充足（select)
        Account fromAct = accountDao.selectByActno(fromActno);
        if (fromAct.getBalance() < money) {
            // 2. 如果转出账户余额不足，提示用户
            throw new MoneyNotEnoughException("转出账户余额不足!");
        }
        // 3. 如果转出账户余额充足，更新转出账户余额（update）
        // 先更新内存中对象余额
        Account toAct = accountDao.selectByActno(toActno);
        fromAct.setBalance(fromAct.getBalance() - money);
        toAct.setBalance(toAct.getBalance() + money);
        int count = accountDao.updateByActno(fromAct);

        // 模拟异常
        // int i = 10 / 0;

        // 4. 更新转入账户余额（update）
        count += accountDao.updateByActno(toAct);

        if (count!=2) {
            throw new TransferException("转账异常，未知原因");
        }

        // 提交事务
        sqlSession.commit();
        // 关闭事务
        SqlSessionUtil.close(sqlSession);
    }
}

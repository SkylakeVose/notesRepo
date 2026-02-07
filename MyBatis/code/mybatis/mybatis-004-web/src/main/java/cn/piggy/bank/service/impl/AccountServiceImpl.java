package cn.piggy.bank.service.impl;

import cn.piggy.bank.dao.AccountDao;
import cn.piggy.bank.dao.impl.AccountDaoImpl;
import cn.piggy.bank.exceptions.MoneyNotEnoughException;
import cn.piggy.bank.exceptions.TransferException;
import cn.piggy.bank.pojo.Account;
import cn.piggy.bank.service.AccountService;

public class AccountServiceImpl implements AccountService {

    private AccountDao accountDao = new AccountDaoImpl();

    @Override
    public void transfer(String fromActno, String toActno, double money) throws MoneyNotEnoughException, TransferException {
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
        // 4. 更新转入账户余额（update）
        count += accountDao.updateByActno(toAct);

        if (count!=2) {
            throw new TransferException("转账异常，未知原因");
        }
    }
}

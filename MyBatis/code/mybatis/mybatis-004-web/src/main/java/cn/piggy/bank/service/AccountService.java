package cn.piggy.bank.service;

import cn.piggy.bank.exceptions.MoneyNotEnoughException;
import cn.piggy.bank.exceptions.TransferException;

/**
 * 账户业务类
 */
public interface AccountService {
    /**
     * 账户转账业务
     * @param fromActno 转出账号
     * @param toActno   转入账号
     * @param money 转账金额
     */
    void transfer(String fromActno, String toActno, double money) throws MoneyNotEnoughException, TransferException;
}

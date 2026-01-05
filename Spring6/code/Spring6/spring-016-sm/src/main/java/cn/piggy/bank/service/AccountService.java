package cn.piggy.bank.service;

import cn.piggy.bank.pojo.Account;

import java.util.List;

public interface AccountService {

    /**
     * 开户
     * @param account
     * @return
     */
    int save(Account account);

    /**
     * 销户
     * @param actno
     * @return
     */
    int deleteByActno(String actno);

    /**
     * 修改账户
     * @param account
     * @return
     */
    int modify(Account account);

    /**
     * 查询账户
     * @param actno
     * @return
     */
    Account selectByActno(String actno);

    /**
     * 查询账户
     * @return
     */
    List<Account> getAll();

    /**
     * 转账
     * @param fromActno
     * @param toActno
     * @param money
     */
    void transfer(String fromActno, String toActno, double money);
}

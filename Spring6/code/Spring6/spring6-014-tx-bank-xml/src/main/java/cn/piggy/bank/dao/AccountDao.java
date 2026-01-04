package cn.piggy.bank.dao;

import cn.piggy.bank.pojo.Account;

// 专门负责账户信息的CURD操作
// DAO中只执行SQL语句，没有任何业务逻辑
// 也就是说DAO不和业务挂钩
public interface AccountDao {
    /**
     * 根据账号查询账号信息
     * @param actno
     * @return
     */
    Account selectByActno(String actno);

    /**
     * 更新账号信息
     * @param act
     * @return
     */
    int update(Account act);
}

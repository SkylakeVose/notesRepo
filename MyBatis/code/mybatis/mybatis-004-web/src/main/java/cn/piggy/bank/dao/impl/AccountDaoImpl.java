package cn.piggy.bank.dao.impl;

import cn.piggy.bank.dao.AccountDao;
import cn.piggy.bank.pojo.Account;
import cn.piggy.bank.utils.SqlSessionUtil;
import org.apache.ibatis.session.SqlSession;

public class AccountDaoImpl implements AccountDao {

    @Override
    public Account selectByActno(String actno) {
        SqlSession sqlSession = SqlSessionUtil.openSession();
        Account account = (Account) sqlSession.selectOne("account.selectByActno", actno);
        /*sqlSession.close();*/
        return account;
    }

    @Override
    public int updateByActno(Account act) {
        SqlSession sqlSession = SqlSessionUtil.openSession();
        int count = sqlSession.update("account.updateByActno", act);
        /*sqlSession.commit();
        sqlSession.close();*/
        return count;
    }
}

package cn.piggy.bank.dao.impl;

import cn.piggy.bank.dao.AccountDao;
import cn.piggy.bank.pojo.Account;
import jakarta.annotation.Resource;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository("accountDao")
public class AccountDaoImpl implements AccountDao {

    @Resource(name = "jdbcTemplate")
    private JdbcTemplate jdbcTemplate;

    @Override
    public Account selectByActno(String actno) {
        String sql = "select * from t_act where actno = ?";
        Account account = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Account.class), actno);
        return account;
    }

    @Override
    public int update(Account act) {
        String sql =  "update t_act set balance = ? where actno = ?";
        int count = jdbcTemplate.update(sql, act.getBalance(), act.getActno());
        return count;
    }
}

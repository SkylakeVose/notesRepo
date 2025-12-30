package cn.piggy.spring6.service;

import org.springframework.stereotype.Service;

@Service
public class AccountService {

    // 转账
    public void transfer() {
        System.out.println("银行账户正在完成转账操作...");
    }

    // 取款
    public void withdraw() {
        System.out.println("银行账户正在执行取款操作...");
    }
}

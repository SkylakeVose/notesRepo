package cn.piggy.bank.exceptions;

/**
 * 余额不足异常
 */
public class MoneyNotEnoughException extends Exception {
    public MoneyNotEnoughException() {
    }

    public MoneyNotEnoughException(String message) {
        super(message);
    }
}

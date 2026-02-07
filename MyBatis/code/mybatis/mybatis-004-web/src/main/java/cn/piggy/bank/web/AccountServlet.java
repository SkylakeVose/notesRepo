package cn.piggy.bank.web;

import cn.piggy.bank.exceptions.MoneyNotEnoughException;
import cn.piggy.bank.exceptions.TransferException;
import cn.piggy.bank.service.AccountService;
import cn.piggy.bank.service.impl.AccountServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/transfer")
public class AccountServlet extends HttpServlet {

    // 为了让这个对象在其他方法中也能被使用，因此声明为实例变量
    private AccountService accountService = new AccountServiceImpl();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 获取表单数据
        String fromActno = req.getParameter("fromActno");
        String toActno = req.getParameter("toActno");
        double money = Double.parseDouble(req.getParameter("money"));

        // 调用service的转账方法完成转账（调业务层）
        try {
            accountService.transfer(fromActno, toActno, money);
            // 此处转账成功，调用View完成展示结果
            resp.sendRedirect(req.getContextPath() + "/success.html");
        } catch (MoneyNotEnoughException e) {
            resp.sendRedirect(req.getContextPath() + "/error1.html");
        } catch (TransferException e) {
            resp.sendRedirect(req.getContextPath() + "/error2.html");
        } catch (Exception e) {
            resp.sendRedirect(req.getContextPath() + "/error2.html");
        }
    }
}

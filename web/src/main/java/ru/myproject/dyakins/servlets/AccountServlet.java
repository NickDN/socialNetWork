package ru.myproject.dyakins.servlets;

import ru.myproject.dyakins.account.Account;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AccountServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Account loginAccount = (Account) session.getAttribute("loginAccount");
        req.setAttribute("account", loginAccount);
        req.getRequestDispatcher("/WEB-INF/jsp/account.jsp").forward(req, resp);
    }
}
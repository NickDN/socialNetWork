package ru.myproject.dyakins.servlets;

import ru.myproject.dyakins.account.Account;
import ru.myproject.dyakins.service.AccountService;
import ru.myproject.dyakins.service.AccountServiceImpl;
import ru.myproject.dyakins.util.Password;
import ru.myproject.dyakins.util.exception.NotFoundException;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;

public class LoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        boolean remember = Boolean.parseBoolean(req.getParameter("remember"));
        if (remember) {
            req.setAttribute("remember", "checked");
        }
        String errMsg;
        if (email.isEmpty() || password.isEmpty()) {
            errMsg = "Email or Password field is empty";
            req.setAttribute("errMsg", errMsg);
            req.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(req, resp);
        } else {
            try {
                AccountService service = new AccountServiceImpl();
                Account account = service.get(email);
                if (!Password.checkPasswords(password, account.getPassword())) {
                    throw new NotFoundException("Username/password does not match");
                }
                HttpSession session = req.getSession();
                session.setAttribute("loginAccount", account);
                if (remember) {
                    storeCookies(resp, account);
                }
                resp.sendRedirect(req.getContextPath() + "/account");
            } catch (NotFoundException e) {
                errMsg = "Username/password does not match";
                req.setAttribute("errMsg", errMsg);
                req.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(req, resp);
            }
        }
    }

    private void storeCookies(HttpServletResponse response, Account account) {
        Cookie cookieEmail = new Cookie("emailCookie", account.getEmail());
        cookieEmail.setMaxAge(24 * 60 * 60);
        response.addCookie(cookieEmail);
        Cookie cookiePassword = new Cookie("passwordCookie", account.getPassword());
        cookiePassword.setMaxAge(24 * 60 * 60);
        response.addCookie(cookiePassword);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
      req.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(req, resp);
    }
}
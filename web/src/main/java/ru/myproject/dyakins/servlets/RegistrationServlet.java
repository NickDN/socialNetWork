package ru.myproject.dyakins.servlets;

import ru.myproject.dyakins.account.Account;
import ru.myproject.dyakins.service.AccountService;
import ru.myproject.dyakins.service.AccountServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.bind.ValidationException;
import java.io.IOException;
import java.time.LocalDate;

public class RegistrationServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        AccountService service = new AccountServiceImpl();
        String firstName = req.getParameter("firstName");
        String secondName = req.getParameter("secondName");
        LocalDate dateOfBirth = null;
        if (!req.getParameter("dateOfBirth").isEmpty()) {
            dateOfBirth = LocalDate.parse(req.getParameter("dateOfBirth"));
        }
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String conPassword = req.getParameter("conPassword");

        Account account = new Account
                .AccountBuilder(firstName, secondName, dateOfBirth, email, password)
                .build();
        try {
            if (!password.equals(conPassword)) {
                throw new ValidationException("Password does not match the confirm password");
            }
            Account regAccount = service.create(account);
            HttpSession session = req.getSession();
            session.setAttribute("loginAccount", regAccount);
            resp.sendRedirect(req.getContextPath() + "/account");
        } catch (ValidationException e) {
            req.setAttribute("account", account);
            req.setAttribute("errMsg", e.getMessage());
            req.getRequestDispatcher("/WEB-INF/jsp/registration.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/jsp/registration.jsp").forward(req, resp);
    }
}

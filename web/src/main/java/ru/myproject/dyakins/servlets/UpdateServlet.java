package ru.myproject.dyakins.servlets;

import ru.myproject.dyakins.account.Account;
import ru.myproject.dyakins.account.Gender;
import ru.myproject.dyakins.service.AccountService;
import ru.myproject.dyakins.service.AccountServiceImpl;
import ru.myproject.dyakins.util.Password;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.xml.bind.ValidationException;
import java.io.IOException;
import java.time.LocalDate;

public class UpdateServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        AccountService service = new AccountServiceImpl();
        String action = req.getParameter("action");
        if (action == null) {
            int id = Integer.parseInt(req.getParameter("id"));
            String firstName = req.getParameter("firstName");
            String secondName = req.getParameter("secondName");
            LocalDate dateOfBirth = null;
            if (!req.getParameter("dateOfBirth").isEmpty()) {
                dateOfBirth = LocalDate.parse(req.getParameter("dateOfBirth"));
            }
            String email = req.getParameter("email");
            String password = req.getParameter("password");
            //  String conPassword = req.getParameter("conPassword");
            String middleName = req.getParameter("middleName");
            Gender gender = Gender.valueOf(req.getParameter("gender").toUpperCase());
            String homeAddress = req.getParameter("homeAddress");
            String workAddress = req.getParameter("workAddress");
            String icq = req.getParameter("icq");
            String skype = req.getParameter("skype");
            String additionalInfo = req.getParameter("additionalInfo");
            Account account = new Account
                    .AccountBuilder(firstName, secondName, dateOfBirth, email, password)
                    .setId(id)
                    .setMiddleName(middleName)
                    .setGender(gender)
                    .setHomeAddress(homeAddress)
                    .setWorkAddress(workAddress)
                    .setIcq(icq)
                    .setSkype(skype)
                    .setAdditionalInfo(additionalInfo)
                    .build();
            try {
                service.update(account);
                HttpSession session = req.getSession();
                session.setAttribute("loginAccount", account);
                req.setAttribute("successMsg", "success");
                req.setAttribute("account", account);
                req.getRequestDispatcher("/WEB-INF/jsp/update.jsp").forward(req, resp);
                //resp.sendRedirect(req.getContextPath() + "/update");
            } catch (ValidationException e) {
                req.setAttribute("account", account);
                req.setAttribute("errMsg", e.getMessage());
                req.getRequestDispatcher("/WEB-INF/jsp/update.jsp").forward(req, resp);
            }
        } else if ("password".equals(action)) {
            Account account = null;
            try {
                String password = req.getParameter("password");
                String conPassword = req.getParameter("conPassword");
                if (!password.equals(conPassword)) {
                    throw new ValidationException("Password does not match the confirm password");
                }
                HttpSession session = req.getSession();
                account = (Account) session.getAttribute("loginAccount");
                account.setPassword(Password.getHashPassword(password));
                service.update(account);
                session.setAttribute("loginAccount", account);
                Cookie cookiePassword = new Cookie("passwordCookie", account.getPassword());
                cookiePassword.setMaxAge(24 * 60 * 60);
                resp.addCookie(cookiePassword);
                req.setAttribute("account", account);
                req.setAttribute("successMsg", "success");
                req.getRequestDispatcher("/WEB-INF/jsp/password.jsp").forward(req, resp);
            } catch (ValidationException e) {
                req.setAttribute("account", account);
                req.setAttribute("errMsg", e.getMessage());
                req.getRequestDispatcher("/WEB-INF/jsp/password.jsp").forward(req, resp);
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        HttpSession session = req.getSession();
        Account account = (Account) session.getAttribute("loginAccount");
        req.setAttribute("account", account);
        if ("password".equals(action)){
            req.getRequestDispatcher("/WEB-INF/jsp/password.jsp").forward(req, resp);
        } else {
            req.getRequestDispatcher("/WEB-INF/jsp/update.jsp").forward(req, resp);
        }
    }
}

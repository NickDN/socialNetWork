package ru.myproject.dyakins.servlets;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;

public class LogoutServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        if (session != null) {
            session.setAttribute("loginAccount", null);
            session.invalidate();
        }
        deleteCookies(resp);
        resp.sendRedirect(req.getContextPath() + "/login");
    }

    private void deleteCookies(HttpServletResponse response) {
        Cookie cookieEmail = new Cookie("emailCookie", null);
        cookieEmail.setMaxAge(0);
        Cookie cookiePassword = new Cookie("passwordCookie", null);
        cookiePassword.setMaxAge(0);
        response.addCookie(cookieEmail);
        response.addCookie(cookiePassword);
    }
}
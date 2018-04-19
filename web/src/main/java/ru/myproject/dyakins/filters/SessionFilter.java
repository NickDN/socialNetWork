package ru.myproject.dyakins.filters;

import ru.myproject.dyakins.account.Account;
import ru.myproject.dyakins.service.AccountService;
import ru.myproject.dyakins.service.AccountServiceImpl;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class SessionFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        HttpSession session = req.getSession(false);
        String loginURI = req.getContextPath() + "/login";
        String registrationURI = req.getContextPath() + "/registration";
        boolean isLoginRegistrationURL = req.getRequestURI().equals(loginURI) || req.getRequestURI().equals(registrationURI);
        String accountURI = req.getContextPath() + "/account";
        boolean loggedIn = session != null && session.getAttribute("loginAccount") != null;
        boolean supportFiles = req.getRequestURI().matches(".*(/|css|js|html|woff2|woff|ttf|map|jpg)$");

        if (loggedIn || isLoginRegistrationURL || supportFiles) {
            if (loggedIn && isLoginRegistrationURL) {
                resp.sendRedirect(accountURI);
            } else {
                filterChain.doFilter(req, resp);
            }
        } else {
            String email = null;
            String password = null;
            Cookie[] cookies = req.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals("emailCookie")) {
                        email = cookie.getValue();
                    } else if (cookie.getName().equals("passwordCookie")) {
                        password = cookie.getValue();
                    }
                }
            }
            if (email != null && password != null) {
                AccountService service = new AccountServiceImpl();
                Account account = service.get(email);
                if (!password.equals(account.getPassword())) {
                    resp.sendRedirect(req.getContextPath() + "/login");
                }
                session = req.getSession();
                session.setAttribute("loginAccount", account);
                filterChain.doFilter(req, resp);
            } else {
                resp.sendRedirect(req.getContextPath() + "/login");
            }
        }
    }

    @Override
    public void destroy() {
    }
}
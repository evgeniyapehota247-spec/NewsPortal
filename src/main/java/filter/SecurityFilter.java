package filter;

import bean.User;
import service.ServiceProvider;
import service.UserSecurity;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

//@WebFilter("/*")
public class SecurityFilter implements Filter {

    private UserSecurity userSecurity;

    // Список публичных ресурсов (не требующих аутентификации)
    private static final List<String> PUBLIC_PATHS = Arrays.asList(
            "/",
            "/home",
            "/login",
            "/register",
            "/logout",
            "/css/",
            "/js/",
            "/images/",
            "/fonts/",
            "/resources/",
            "/favicon.ico",
            "/error"
    );

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        userSecurity = ServiceProvider.getInstance().getUserSecurity();
        System.out.println("SecurityFilter инициализирован");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String path = request.getRequestURI().substring(request.getContextPath().length());
        String method = request.getMethod();

        System.out.println("SecurityFilter: " + method + " " + path);

        // Проверяем, публичный ли ресурс
        boolean isPublicPath = PUBLIC_PATHS.stream().anyMatch(path::startsWith);

        // Пропускаем публичные ресурсы и POST запросы к публичным ресурсам
        if (isPublicPath || ("POST".equals(method) && isPublicPath)) {
            System.out.println("SecurityFilter: Пропускаем (публичный ресурс)");
            filterChain.doFilter(request, response);
            return;
        }

        // Для защищенных ресурсов проверяем аутентификацию
        HttpSession session = request.getSession(false);
        User user = null;

        // 1. Проверяем сессию
        if (session != null) {
            user = (User) session.getAttribute("auth");
            if (user != null) {
                System.out.println("SecurityFilter: Пользователь в сессии - " + user.getEmail());
            }
        }

        // 2. Если нет в сессии, проверяем куки
        if (user == null) {
            user = getUserFromCookies(request);
            if (user != null) {
                System.out.println("SecurityFilter: Пользователь по куки - " + user.getEmail());
                // Создаем сессию для пользователя из куки
                session = request.getSession(true);
                session.setAttribute("auth", user);
            }
        }

        // 3. Если пользователь все еще не аутентифицирован - редирект на логин
        if (user == null) {
            System.out.println("SecurityFilter: Пользователь не аутентифицирован, редирект на /login");
            String encodedMessage = java.net.URLEncoder.encode(
                    "Для доступа к этой странице требуется авторизация",
                    "UTF-8"
            );
            response.sendRedirect(request.getContextPath() + "/login?message=" + encodedMessage);
            return;
        }

        // 4. Пользователь аутентифицирован - пропускаем запрос
        System.out.println("SecurityFilter: Пользователь аутентифицирован, пропускаем");
        filterChain.doFilter(request, response);
    }

    /**
     * Получает пользователя из remember-me куки
     */
    private User getUserFromCookies(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return null;
        }

        String userEmail = null;
        String rememberToken = null;

        for (Cookie cookie : cookies) {
            if ("userEmail".equals(cookie.getName())) {
                userEmail = cookie.getValue();
            }
            if ("rememberToken".equals(cookie.getName())) {
                rememberToken = cookie.getValue();
            }
        }

        if (userEmail != null && rememberToken != null) {
            System.out.println("SecurityFilter: Найдены куки для " + userEmail);
            return userSecurity.authenticateByToken(userEmail, rememberToken);
        }

        return null;
    }

    @Override
    public void destroy() {
        System.out.println("SecurityFilter уничтожен");
    }
}
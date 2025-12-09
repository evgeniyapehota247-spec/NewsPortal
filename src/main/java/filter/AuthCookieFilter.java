package filter;

import bean.User;
import service.ServiceProvider;
import service.UserSecurity;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("/*")
public class AuthCookieFilter implements Filter {

    private UserSecurity userSecurity;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        userSecurity = ServiceProvider.getInstance().getUserSecurity();
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        // ⭐ ВАЖНО: Установи кодировку ПЕРВЫМ делом!
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String path = httpRequest.getRequestURI().substring(httpRequest.getContextPath().length());

        // ⭐ ВАЖНО: Пропускаем POST запросы на регистрацию без проверки авторизации
        if (isPublicResource(path) || "POST".equals(httpRequest.getMethod())) {
            chain.doFilter(request, response);
            return;
        }

        if (isPublicResource(path)) {
            chain.doFilter(request, response);//передача запроса дальше следующему фильтру или целевому сервлету
            return;
        }

// Дальше проверка авторизации для защищенных страниц

        HttpSession session = httpRequest.getSession(false);

        // Если в сессии нет пользователя, проверяем куки
        if (session == null || session.getAttribute("auth") == null) {
            User user = getUserFromCookies(httpRequest);
            if (user != null) {
                // Нашли пользователя в куках - создаем сессию
                session = httpRequest.getSession(true);
                session.setAttribute("auth", user);
                chain.doFilter(request, response);
                return;
            } else {
                // КОДИРУЕМ русский текст для URL
                String encodedMessage = java.net.URLEncoder.encode(
                        "Для доступа к этой странице требуется авторизация",
                        "UTF-8"
                );

                // Пользователь не аутентифицирован - перенаправляем на логин
                httpResponse.sendRedirect(httpRequest.getContextPath() +
                        "/login?message="  + encodedMessage);
                return;
            }
        }

        chain.doFilter(request, response);
    }

    /**
     * Получает пользователя из куки
     */
    private User getUserFromCookies(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) return null;

        String userEmail = null;
        String rememberToken = null;

        // Ищем куки с данными аутентификации
        for (Cookie cookie : cookies) {
            if ("userEmail".equals(cookie.getName())) {
                userEmail = cookie.getValue();
            }
            if ("rememberToken".equals(cookie.getName())) {
                rememberToken = cookie.getValue();
            }
        }

        // Если нашли оба куки, проверяем аутентификацию
        if (userEmail != null && rememberToken != null) {
            return userSecurity.authenticateByToken(userEmail, rememberToken);
        }

        return null;
    }

    private boolean isPublicResource(String path) {
        return path.equals("/") ||
                path.equals("/home") ||
                path.equals("/login") ||
                path.equals("/register") ;
    }

    @Override
    public void destroy() {
        // Очистка ресурсов
    }
}
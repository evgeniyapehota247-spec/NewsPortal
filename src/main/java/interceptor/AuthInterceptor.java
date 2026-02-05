package interceptor;

import bean.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import service.AuthService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Autowired
    private AuthService authService;

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
            "/favicon.ico",
            "/error"
    );

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {

        String path = request.getServletPath();
        String method = request.getMethod();

        System.out.println("AuthInterceptor: " + method + " " + path);

        boolean isPublicPath = PUBLIC_PATHS.stream().anyMatch(path::startsWith);

        if (isPublicPath) {
            System.out.println("AuthInterceptor: Пропускаем (публичный ресурс)");
            return true;
        }

        HttpSession session = request.getSession(false);
        User user = null;

        if (session != null) {
            user = (User) session.getAttribute("auth");
            if (user != null) {
                System.out.println("AuthInterceptor: Пользователь в сессии - " + user.getEmail());
            }
        }

        if (user == null) {
            user = getUserFromCookies(request);
            if (user != null) {
                System.out.println("AuthInterceptor: Пользователь по куки - " + user.getEmail());
                // Создаем сессию для пользователя из куки
                session = request.getSession(true);
                session.setAttribute("auth", user);
            }
        }

        if (user == null) {
            System.out.println("AuthInterceptor: Пользователь не аутентифицирован, редирект на /login");
            String encodedMessage = java.net.URLEncoder.encode(
                    "Для доступа к этой странице требуется авторизация",
                    "UTF-8"
            );
            response.sendRedirect(request.getContextPath() + "/login?message=" + encodedMessage);
            return false;
        }

        System.out.println("AuthInterceptor: Пользователь аутентифицирован, пропускаем");
        return true;
    }


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
            System.out.println("AuthInterceptor: Найдены куки для " + userEmail);
            return authService.authenticateByToken(userEmail, rememberToken);
        }

        return null;
    }
}
package interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashSet;
import java.util.Set;

@Component
public class NotFoundInterceptor implements HandlerInterceptor {

    private final Set<String> existingPaths = new HashSet<>();

    public NotFoundInterceptor() {
        existingPaths.add("/");
        existingPaths.add("/home");
        existingPaths.add("/allNews");
        existingPaths.add("/login");
        existingPaths.add("/register");
        existingPaths.add("/logout");
        existingPaths.add("/userHome");
        existingPaths.add("/createNews");
        existingPaths.add("/myNews");
        existingPaths.add("/viewNews");
        existingPaths.add("/deleteNews");

        System.out.println("===== NotFoundInterceptor ИНИЦИАЛИЗИРОВАН =====");
        System.out.println("Зарегистрированные пути: " + existingPaths);
    }

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {

        String path = request.getServletPath();

        // Пропускаем статические ресурсы
        if (path.startsWith("/css/") || path.startsWith("/js/") ||
                path.startsWith("/images/") || path.startsWith("/fonts/")) {
            return true;
        }

        if (path.startsWith("/WEB-INF/")) {
            return true;
        }

        boolean pathExists = existingPaths.contains(path) ||
                path.matches("/news/\\d+") ||
                path.matches("/editNews/\\d+");

        if (!pathExists) {
            System.out.println("NotFoundInterceptor: путь НЕ существует: " + path);

            HttpSession session = request.getSession(false);
            boolean isAuthenticated = (session != null && session.getAttribute("auth") != null);

            if (isAuthenticated) {
                System.out.println("NotFoundInterceptor: пользователь авторизован -> на /userHome");
                response.sendRedirect(request.getContextPath() + "/userHome");
            } else {
                System.out.println("NotFoundInterceptor: пользователь НЕ авторизован -> на /home");
                response.sendRedirect(request.getContextPath() + "/home");
            }

            return false;
        }

        System.out.println("NotFoundInterceptor: путь существует, пропускаем: " + path);
        return true;
    }
}
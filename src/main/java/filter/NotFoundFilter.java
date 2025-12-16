package filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class NotFoundFilter implements Filter {

    private Set<String> existingPaths = new HashSet<>();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Добавьте ВСЕ ваши реальные сервлеты и пути JSP
        existingPaths.add("/");
        existingPaths.add("/home");
        existingPaths.add("/allNews") ;  // Добавляем наш новый путь
        existingPaths.add("/login");
        existingPaths.add("/register");
        existingPaths.add("/logout");
        existingPaths.add("/userHome");

        System.out.println("===== NotFoundFilter ИНИЦИАЛИЗИРОВАН =====");
        System.out.println("Зарегистрированные пути: " + existingPaths);
        System.out.println("===== ============================= =====");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String path = httpRequest.getServletPath();

        System.out.println("NotFoundFilter проверяет: " + path);

        // Пропускаем статические ресурсы
        if (path.startsWith("/css/") || path.startsWith("/js/") || path.startsWith("/images/")) {
            System.out.println("NotFoundFilter: пропускаем статический ресурс");
            chain.doFilter(request, response);
            return;
        }

        // Если это JSP в WEB-INF - пропускаем
        if (path.startsWith("/WEB-INF/")) {
            System.out.println("NotFoundFilter: пропускаем WEB-INF ресурс");
            chain.doFilter(request, response);
            return;
        }

        // Проверяем существование пути
        boolean pathExists = existingPaths.contains(path) ||
                path.matches("/news/\\d+") ||
                path.matches("/editNews/\\d+");

        if (pathExists) {
            System.out.println("NotFoundFilter: путь существует, пропускаем");
            chain.doFilter(request, response);
        } else {
            System.out.println("NotFoundFilter: путь НЕ существует: " + path);

            // Проверяем авторизацию
            HttpSession session = httpRequest.getSession(false);
            boolean isAuthenticated = (session != null && session.getAttribute("auth") != null);

            if (isAuthenticated) {
                System.out.println("NotFoundFilter: пользователь авторизован -> на /userHome");
                HttpServletResponse httpResponse = (HttpServletResponse) response;
                httpResponse.sendRedirect(httpRequest.getContextPath() + "/userHome");
            } else {
                System.out.println("NotFoundFilter: пользователь НЕ авторизован -> на /home");
                HttpServletResponse httpResponse = (HttpServletResponse) response;
                httpResponse.sendRedirect(httpRequest.getContextPath() + "/home");
            }
        }
    }

    @Override
    public void destroy() {
        System.out.println("NotFoundFilter уничтожен");
    }
}
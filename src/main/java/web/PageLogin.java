package web;

import bean.User;
import service.ServiceException;
import service.ServiceProvider;
import service.UserSecurity;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/login")
public class PageLogin extends HttpServlet {

    private final UserSecurity userSecurity = ServiceProvider.getInstance().getUserSecurity();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Проверяем, не аутентифицирован ли пользователь через куки
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("auth") != null) {
            response.sendRedirect(request.getContextPath() + "/userHome");
            return;
        }

        request.setAttribute("pageTitle", "Новости Беларуси - Вход");
        request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String remember = request.getParameter("remember");

        try {
            User user = userSecurity.signIn(email, password);

            if (user == null) {
                response.sendRedirect(request.getContextPath() + "/login?authError=true");
                return;
            }

            HttpSession session = request.getSession(true);//"дай мне сессию, если она есть, иначе создай новую" (по умолчанию)
            session.setAttribute("auth", user);

            // Если выбрано "Запомнить меня", создаем куки
            if ("on".equals(remember)) { //"on" - стандартное значение, которое браузер отправляет для отмеченных чекбоксов
                String rememberToken = userSecurity.generateRememberToken(user);

                // Создаем куки на 30 дней
                Cookie emailCookie = new Cookie("userEmail", user.getEmail());
                emailCookie.setMaxAge(30 * 24 * 60 * 60); // 30 дней
                emailCookie.setPath("/");//куки доступна для всех путей на домене
                emailCookie.setHttpOnly(true);//Защита от XSS-атак - куки недоступна из JavaScript

                Cookie tokenCookie = new Cookie("rememberToken", rememberToken);
                tokenCookie.setMaxAge(30 * 24 * 60 * 60); // 30 дней
                tokenCookie.setPath("/");
                tokenCookie.setHttpOnly(true);

                response.addCookie(emailCookie);//Отправка куки в браузер
                response.addCookie(tokenCookie);
            }

            response.sendRedirect(request.getContextPath() + "/userHome");

        } catch (ServiceException e) {
            response.sendRedirect("error.jsp");
        }
    }
}
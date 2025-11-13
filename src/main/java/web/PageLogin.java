package web;

import bean.User;
import service.ServiceProvider;
import service.UserSecurity;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/login")
public class PageLogin extends HttpServlet {

    private UserSecurity userSecurity = ServiceProvider.getInstance().getUserSecurity();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // ПРОСТО показываем форму входа, БЕЗ ошибки
        request.setAttribute("pageTitle", "Новости Беларуси - Вход");
        request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        User user = userSecurity.signIn(email, password);

        if (user == null) {
            // Устанавливаем ошибку ТОЛЬКО при неудачной попытке входа
            request.setAttribute("authError", "true");

            // Возвращаем на страницу логина с сообщением об ошибке
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/login.jsp");
            dispatcher.forward(request, response);
            System.out.println("ok!");
            return;

        }
        // Сохраняем в сессию
        request.getSession().setAttribute("user", user);

        // Меняем только эту строку:
        response.sendRedirect(request.getContextPath() + "/userHome");

    }
}
package web;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "PageHome", value = {"","/", "/home"})//URL-адреса, которые обрабатывает сервлет
public class PageHome extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Устанавливаем атрибуты для JSP (можно передавать данные)
        request.setAttribute("pageTitle", "Новости Беларуси");
        request.setAttribute("welcomeMessage", "Добро пожаловать на наш портал!");

        // Перенаправляем на JSP в папке WEB-INF/jsp
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/main.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Для POST запросов (формы, логин и т.д.)
        doGet(request, response);
    }
}
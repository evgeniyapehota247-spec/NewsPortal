package web;

import bean.News;
import service.NewsService;
import service.ServiceException;
import service.ServiceProvider;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "PageHome", value = {"", "/", "/home"})//URL-адреса, которые обрабатывает сервлет
public class PageHome extends HttpServlet {

    private final NewsService newsService = ServiceProvider.getInstance().getNewsService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Устанавливаем атрибуты для JSP (можно передавать данные)
        request.setAttribute("pageTitle", "Новости Беларуси");
        request.setAttribute("welcomeMessage", "Добро пожаловать на наш портал!");

        List<News> news;
        try {

            news = newsService.takeTopNews(3);
            request.setAttribute("topNews", news);

            // Перенаправляем на JSP в папке WEB-INF/jsp
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/main.jsp");
            dispatcher.forward(request, response);

        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Для POST запросов (формы, логин и т.д.)
        doGet(request, response);
    }
}
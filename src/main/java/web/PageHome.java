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

@WebServlet(name = "PageHome", value = {"", "/", "/home"})
public class PageHome extends HttpServlet {

    private final NewsService newsService = ServiceProvider.getInstance().getNewsService();
    private static final int PAGE_SIZE = 3; // 3 новостей на главной странице

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setAttribute("pageTitle", "Новости Беларуси");
        request.setAttribute("welcomeMessage", "Добро пожаловать на наш портал!");

        try {
            // Получаем номер страницы из параметра
            String pageParam = request.getParameter("page");
            int currentPage = 1;

            if (pageParam != null && !pageParam.isEmpty()) {
                try {
                    currentPage = Integer.parseInt(pageParam);
                    if (currentPage < 1) currentPage = 1;
                } catch (NumberFormatException e) {
                    currentPage = 1;
                }
            }

            // Получаем новости для текущей страницы
            List<News> news = newsService.findAllNews(currentPage, PAGE_SIZE);
            int totalPages = newsService.getTotalPages(PAGE_SIZE);
            int totalNewsCount = newsService.getTotalNewsCount();

            // Проверяем, что текущая страница не больше общего количества
            if (currentPage > totalPages && totalPages > 0) {
                currentPage = totalPages;
                news = newsService.findAllNews(currentPage, PAGE_SIZE);
            }

            // Устанавливаем атрибуты для пагинации
            request.setAttribute("topNews", news);
            request.setAttribute("currentPage", currentPage);
            request.setAttribute("totalPages", totalPages);
            request.setAttribute("pageSize", PAGE_SIZE);
            request.setAttribute("totalNewsCount", totalNewsCount);

            // Вычисляем диапазон отображаемых новостей
            int startNews = (currentPage - 1) * PAGE_SIZE + 1;
            int endNews = Math.min(currentPage * PAGE_SIZE, totalNewsCount);
            request.setAttribute("startNews", startNews);
            request.setAttribute("endNews", endNews);

            // Перенаправляем на JSP
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/main.jsp");
            dispatcher.forward(request, response);

        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
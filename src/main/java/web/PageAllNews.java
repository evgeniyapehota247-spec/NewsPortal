// PageAllNews.java
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

@WebServlet(name = "PageAllNews", value = "/allNews")
public class PageAllNews extends HttpServlet {

    private final NewsService newsService = ServiceProvider.getInstance().getNewsService();
    private static final int PAGE_SIZE = 10; // Новостей на странице

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

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

            // Проверяем, что текущая страница не больше общего количества
            if (currentPage > totalPages && totalPages > 0) {
                currentPage = totalPages;
                news = newsService.findAllNews(currentPage, PAGE_SIZE);
            }

            // Устанавливаем атрибуты
            request.setAttribute("newsList", news);
            request.setAttribute("currentPage", currentPage);
            request.setAttribute("totalPages", totalPages);
            request.setAttribute("pageSize", PAGE_SIZE);

            // Перенаправляем на JSP
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/allNews.jsp");
            dispatcher.forward(request, response);

        } catch (ServiceException e) {
            throw new ServletException("Error loading news", e);
        }
    }
}
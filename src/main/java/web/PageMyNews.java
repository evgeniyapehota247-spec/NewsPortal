package web;

import bean.News;
import bean.User;
import service.NewsService;
import service.ServiceException;
import service.ServiceProvider;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet(name = "PageMyNews", value = "/myNews")
public class PageMyNews extends HttpServlet {

    private final NewsService newsService = ServiceProvider.getInstance().getNewsService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("auth") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        User user = (User) session.getAttribute("auth");

        try {
            // Получаем новости только этого пользователя
            List<News> userNews = newsService.findByAuthorId(user.getId());

            request.setAttribute("myNews", userNews);

            // Сообщения
            String success = request.getParameter("success");
            if (success != null) {
                request.setAttribute("success", success);
            }

            String error = request.getParameter("error");
            if (error != null) {
                request.setAttribute("error", error);
            }

            // Фильтр по статусу
            String statusFilter = request.getParameter("status");
            if (statusFilter != null && !statusFilter.isEmpty()) {
                try {
                    int statusId = Integer.parseInt(statusFilter);
                    List<News> filteredNews = userNews.stream()
                            .filter(news -> news.getNews_status_id() == statusId)
                            .collect(Collectors.toList());
                    request.setAttribute("myNews", filteredNews);
                    request.setAttribute("currentStatus", statusId);
                } catch (NumberFormatException e) {
                }
            }

            // Статистика
            long publishedCount = userNews.stream()
                    .filter(n -> n.getNews_status_id() == 2)
                    .count();
            long draftCount = userNews.stream()
                    .filter(n -> n.getNews_status_id() == 1)
                    .count();

            request.setAttribute("publishedCount", publishedCount);
            request.setAttribute("draftCount", draftCount);
            request.setAttribute("totalCount", userNews.size());

            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/myNews.jsp");
            dispatcher.forward(request, response);

        } catch (ServiceException e) {
            throw new ServletException("Error loading news", e);
        }
    }
}
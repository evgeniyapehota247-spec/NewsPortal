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

@WebServlet(name = "PageViewNews", value = "/viewNews")
public class PageViewNews extends HttpServlet {

    private final NewsService newsService = ServiceProvider.getInstance().getNewsService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        String idParam = request.getParameter("id");

        if (idParam == null || idParam.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/home");
            return;
        }

        try {
            int id = Integer.parseInt(idParam);
            News news = newsService.getById(id);

            if (news == null) {
                response.sendRedirect(request.getContextPath() + "/home?error=News not found");
                return;
            }

            // Проверяем права доступа
            boolean hasAccess = false;

            // Если новость опубликована - доступна всем
            if (news.getNews_status_id() == 2) {
                hasAccess = true;
            }
            // Если не опубликована, проверяем авторизацию и права
            else if (session != null && session.getAttribute("auth") != null) {
                User user = (User) session.getAttribute("auth");
                // Автор может просматривать свои черновики
                if (news.getAuthor_id() == user.getId()) {
                    hasAccess = true;
                }
                // Админ может просматривать все
                if (user.getRoleId() == 2) { // 2 - админ
                    hasAccess = true;
                }
            }

            if (!hasAccess) {
                response.sendRedirect(request.getContextPath() + "/home?error=You don't have permission to view this news");
                return;
            }

            request.setAttribute("news", news);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/viewNews.jsp");
            dispatcher.forward(request, response);

        } catch (NumberFormatException | ServiceException e) {
            response.sendRedirect(request.getContextPath() + "/home?error=Error loading news");
        }
    }
}
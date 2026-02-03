package web;

import bean.User;
import service.NewsService;
import service.ServiceException;
import service.ServiceProvider;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "PageNewsDelete", value = "/deleteNews")
public class PageNewsDelete extends HttpServlet {

    private final NewsService newsService = ServiceProvider.getInstance().getNewsService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("auth") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        User user = (User) session.getAttribute("auth");
        String idParam = request.getParameter("id");

        if (idParam == null || idParam.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/myNews?error=Invalid news ID");
            return;
        }

        try {
            int newsId = Integer.parseInt(idParam);

            // Можно добавить проверку, что новость принадлежит пользователю
            // или что пользователь - администратор

            newsService.delete(newsId);
            response.sendRedirect(request.getContextPath() + "/myNews?success=News deleted successfully");

        } catch (NumberFormatException | ServiceException e) {
            response.sendRedirect(request.getContextPath() + "/myNews?error=Error deleting news");
        }
    }
}
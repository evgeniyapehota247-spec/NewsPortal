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
import java.time.LocalDateTime;

@WebServlet(name = "PageCreateNews", value = "/createNews")
public class PageCreateNews extends HttpServlet {


    private final NewsService newsService = ServiceProvider.getInstance().getNewsService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("auth") == null) {
            response.sendRedirect(request.getContextPath() + "/login?message=Please login first");
            return;
        }

        // Проверяем, редактируем ли существующую новость
        String editId = request.getParameter("edit");
        if (editId != null && !editId.isEmpty()) {
            try {
                int id = Integer.parseInt(editId);
                News news = newsService.getById(id);

                // Проверяем права доступа
                User user = (User) session.getAttribute("auth");
                if (news.getAuthor_id() != user.getId() && user.getRoleId() != 2) { // 2 - админ
                    response.sendRedirect(request.getContextPath() + "/myNews?error=No permission");
                    return;
                }

                request.setAttribute("news", news);
                request.setAttribute("isEdit", true);
            } catch (ServiceException | NumberFormatException e) {
                // Продолжаем как создание новой новости
            }
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/createNews.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("auth") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        User user = (User) session.getAttribute("auth");

        try {
            String idParam = request.getParameter("id");
            String title = request.getParameter("title");
            String brief = request.getParameter("brief");
            String content = request.getParameter("content");
            String status = request.getParameter("status");
            String publishDate = request.getParameter("publish_date");

            // Валидация
            if (title == null || title.trim().isEmpty() ||
                    brief == null || brief.trim().isEmpty()) {

                request.setAttribute("error", "Заголовок и краткое описание обязательны");
                request.setAttribute("title", title);
                request.setAttribute("brief", brief);
                request.setAttribute("content", content);

                RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/createNews.jsp");
                dispatcher.forward(request, response);
                return;
            }

            News news;
            boolean isEdit = false;

            if (idParam != null && !idParam.isEmpty()) {
                // Редактирование существующей новости
                int id = Integer.parseInt(idParam);
                news = newsService.getById(id);

                // Проверяем права доступа
                if (news.getAuthor_id() != user.getId() && user.getRoleId() != 2) {
                    response.sendRedirect(request.getContextPath() + "/myNews?error=No permission");
                    return;
                }

                isEdit = true;
            } else {
                // Создание новой новости
                news = new News();
                news.setAuthor_id(user.getId());
//                news.setAuthor_name(user.getFirstname() + " " + user.getLastname()); // Добавьте эту строку
                news.setCreated_at(LocalDateTime.now());
            }

            // Заполняем данные
            news.setTitle(title.trim());
            news.setBrief(brief.trim());
            news.setContent_path(content != null ? content.trim() : "");

            // Статус: 1 - черновик, 2 - опубликовано
            int statusId = (status != null && status.equals("2")) ? 2 : 1;
            news.setNews_status_id(statusId);

            // Дата публикации
            if (publishDate != null && !publishDate.isEmpty()) {
                LocalDateTime publishDateTime = LocalDateTime.parse(publishDate + "T00:00:00");
                news.setPublish_date(publishDateTime);
            } else if (statusId == 2) {
                // Если публикуем сейчас, устанавливаем текущую дату
                news.setPublish_date(LocalDateTime.now());
            }

            news.setUpdated_at(LocalDateTime.now());

            // Сохраняем или обновляем
            if (isEdit) {
                newsService.update(news);
                response.sendRedirect(request.getContextPath() + "/myNews?success=News updated successfully");
            } else {
                newsService.create(news);
                response.sendRedirect(request.getContextPath() + "/myNews?success=News created successfully");
            }

        } catch (ServiceException e) {
            throw new ServletException("Error saving news", e);
        } catch (Exception e) {
            request.setAttribute("error", "Произошла ошибка: " + e.getMessage());
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/createNews.jsp");
            dispatcher.forward(request, response);
        }
    }
}
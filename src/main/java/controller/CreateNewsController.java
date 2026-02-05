package controller;

import bean.News;
import bean.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import service.NewsService;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
@RequestMapping("/createNews")
public class CreateNewsController {

    @Autowired
    private NewsService newsService;

    private static final DateTimeFormatter DATE_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @GetMapping
    public String showForm(
            @RequestParam(required = false) Integer edit,
            HttpSession session,
            Model model) {

        User user = (User) session.getAttribute("auth");
        if (user == null) {
            return "redirect:/login?message=Please+login+first";
        }

        // Редактирование существующей новости
        if (edit != null) {
            try {
                News news = newsService.findById(edit);

                if (news == null) {
                    return "redirect:/myNews?error=News+not+found";
                }

                // Проверяем права доступа
                if (news.getAuthor_id() != user.getId() && user.getRoleId() != 2) {
                    return "redirect:/myNews?error=No+permission+to+edit+this+news";
                }

                if (news.getPublish_date() != null) {
                    String formattedDate = news.getPublish_date().format(DATE_FORMATTER);
                    model.addAttribute("formattedPublishDate", formattedDate);
                }

                model.addAttribute("news", news);
                model.addAttribute("isEdit", true);

            } catch (Exception e) {
                return "redirect:/myNews?error=Error+loading+news+for+edit";
            }
        }

        return "createNews";
    }

    @PostMapping
    public String saveNews(
            @RequestParam(required = false) Integer id,
            @RequestParam String title,
            @RequestParam String brief,
            @RequestParam(required = false) String content_path,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String publish_date,
            HttpSession session,
            Model model) {

        User user = (User) session.getAttribute("auth");
        if (user == null) {
            return "redirect:/login";
        }

        try {
            // Валидация
            if (title == null || title.trim().isEmpty() ||
                    brief == null || brief.trim().isEmpty()) {

                model.addAttribute("error", "Заголовок и краткое описание обязательны");
                model.addAttribute("title", title);
                model.addAttribute("brief", brief);
                model.addAttribute("content_path", content_path);
                model.addAttribute("publish_date", publish_date);
                model.addAttribute("status", status);

                return "createNews";
            }

            News news;
            boolean isEdit = false;

            if (id != null) {

                news = newsService.findById(id);

                if (news == null) {
                    return "redirect:/myNews?error=News+not+found";
                }

                if (news.getAuthor_id() != user.getId() && user.getRoleId() != 2) {
                    return "redirect:/myNews?error=No+permission+to+edit+this+news";
                }

                isEdit = true;
            } else {

                news = new News();
                news.setAuthor_id(user.getId());
                news.setCreated_at(LocalDateTime.now());
            }

            news.setTitle(title.trim());
            news.setBrief(brief.trim());
            news.setContent_path(content_path != null ? content_path.trim() : "");

            int statusId = (status != null && status.equals("2")) ? 2 : 1;
            news.setNews_status_id(statusId);

            LocalDateTime publishDate = null;
            if (publish_date != null && !publish_date.isEmpty()) {
                try {
                    publishDate = LocalDateTime.parse(publish_date + "T00:00:00");
                } catch (Exception e) {
                    publishDate = LocalDateTime.now();
                }
            } else if (statusId == 2) {
                publishDate = LocalDateTime.now();
            }
            news.setPublish_date(publishDate);

            news.setUpdated_at(LocalDateTime.now());

            if (isEdit) {
                newsService.update(news);
                return "redirect:/myNews?success=News+updated+successfully";
            } else {
                newsService.save(news);
                return "redirect:/myNews?success=News+created+successfully";
            }

        } catch (Exception e) {
            model.addAttribute("error", "Произошла ошибка: " + e.getMessage());
            model.addAttribute("title", title);
            model.addAttribute("brief", brief);
            model.addAttribute("content", content_path);
            model.addAttribute("publish_date", publish_date);
            model.addAttribute("status", status);
            return "createNews";
        }
    }
}
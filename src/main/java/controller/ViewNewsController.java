package controller;

import bean.News;
import bean.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import service.NewsService;

import javax.servlet.http.HttpSession;


@Controller
public class ViewNewsController {

    private final NewsService newsService;

    @Autowired
    public ViewNewsController(NewsService newsService) {
        this.newsService = newsService;
    }

    @GetMapping("/viewNews")
    public String viewNews(
            @RequestParam(value = "id", required = false) String idParam,
            HttpSession session,
            Model model) {

        if (idParam == null || idParam.isEmpty()) {
            return "redirect:/home?error=News ID is required";
        }

        try {
            int id = Integer.parseInt(idParam);
            News news = newsService.findById(id);

            if (news == null) {
                return "redirect:/home?error=News not found";
            }

            // Проверяем права доступа
            if (!hasAccessToNews(news, session)) {
                return "redirect:/home?error=You don't have permission to view this news";
            }

            model.addAttribute("news", news);
            return "viewNews";

        } catch (NumberFormatException e) {
            return "redirect:/home?error=Invalid news ID format";
        } catch (Exception e) {
            return "redirect:/home?error=Error loading news: " + e.getMessage();
        }
    }

    @GetMapping("/viewNews/{id}")
    public String viewNewsById(
            @PathVariable("id") int newsId,
            HttpSession session,
            Model model) {

        try {
            News news = newsService.findById(newsId);

            if (news == null) {
                return "redirect:/home?error=News not found";
            }

            if (!hasAccessToNews(news, session)) {
                return "redirect:/home?error=You don't have permission to view this news";
            }

            model.addAttribute("news", news);

            return "viewNews";

        } catch (Exception e) {
            return "redirect:/home?error=Error loading news";
        }
    }

    private boolean hasAccessToNews(News news, HttpSession session) {

        if (news.getNews_status_id() == 2) {
            return true;
        }

        if (session == null || session.getAttribute("auth") == null) {
            return false;
        }

        User user = (User) session.getAttribute("auth");

        return user.getRoleId() == 1;
    }
}
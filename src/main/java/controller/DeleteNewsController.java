package controller;

import bean.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import service.NewsService;

import javax.servlet.http.HttpSession;

@Controller
public class DeleteNewsController {

    private final NewsService newsService;

    @Autowired
    public DeleteNewsController(NewsService newsService) {
        this.newsService = newsService;
    }

    @GetMapping("/deleteNews")
    public String deleteNews(
            HttpSession session,
            @RequestParam(value = "id") String idParam) {

        if (session == null || session.getAttribute("auth") == null) {
            return "redirect:/login";
        }

        User user = (User) session.getAttribute("auth");

        if (idParam == null || idParam.isEmpty()) {
            return "redirect:/myNews?error=Invalid news ID";
        }

        try {
            int newsId = Integer.parseInt(idParam);
            newsService.delete(newsId);
            return "redirect:/myNews?success=News deleted successfully";

        } catch (NumberFormatException e) {
            return "redirect:/myNews?error=Error deleting news";
        }
    }
}
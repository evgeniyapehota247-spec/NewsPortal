package controller;

import bean.News;
import bean.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import service.NewsService;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class MyNewsController {

    @Autowired
    private NewsService newsService;

    @GetMapping("/myNews")
    public String myNews(
            HttpSession session,
            @RequestParam(required = false) Integer status,
            @RequestParam(required = false) String success,
            @RequestParam(required = false) String error,
            Model model) {

        User user = (User) session.getAttribute("auth");
        if (user == null) {
            return "redirect:/login";
        }

        try {
            List<News> userNews = newsService.findByAuthorId(user.getId());

            if (status != null) {
                List<News> filteredNews = userNews.stream()
                        .filter(news -> news.getNews_status_id() == status)
                        .toList();
                model.addAttribute("myNews", filteredNews);
                model.addAttribute("currentStatus", status);
            } else {
                model.addAttribute("myNews", userNews);
            }

            // Статистика
            long publishedCount = userNews.stream()
                    .filter(n -> n.getNews_status_id() == 2)
                    .count();
            long draftCount = userNews.stream()
                    .filter(n -> n.getNews_status_id() == 1)
                    .count();

            model.addAttribute("publishedCount", publishedCount);
            model.addAttribute("draftCount", draftCount);
            model.addAttribute("totalCount", userNews.size());

            // Сообщения
            if (success != null) {
                model.addAttribute("success", success);
            }
            if (error != null) {
                model.addAttribute("error", error);
            }

            return "myNews";

        } catch (Exception e) {
            model.addAttribute("error", "Ошибка: " + e.getMessage());
            return "error";
        }
    }
}
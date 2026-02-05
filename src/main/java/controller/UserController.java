package controller;

import bean.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import service.NewsService;

import javax.servlet.http.HttpSession;

@Controller
public class UserController {

    @Autowired
    private NewsService newsService;

    @GetMapping("/userHome")
    public String userHome(HttpSession session, Model model) {
        User user = (User) session.getAttribute("auth");

        if (user == null) {
            return "redirect:/login";
        }

        try {

            // Получаем статистику пользователя
            var userNews = newsService.findByAuthorId(user.getId());
            long totalCount = userNews.size();
            long publishedCount = userNews.stream()
                    .filter(n -> n.getNews_status_id() == 2)
                    .count();
            long draftCount = userNews.stream()
                    .filter(n -> n.getNews_status_id() == 1)
                    .count();

            model.addAttribute("user", user);
            model.addAttribute("totalCount", totalCount);
            model.addAttribute("publishedCount", publishedCount);
            model.addAttribute("draftCount", draftCount);

            return "userHome";

        } catch (Exception e) {
            model.addAttribute("error", "Ошибка: " + e.getMessage());
            return "error";
        }
    }
}
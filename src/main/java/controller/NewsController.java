package controller;

import bean.News;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import service.NewsService;

import java.util.List;

@Controller
public class NewsController {

    @Autowired
    private NewsService newsService;

    private static final int PAGE_SIZE = 10;

    @GetMapping("/allNews")
    public String allNews(
            @RequestParam(defaultValue = "1") int page,
            Model model) {

        try {

            List<News> news = newsService.findAllNews(page, PAGE_SIZE);
            int totalPages = newsService.getTotalPages(PAGE_SIZE);

            // Проверяем, что текущая страница не больше общего количества
            if (page > totalPages && totalPages > 0) {
                page = totalPages;
                news = newsService.findAllNews(page, PAGE_SIZE);
            }

            model.addAttribute("newsList", news);
            model.addAttribute("currentPage", page);
            model.addAttribute("totalPages", totalPages);
            model.addAttribute("pageSize", PAGE_SIZE);

            return "allNews";

        } catch (Exception e) {
            model.addAttribute("error", "Ошибка при загрузке новостей: " + e.getMessage());
            return "error";
        }
    }
}
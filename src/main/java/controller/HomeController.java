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
public class HomeController {

    @Autowired
    private NewsService newsService;

    private static final int PAGE_SIZE = 3;

    @GetMapping({"", "/", "/home"})
    public String home(
            @RequestParam(defaultValue = "1") int page,
            Model model) {

        model.addAttribute("pageTitle", "Новости Беларуси");
        model.addAttribute("welcomeMessage", "Добро пожаловать на наш портал!");

        try {
            // Получаем новости для текущей страницы
            List<News> news = newsService.findAllNews(page, PAGE_SIZE);
            int totalPages = newsService.getTotalPages(PAGE_SIZE);
            int totalNewsCount = newsService.getTotalCount();

            // Проверяем, что текущая страница не больше общего количества
            if (page > totalPages && totalPages > 0) {
                page = totalPages;
                news = newsService.findAllNews(page, PAGE_SIZE);
            }

            // Устанавливаем атрибуты для пагинации
            model.addAttribute("topNews", news);
            model.addAttribute("currentPage", page);
            model.addAttribute("totalPages", totalPages);
            model.addAttribute("pageSize", PAGE_SIZE);
            model.addAttribute("totalNewsCount", totalNewsCount);

            // Вычисляем диапазон отображаемых новостей
            int startNews = (page - 1) * PAGE_SIZE + 1;
            int endNews = Math.min(page * PAGE_SIZE, totalNewsCount);
            model.addAttribute("startNews", startNews);
            model.addAttribute("endNews", endNews);

            return "main";

        } catch (Exception e) {
            model.addAttribute("error", "Ошибка при загрузке новостей: " + e.getMessage());
            return "error";
        }
    }
}
package controller;

import bean.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import service.AuthService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
public class AuthController {

    @Autowired
    private AuthService authService;

    @GetMapping("/login")
    public String showLoginForm(HttpSession session, Model model) {

        // Проверяем, не аутентифицирован ли пользователь
        if (session != null && session.getAttribute("auth") != null) {
            return "redirect:/userHome";
        }

        model.addAttribute("pageTitle", "Новости Беларуси - Вход");
        return "login";
    }

    @PostMapping("/login")
    public String login(
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam(required = false) String remember,
            HttpServletRequest request,
            HttpServletResponse response,
            HttpSession session,
            Model model) {

        try {
            User user = authService.signIn(email, password);

            if (user == null) {
                model.addAttribute("error", "Неверный email или пароль");
                return "login";
            }

            // Сохраняем пользователя в сессии
            session.setAttribute("auth", user);

            // Если выбрано "Запомнить меня", создаем куки
            if ("on".equals(remember)) {
                String rememberToken = authService.generateRememberToken(user);

                Cookie emailCookie = new Cookie("userEmail", user.getEmail());
                emailCookie.setMaxAge(30 * 24 * 60 * 60);
                emailCookie.setPath("/");
                emailCookie.setHttpOnly(true);

                Cookie tokenCookie = new Cookie("rememberToken", rememberToken);
                tokenCookie.setMaxAge(30 * 24 * 60 * 60);
                tokenCookie.setPath("/");
                tokenCookie.setHttpOnly(true);

                response.addCookie(emailCookie);
                response.addCookie(tokenCookie);
            }

            return "redirect:/userHome";

        } catch (Exception e) {
            model.addAttribute("error", "Ошибка авторизации: " + e.getMessage());
            return "login";
        }
    }
}
package controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
public class LogoutController {

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {

        // Удаляем сессию
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        // Удаляем куки аутентификации
        Cookie emailCookie = new Cookie("userEmail", "");
        emailCookie.setMaxAge(0);
        emailCookie.setPath("/");

        Cookie tokenCookie = new Cookie("rememberToken", "");
        tokenCookie.setMaxAge(0);
        tokenCookie.setPath("/");

        response.addCookie(emailCookie);
        response.addCookie(tokenCookie);

        return "redirect:/home";
    }
}
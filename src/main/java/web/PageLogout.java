package web;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/logout")
public class PageLogout extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Удаляем сессию
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        // Удаляем куки аутентификации
        Cookie emailCookie = new Cookie("userEmail", "");
        emailCookie.setMaxAge(0); // сразу устаревает
        emailCookie.setPath("/");

        Cookie tokenCookie = new Cookie("rememberToken", "");
        tokenCookie.setMaxAge(0); // сразу устаревает
        tokenCookie.setPath("/");

        response.addCookie(emailCookie);
        response.addCookie(tokenCookie);

        // Перенаправляем на главную страницу
        response.sendRedirect(request.getContextPath() + "/home");
    }
}
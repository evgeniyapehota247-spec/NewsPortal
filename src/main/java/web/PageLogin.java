package web;

import bean.User;
import service.ServiceException;
import service.ServiceProvider;
import service.UserSecurity;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/login")
public class PageLogin extends HttpServlet {

    private final UserSecurity userSecurity = ServiceProvider.getInstance().getUserSecurity();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // ПРОСТО показываем форму входа, БЕЗ ошибки
        request.setAttribute("pageTitle", "Новости Беларуси - Вход");
        request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        try {
            User user = userSecurity.signIn(email, password);

            if (user == null) {
                response.sendRedirect(request.getContextPath() + "/login?authError=true");
                System.out.println("ok!");
                return;
            }

            HttpSession session = request.getSession(true);//"дай мне сессию, если она есть, иначе создай новую" (по умолчанию)
            session.setAttribute("auth", user);

            response.sendRedirect(request.getContextPath() + "/userHome");
        } catch (ServiceException e) {
            response.sendRedirect("error.jsp");
        }
    }
}
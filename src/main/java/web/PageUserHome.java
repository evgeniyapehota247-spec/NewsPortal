package web;

import bean.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/userHome")
public class PageUserHome extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);//false - параметр, который означает: "не создавай новую сессию, если её нет"
        // "дай мне сессию, если она есть, иначе верни null"

        if (session == null){
            response.sendRedirect(request.getContextPath() + "/login&message=You are not logged in");
            return;
        }

       User user = (User) session.getAttribute("auth");
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login&message=You are not logged in");
            return;
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/userHome.jsp");
        dispatcher.forward(request, response);
    }
}

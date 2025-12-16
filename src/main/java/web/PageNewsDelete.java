//package web;
//
//import service.NewsService;
//import service.ServiceException;
//import service.ServiceProvider;
//
//import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
//@WebServlet("/news/delete")
//public class PageNewsDelete extends HttpServlet {
//
//    private final NewsService newsService =
//            ServiceProvider.getInstance().getNewsService();
//
//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//
//        int id = Integer.parseInt(request.getParameter("id"));
//
//        try {
//            newsService.delete(id);
//            response.sendRedirect(request.getContextPath() + "/news");
//
//        } catch (ServiceException e) {
//            throw new ServletException(e);
//        }
//    }
//}

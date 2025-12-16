//package web;
//
//import bean.News;
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
//@WebServlet("/news/view")
//public class PageNewsView extends HttpServlet {
//
//    private final NewsService newsService =
//            ServiceProvider.getInstance().getNewsService();
//
//    protected void doGet(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        int id = Integer.parseInt(request.getParameter("id"));
//        News news = null;
//        try {
//            news = newsService.getById(id);
//        } catch (ServiceException e) {
//            throw new RuntimeException(e);
//        }
//        request.setAttribute("news", news);
//        request.getRequestDispatcher("/WEB-INF/jsp/news/view.jsp").forward(request,response);
//    }
//}

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
//import java.util.List;
//
//@WebServlet("/news")
//public class PageNewsList extends HttpServlet {
//
//    private final NewsService newsService =
//            ServiceProvider.getInstance().getNewsService();
//
//    protected void doGet(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        List<News> news = null;
//        try {
//            news = newsService.getAll();
//        } catch (ServiceException e) {
//            throw new RuntimeException(e);
//        }
//        request.setAttribute("newsList", news);
//        request.getRequestDispatcher("/WEB-INF/jsp/news/list.jsp").forward(request,response);
//    }
//}

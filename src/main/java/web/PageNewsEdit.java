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
//@WebServlet("/news/edit")
//public class PageNewsEdit extends HttpServlet {
//
//    private final NewsService newsService =
//            ServiceProvider.getInstance().getNewsService();
//
//    protected void doGet(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        String action = request.getParameter("action");
//
//        if ("create".equals(action)) {
//            request.setAttribute("mode", "create");
//        } else {
//            int id = Integer.parseInt(request.getParameter("id"));
//            try {
//                request.setAttribute("news", newsService.getById(id));
//            } catch (ServiceException e) {
//                throw new RuntimeException(e);
//            }
//            request.setAttribute("mode", "edit");
//        }
//
//        request.getRequestDispatcher("/WEB-INF/jsp/news/edit.jsp").forward(request,response);
//    }
//
//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//
//        request.setCharacterEncoding("UTF-8");
//
//        String mode = request.getParameter("mode");
//        String title = request.getParameter("title");
//        String brief = request.getParameter("brief");
//        String content = request.getParameter("content");
//
//        try {
//            if ("create".equals(mode)) {
//
//                News news = new News();
//                news.setTitle(title);
//                news.setBrief(brief);
//                news.setContent(content);
//
//                newsService.create(news);
//
//            } else if ("edit".equals(mode)) {
//
//                int id = Integer.parseInt(request.getParameter("id"));
//
//                News news = new News();
//                news.setId(id);
//                news.setTitle(title);
//                news.setBrief(brief);
//                news.setContent(content);
//
//                newsService.update(news);
//            }
//
//            response.sendRedirect(request.getContextPath() + "/news");
//
//        } catch (ServiceException e) {
//            throw new ServletException(e);
//        }
//    }
//}

package filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//@WebFilter("/*")
public class EncodingFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("EncodingFilter инициализирован");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        // Устанавливаем кодировку UTF-8 для всех запросов и ответов
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        System.out.println("EncodingFilter: Установлена кодировка UTF-8 для " + request.getRequestURI());

        // Передаем запрос дальше
        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        System.out.println("EncodingFilter уничтожен");
    }
}
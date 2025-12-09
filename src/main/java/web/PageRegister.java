package web;

import bean.RegistrationInfo;
import service.ServiceException;
import service.ServiceProvider;
import service.UserSecurity;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/register")
public class PageRegister extends HttpServlet {

    private final UserSecurity userSecurity = ServiceProvider.getInstance().getUserSecurity();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("pageTitle", "Новости Беларуси - Регистрация");
        request.getRequestDispatcher("/WEB-INF/jsp/register.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Сохраняем введенные значения для отображения при ошибке
        request.setAttribute("firstName", request.getParameter("firstName"));
        request.setAttribute("lastName", request.getParameter("lastName"));
        request.setAttribute("email", request.getParameter("email"));

        RegistrationInfo.RegBuilder builder = new RegistrationInfo.RegBuilder();
        builder.firstName(request.getParameter("firstName"))
                .lastName(request.getParameter("lastName"))
                .email(request.getParameter("email"))
                .password(request.getParameter("password"));

        RegistrationInfo registrationInfo = builder.build();

        try {
            boolean success = userSecurity.registration(registrationInfo);

            if (success) {
                response.sendRedirect(request.getContextPath() + "/login?after_reg=true");
            } else {
                // Общая ошибка регистрации
                request.setAttribute("error", "Ошибка регистрации. Попробуйте позже.");
                request.getRequestDispatcher("/WEB-INF/jsp/register.jsp").forward(request, response);
            }

        } catch (ServiceException e) {
            // Конкретные ошибки
            String errorMessage = e.getMessage();

            if (errorMessage.contains("email") || errorMessage.contains("уже существует")) {
                request.setAttribute("error", "Пользователь с таким email уже существует");
                request.setAttribute("emailError", "Этот email уже занят");
                request.setAttribute("duplicateEmail", true);
            } else if (errorMessage.contains("пароль") || errorMessage.contains("password")) {
                request.setAttribute("error", "Пароль не соответствует требованиям");
                request.setAttribute("passwordError", "Используйте минимум 8 символов, цифры и буквы");
            } else if (errorMessage.contains("имя") || errorMessage.contains("name")) {
                request.setAttribute("error", "Неверно указано имя или фамилия");
                request.setAttribute("firstNameError", "Проверьте правильность имени");
                request.setAttribute("lastNameError", "Проверьте правильность фамилии");
            } else {
                request.setAttribute("error", "Ошибка регистрации: " + errorMessage);
            }

            request.getRequestDispatcher("/WEB-INF/jsp/register.jsp").forward(request, response);
        }
    }
}
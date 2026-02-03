package web;

import bean.User;
import bean.UserDetails;
import service.ServiceException;
import service.ServiceProvider;
import service.UserSecurity;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@WebServlet("/register")
public class PageRegister extends HttpServlet {

    private final UserSecurity userSecurity = ServiceProvider.getInstance().getUserSecurity();

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

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
        request.setAttribute("dob", request.getParameter("dob"));

        try {
            // Создаем объект User
            User user = new User();
            user.setEmail(request.getParameter("email"));
            user.setPassword(request.getParameter("password"));

            // Устанавливаем значения по умолчанию
            user.setUserStatusId(1); // Например, 1 = активный
            user.setRoleId(2); // Например, 2 = обычный пользователь

            user.setCreatedAt(LocalDateTime.now());
            user.setUpdatedAt(LocalDateTime.now());

            // Создаем объект UserDetails
            UserDetails userDetails = new UserDetails();
            userDetails.setFirstName(request.getParameter("firstName"));
            userDetails.setLastName(request.getParameter("lastName"));

            // Парсим дату рождения
            String dobParam = request.getParameter("dob");
            if (dobParam != null && !dobParam.trim().isEmpty()) {
                try {
                    LocalDate dob = LocalDate.parse(dobParam.trim(), DATE_FORMATTER);

                    // Дополнительная проверка на корректность даты
                    LocalDate now = LocalDate.now();
                    if (dob.isAfter(now)) {
                        request.setAttribute("error", "Дата рождения не может быть в будущем");
                        request.setAttribute("dobError", "Выберите корректную дату");
                        request.getRequestDispatcher("/WEB-INF/jsp/register.jsp").forward(request, response);
                        return;
                    }
                    userDetails.setDob(dob);

                } catch (DateTimeParseException e) {
                    request.setAttribute("error", "Неверный формат даты рождения. Используйте ГГГГ-ММ-ДД");
                    request.setAttribute("dobError", "Используйте формат: ГГГГ-ММ-ДД");
                    request.getRequestDispatcher("/WEB-INF/jsp/register.jsp").forward(request, response);
                    return;
                }
            }

            // Связываем User и UserDetails
            user.setUserDetails(userDetails);

            boolean success = userSecurity.registration(user);

            if (success) {
                response.sendRedirect(request.getContextPath() + "/login?after_reg=true");
            } else {
                request.setAttribute("error", "Ошибка регистрации. Попробуйте позже.");
                request.getRequestDispatcher("/WEB-INF/jsp/register.jsp").forward(request, response);
            }

        } catch (ServiceException e) {
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
            } else if (errorMessage.contains("дата") || errorMessage.contains("dob") || errorMessage.contains("возраст")) {
                request.setAttribute("error", "Неверная дата рождения");
                request.setAttribute("dobError", "Проверьте правильность даты рождения");
            } else {
                request.setAttribute("error", "Ошибка регистрации: " + errorMessage);
            }

            request.getRequestDispatcher("/WEB-INF/jsp/register.jsp").forward(request, response);

        } catch (Exception e) {
            request.setAttribute("error", "Произошла ошибка: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/jsp/register.jsp").forward(request, response);
        }
    }
}
package controller;

import bean.User;
import bean.UserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import service.AuthService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Controller
public class RegisterController {

    private final AuthService authService;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Autowired
    public RegisterController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/register")
    public String showRegisterForm(Model model) {

        model.addAttribute("pageTitle", "Новости Беларуси - Регистрация");
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String register(
            @ModelAttribute("user") User user,
            @RequestParam("firstName") String firstName,
            @RequestParam("lastName") String lastName,
            @RequestParam("dob") String dobParam,
            @RequestParam("password") String password,
            Model model,
            RedirectAttributes redirectAttributes) {

        model.addAttribute("firstName", firstName);
        model.addAttribute("lastName", lastName);
        model.addAttribute("email", user.getEmail());
        model.addAttribute("dob", dobParam);

        try {

            user.setUserStatusId(1);
            user.setRoleId(2);
            user.setCreatedAt(LocalDateTime.now());
            user.setUpdatedAt(LocalDateTime.now());
            user.setPassword(password);

            UserDetails userDetails = new UserDetails();
            userDetails.setFirstName(firstName);
            userDetails.setLastName(lastName);

            if (dobParam != null && !dobParam.trim().isEmpty()) {
                try {
                    LocalDate dob = LocalDate.parse(dobParam.trim(), DATE_FORMATTER);
                    LocalDate now = LocalDate.now();

                    if (dob.isAfter(now)) {
                        model.addAttribute("error", "Дата рождения не может быть в будущем");
                        model.addAttribute("dobError", "Выберите корректную дату");
                        return "register";
                    }
                    userDetails.setDob(dob);
                } catch (DateTimeParseException e) {
                    model.addAttribute("error", "Неверный формат даты рождения. Используйте ГГГГ-ММ-ДД");
                    model.addAttribute("dobError", "Используйте формат: ГГГГ-ММ-ДД");
                    return "register";
                }
            }

            user.setUserDetails(userDetails);

            boolean success = authService.registration(user);

            if (success) {
                redirectAttributes.addAttribute("after_reg", true);
                return "redirect:/login";
            } else {
                model.addAttribute("error", "Ошибка регистрации. Попробуйте позже.");
                return "register";
            }

        } catch (Exception e) {
            model.addAttribute("error", "Произошла ошибка: " + e.getMessage());
            return "register";
        }
    }

    private void handleRegistrationError(String errorMessage, Model model, String email) {
        if (errorMessage.contains("email") || errorMessage.contains("уже существует")) {
            model.addAttribute("error", "Пользователь с таким email уже существует");
            model.addAttribute("emailError", "Этот email уже занят");
            model.addAttribute("duplicateEmail", true);
        } else if (errorMessage.contains("пароль") || errorMessage.contains("password")) {
            model.addAttribute("error", "Пароль не соответствует требованиям");
            model.addAttribute("passwordError", "Используйте минимум 8 символов, цифры и буквы");
        } else if (errorMessage.contains("имя") || errorMessage.contains("name")) {
            model.addAttribute("error", "Неверно указано имя или фамилия");
            model.addAttribute("firstNameError", "Проверьте правильность имени");
            model.addAttribute("lastNameError", "Проверьте правильность фамилии");
        } else if (errorMessage.contains("дата") || errorMessage.contains("dob") || errorMessage.contains("возраст")) {
            model.addAttribute("error", "Неверная дата рождения");
            model.addAttribute("dobError", "Проверьте правильность даты рождения");
        } else {
            model.addAttribute("error", "Ошибка регистрации: " + errorMessage);
        }
    }
}
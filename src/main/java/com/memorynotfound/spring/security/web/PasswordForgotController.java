package com.memorynotfound.spring.security.web;

import com.memorynotfound.spring.security.model.Mail;
import com.memorynotfound.spring.security.model.PasswordResetToken;
import com.memorynotfound.spring.security.model.User;
import com.memorynotfound.spring.security.repository.PasswordResetTokenRepository;
import com.memorynotfound.spring.security.service.EmailService;
import com.memorynotfound.spring.security.service.TelegramService;
import com.memorynotfound.spring.security.service.UserService;
import com.memorynotfound.spring.security.web.dto.PasswordForgotDto;
import com.pengrad.telegrambot.TelegramBot;

import com.pengrad.telegrambot.request.SendMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("/forgot-password")
public class PasswordForgotController {

    @Autowired private UserService userService;
    @Autowired private PasswordResetTokenRepository tokenRepository;
    @Autowired private EmailService emailService;
   
    
    @ModelAttribute("forgotPasswordForm")
    public PasswordForgotDto forgotPasswordDto() {
        return new PasswordForgotDto();
    }

    @GetMapping
    public String displayForgotPasswordPage() {
        return "forgot-password";
    }

    @PostMapping
    public String processForgotPasswordForm(@ModelAttribute("forgotPasswordForm") @Valid PasswordForgotDto form,
                                            BindingResult result,
                                            HttpServletRequest request) {

        if (result.hasErrors()){
            return "forgot-password";
        }

        User user = userService.findByEmail(form.getEmail());
        if (user == null){
            result.rejectValue("email", null, "El email no se ecuentra registrado");
            return "forgot-password";
        }

        PasswordResetToken token = new PasswordResetToken();
        token.setToken(UUID.randomUUID().toString());
        token.setUser(user);
        token.setExpiryDate(2);
        tokenRepository.save(token);

        Mail mail = new Mail();
        mail.setFrom("santiagoandrade5@gmail.com");
        mail.setTo(user.getEmail());
        mail.setSubject("Restablecer contraseña");
        
   
        Map<String, Object> model = new HashMap<>();
        model.put("token", token);
        model.put("user", user);
        model.put("signature", "santiagoandrade5@gmail.com");
        String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
        model.put("resetUrl", url + "/reset-password?token=" + token.getToken());
        TelegramBot bot2 = new TelegramBot("740048086:AAEdFs0sFhuXBNYOWD_3SE4QMX-cA3VcAbE");
        SendMessage request2 = new SendMessage("839532185", "http://localhost:8080/reset-password?token=" + token.getToken());
		        
		bot2.execute(request2);
        mail.setModel(model);
        TelegramService.Bot("http://localhost:8080/reset-password?token=" + token.getToken());
        emailService.sendEmail(mail);
        
        return "redirect:/forgot-password?success";

    }

}

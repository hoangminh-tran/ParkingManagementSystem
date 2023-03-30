package com.demo.service.Impl;


import com.demo.entity.Customer;
import com.demo.entity.Resident;
import com.demo.entity.User;
import com.demo.repository.CustomerRepository;
import com.demo.repository.ResidentRepository;
import com.demo.repository.UserRepository;
import com.demo.service.MailService;
import com.demo.service.ThymeleafService;
import com.demo.utils.request.PaymentCustomerMail;
import com.demo.utils.response.FeeResponse;
import com.demo.utils.response.PaymentCustomerReponseDTO;
import com.demo.utils.response.PaymentResidentResponseDTO;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;


import java.nio.charset.StandardCharsets;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {


    private final JavaMailSender javaMailSender;


    private final UserRepository userRepository;


    private final ThymeleafService thymeleafService;

    private final CustomerRepository customerRepository;

    private final ResidentRepository residentRepository;


    @Value("${spring.mail.username")
    public String email;


    @Override
    public String invoiceCustomer(PaymentCustomerMail dto) {
        User user = userRepository.findById(dto.getId_User()).get();
        System.out.println(user);
        Customer customer = customerRepository.findById(dto.getId_User()).get();
        System.out.println(customer);
        if(user != null && customer.isStatus_Account() == false)
        {
            if(dto != null)
            {
                System.out.println(dto.getId_C_Invoice() + " " + dto.getStartTime());
                String email_to = user.getEmail();
                String email_subject = "Invoice Customer Booking";
                sendMailInvoiceCustomer(email_to, email_subject, dto);
            }
            else return "Fail";
        }
        else return "Fail";
        return "Success";
    }


    @Override
    public String invoiceResident(PaymentResidentResponseDTO invoiceToEmail) {
        User user = userRepository.findById(invoiceToEmail.getIdUser()).get();
        Resident resident = residentRepository.findById(invoiceToEmail.getIdUser()).get();
        if(user != null && resident.isStatus_Account() == false)
        {
            if(invoiceToEmail != null)
            {
                String email_to = user.getEmail();
                String email_subject = "Invoice Customer Booking";
                sendMailInvoiceResident(email_to, email_subject, invoiceToEmail);
            }
            else return "Fail";
        }
        else return "Fail";
        return "Success";
    }


    public String generatePassword() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789@";
        Random random = new Random();
        String password = "";
        for (int i = 0; i < 11; i++)
        {
            password +=  characters.charAt(random.nextInt(characters.length()));


        }
        String regex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@])(?=\\S+$).{8,}$";
        Pattern pattern = Pattern.compile( regex );
        Matcher matcher = pattern.matcher( password );


        if (matcher.matches()) {
            return password;
        } else {
            return generatePassword(); // recursion
        }
    }


    @Override
    public String forgot_password(String id_User) {
        User user = userRepository.findById(id_User).get();
        if(user != null) {
            String password = generatePassword();
            user.setPassword(password);
            String email_to = user.getEmail();
            String email_subject = "Update User Password";


            userRepository.save(user);
            sendMailForgotPassword(email_to, email_subject, password);
        }
        return "Success";
    }

    @Override
    public String feeCustomerExpired(FeeResponse dto) {
        User user = userRepository.findById(dto.getId_user()).get();
        Customer customer = customerRepository.findById(dto.getId_user()).get();
        if(user != null && customer != null && customer.isStatus_Account() == false) {
            if(dto != null)
            {
                String email_to = user.getEmail();
                String email_subject = "Fee Customer Expired";
                sendMailFeeCustomerExpired(email_to, email_subject, dto);
            }
            else return "Failed";
        }
        else return "Failed";
        return "Success";
    }

    @Override
    public String feeResidentExpired(FeeResponse dto) {
        User user = userRepository.findById(dto.getId_user()).get();
        Resident resident = residentRepository.findById(dto.getId_user()).get();
        if(user != null && resident != null && resident.isStatus_Account() == false){
            if(dto != null)
            {
                String email_to = user.getEmail();
                String email_subject = "Fee Resident Expired";
                sendMailFeeCustomerExpired(email_to, email_subject, dto);
            }
            else return "Failed";
        }
        else return "Failed";
        return "Success";
    }

    private void sendMailFeeCustomerExpired(String email_to, String email_subject, FeeResponse dto) {
        try{


            MimeMessage message = javaMailSender.createMimeMessage();


            MimeMessageHelper helper = new MimeMessageHelper(
                    message,
                    MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                    StandardCharsets.UTF_8.name()
            );
            helper.setFrom(email);
            helper.setTo(email_to);
            helper.setSubject(email_subject);
            helper.setText(thymeleafService.createContentFeeCustomerExpired(dto), true);
            javaMailSender.send(message);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }


    private void sendMailFeeResidentExpired(String email_to, String email_subject, FeeResponse dto) {
        try{


            MimeMessage message = javaMailSender.createMimeMessage();


            MimeMessageHelper helper = new MimeMessageHelper(
                    message,
                    MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                    StandardCharsets.UTF_8.name()
            );
            helper.setFrom(email);
            helper.setTo(email_to);
            helper.setSubject(email_subject);
            helper.setText(thymeleafService.createContentFeeResidentExpired(dto), true);
            javaMailSender.send(message);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    private void sendMailForgotPassword(String email_to, String email_subject, String password) {
        try{


            MimeMessage message = javaMailSender.createMimeMessage();


            MimeMessageHelper helper = new MimeMessageHelper(
                    message,
                    MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                    StandardCharsets.UTF_8.name()
            );
            helper.setFrom(email);
            helper.setTo(email_to);
            helper.setSubject(email_subject);
            helper.setText(thymeleafService.createContentForgotPassword(password), true);
            javaMailSender.send(message);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }


    private void sendMailInvoiceCustomer(String email_to, String email_subject, PaymentCustomerMail dto) {
        try{


            MimeMessage message = javaMailSender.createMimeMessage();


            MimeMessageHelper helper = new MimeMessageHelper(
                    message,
                    MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                    StandardCharsets.UTF_8.name()
            );
            helper.setFrom(email);
            helper.setTo(email_to);
            helper.setSubject(email_subject);
            helper.setText(thymeleafService.createContentInvoiceCustomer(dto), true);
            javaMailSender.send(message);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    private void sendMailInvoiceResident(String email_to, String email_subject, PaymentResidentResponseDTO dto) {
        try{


            MimeMessage message = javaMailSender.createMimeMessage();


            MimeMessageHelper helper = new MimeMessageHelper(
                    message,
                    MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                    StandardCharsets.UTF_8.name()
            );
            helper.setFrom(email);
            helper.setTo(email_to);
            helper.setSubject(email_subject);
            helper.setText(thymeleafService.createContentInvoiceResident(dto), true);
            javaMailSender.send(message);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}


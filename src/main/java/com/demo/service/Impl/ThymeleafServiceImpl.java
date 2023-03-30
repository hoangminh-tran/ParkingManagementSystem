package com.demo.service.Impl;


import com.demo.service.ThymeleafService;
import com.demo.utils.request.PaymentCustomerMail;
import com.demo.utils.response.FeeResponse;
import com.demo.utils.response.PaymentCustomerReponseDTO;
import com.demo.utils.response.PaymentResidentResponseDTO;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;


@Service
public class ThymeleafServiceImpl implements ThymeleafService {
    private static final String MAIL_TEMPLATE_BASE_NAME = "/mail/MailMessages";


    private static final String MAIL_TEMPLATE_PREFIX = "/templates/";


    private static final String MAIL_TEMPLATE_SUFFIX = ".html";


    private static final String UTF_8 = "UTF-8";


    private static final String TEMPLATE_FORGOT_PASSWORD = "mail_ForgotPassword";


    private static final String TEMPLATE_INVOICE_CUSTOMER = "mail_InvoiceCustomer";


    private static final String TEMPLATE_INVOICE_RESIDENT = "mail_InvoiceResident";

    private static final String TEMPLATE_FEE_CUSTOMER_EXPIRED = "mail_feeCustomerExpired";

    private static final String TEMPLATE_FEE_RESIDENT_EXPIRED = "mail_feeResidentExpired";

    private static TemplateEngine templateEngine;


    static
    {
        templateEngine = emailTemplateEngine();
    }


    private static TemplateEngine emailTemplateEngine() {
        final SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(htmlTemplateResolver());
        templateEngine.setTemplateEngineMessageSource(emailMessageSource());
        return templateEngine;
    }


    private static ITemplateResolver htmlTemplateResolver() {
        final ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setPrefix(MAIL_TEMPLATE_PREFIX);
        templateResolver.setSuffix(MAIL_TEMPLATE_SUFFIX);
        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateResolver.setCharacterEncoding(UTF_8);
        templateResolver.setCacheable(false);


        return templateResolver;
    }


    private static ResourceBundleMessageSource emailMessageSource() {
        final ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename(MAIL_TEMPLATE_BASE_NAME);
        return messageSource;
    }


    @Override
    public String createContentForgotPassword(String password) {
        final Context context = new Context();
        System.out.println(password);


        context.setVariable("password", password);


        return templateEngine.process(TEMPLATE_FORGOT_PASSWORD, context);
    }


    @Override
    public String createContentInvoiceCustomer(PaymentCustomerMail invoiceToEmail) {
        final Context context = new Context();


        context.setVariable("Id_C_Invoice", invoiceToEmail.getId_C_Invoice());
        context.setVariable("Status_Invoice",  (invoiceToEmail.isStatus_Invoice() == true ? "Completed" : "Not Completed"));
        context.setVariable("Id_Payment", invoiceToEmail.getId_Payment());
        context.setVariable("total_of_money", invoiceToEmail.getTotal_of_money());
        context.setVariable("Type_Of_Payment", invoiceToEmail.getType_Of_Payment());
        context.setVariable("startDate", invoiceToEmail.getStartDate());
        context.setVariable("endDate", invoiceToEmail.getEndDate());
        context.setVariable("startTime", invoiceToEmail.getStartTime());
        context.setVariable("endTime", invoiceToEmail.getEndTime());
        context.setVariable("Id_Booking", invoiceToEmail.getId_Booking());
        context.setVariable("id_C_Slot", invoiceToEmail.getId_C_Slot());


        return templateEngine.process(TEMPLATE_INVOICE_CUSTOMER, context);
    }


    @Override
    public String createContentInvoiceResident(PaymentResidentResponseDTO invoiceToEmail) {
        final Context context = new Context();


        context.setVariable("Id_R_Invoice", invoiceToEmail.getId_R_Invoice());
        context.setVariable("Status_Invoice",  (invoiceToEmail.isStatus() == true ? "Completed" : "Not Completed"));
        context.setVariable("Id_Payment", invoiceToEmail.getId_Payment());
        context.setVariable("Total_of_money", invoiceToEmail.getTotal_Of_Money());
        context.setVariable("Type_Of_Payment", invoiceToEmail.getTypeOfPayment());
        context.setVariable("Date_Of_Payment", invoiceToEmail.getDateOfPayment());


        return templateEngine.process(TEMPLATE_INVOICE_RESIDENT, context);
    }

    @Override
    public String createContentFeeCustomerExpired(FeeResponse dto) {
        final Context context = new Context();
        context.setVariable("Id_User", dto.getId_user());
        context.setVariable("id_invoice", dto.getId_invoice());
        context.setVariable("status_fee", dto.isStatus_fee());
        context.setVariable("current_date", dto.getCurrent_date());
        context.setVariable("current_time", dto.getCurrent_time());
        context.setVariable("end_date", dto.getEnd_date());
        context.setVariable("end_time", dto.getEnd_time());

        context.setVariable("expired", dto.getExpired());
        context.setVariable("based_fee", dto.getBased_fee());
        context.setVariable("fined_fee", dto.getFined_fee());
        context.setVariable("warning", dto.isWarning());
        context.setVariable("sum", dto.getSum());
        context.setVariable("has_paid", dto.getHas_paid());
        context.setVariable("total_fee", dto.getTotal_fee());

        return templateEngine.process(TEMPLATE_FEE_CUSTOMER_EXPIRED, context);
    }

    @Override
    public String createContentFeeResidentExpired(FeeResponse dto) {
        final Context context = new Context();
        context.setVariable("Id_User", dto.getId_user());
        context.setVariable("id_invoice", dto.getId_invoice());
        context.setVariable("status_fee", dto.isStatus_fee());
        context.setVariable("current_date", dto.getCurrent_date());
        context.setVariable("current_time", dto.getCurrent_time());
        context.setVariable("end_date", dto.getEnd_date());
        context.setVariable("end_time", dto.getEnd_time());
        context.setVariable("expired", dto.getExpired());
        context.setVariable("based_fee", dto.getBased_fee());
        context.setVariable("fined_fee", dto.getFined_fee());
        context.setVariable("warning", dto.isWarning());
        context.setVariable("sum", dto.getSum());
        context.setVariable("has_paid", dto.getHas_paid());
        context.setVariable("total_fee", dto.getTotal_fee());



        return templateEngine.process(TEMPLATE_FEE_RESIDENT_EXPIRED, context);
    }
}


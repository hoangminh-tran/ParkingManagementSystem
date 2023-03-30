package com.demo.service.Impl;

import com.demo.entity.*;
import com.demo.repository.*;
import com.demo.service.CustomerExpiredService;
import com.demo.service.LoginService;
import com.demo.utils.request.MailDTO;
import com.demo.utils.response.ExpiredResponse;
import com.demo.utils.response.FeeResponse;
import com.demo.utils.response.LoginAPI;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    BookingRepository bookingRepository;

    @Autowired
    Payment_C_Repository payment_c_repository;

    @Autowired
    Invoice_C_Repository invoice_c_repository;

    @Autowired
    ResidentRepository residentRepository;

    @Autowired
    CustomerExpiredService customerExpiredService;

    @Override
    public LoginAPI checkLoginAccount(String username, String password) {
        LoginAPI loginAPI = new LoginAPI();
//        System.out.println(username + " " + password);
        User user = userRepository.findUserByUsernameAndPassword(username, password);
        if(user != null)
        {
            Customer customer = customerRepository.findById(username).orElse(null);
            Resident resident = residentRepository.findById(username).orElse(null);
            if(customer != null)
            {
                if(customer.isStatus_Account() == true)
                {
                    loginAPI.setMessage("Your Customer Account has been banned");
                }
                else
                {
                    loginAPI = new LoginAPI(user.getId(), user.getFullname(), user.getPassword(), user.isGender(),
                            user.getDateofbirth(), user.getEmail(), user.getPhone(), customer.isStatus_Account(), "Login Customer Successfully");
                }
            }
            if(resident != null)
            {
                if(resident.isStatus_Account() == true)
                {
                    loginAPI.setMessage("Your Resident Account has been banned");
                }
                else
                {
                    loginAPI = new LoginAPI(user.getId(), user.getFullname(), user.getPassword(), user.isGender(),
                        user.getDateofbirth(), user.getEmail(), user.getPhone(), resident.isStatus_Account(), "Login Resident Successfully");
                }
            }
            if(resident == null && customer == null)
            {
                loginAPI.setMessage("Manager Can not be Login here");
            }
        }
        else
        {
            loginAPI.setMessage("Invalid Password or Username");
        }
        return loginAPI;
    }

    @Override
    public List<FeeResponse> checkLoginExpireInvoice(String id_User, String time) {
        List<FeeResponse> feeResponseList = new ArrayList<>();
        List<Booking> bookingList = bookingRepository.findBookingByCustomer(id_User);
        if(bookingList.size() > 0) {
            for (Booking booking : bookingList) {
                Payment_C payment_c = payment_c_repository.findPayment_C_By_Id_Booking(booking.getId_Booking());
                if (payment_c != null) {
                    Customer_Invoice customer_invoice = invoice_c_repository.findCustomer_Invoice_By_Id_Payment(payment_c.getId_Payment());
                    if (customer_invoice != null && customerExpiredService.checkExpired(id_User,
                            customerExpiredService.findAllCustomerInvoiceByCustomerID(id_User),
                            time) != null)
                    {
                        System.out.println("Hm: " + customerExpiredService.getCustomerFee(customer_invoice.getId_C_Invoice(), time));
                        feeResponseList.add(customerExpiredService.getCustomerFee(customer_invoice.getId_C_Invoice(), time));
                    }
                }
            }
        }
        return feeResponseList;
    }
}

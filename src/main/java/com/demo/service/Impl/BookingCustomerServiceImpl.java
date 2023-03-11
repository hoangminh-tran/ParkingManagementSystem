package com.demo.service.Impl;

import com.demo.entity.*;
import com.demo.repository.*;
import com.demo.service.BookingCustomerService;

import com.demo.utils.request.BookingAPI;
import com.demo.utils.request.BookingCustomerDTO;
import com.demo.utils.request.BookingDTO;
import com.demo.utils.response.BookingCustomerResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookingCustomerServiceImpl implements BookingCustomerService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    BuildingRepository buildingRepository;

    @Autowired
    ManagerRepository managerRepository;
    @Autowired
    Customer_Slot_Repository customer_slot_repository;

    @Autowired
    BookingRepository bookingRepository;

    @Autowired
    AreaRepository areaRepository;

    public BookingCustomerResponseDTO bookingCustomerResponseDTO;

    @Override
    public BookingCustomerResponseDTO save(BookingCustomerDTO dto) {

        Customer_Slot customerSlot = customer_slot_repository.findCustomerSlot(dto.getId_C_Slot(), dto.getId_Building());
        customerSlot.setType_Of_Vehicle(dto.getType_Of_Vehicle());
        customerSlot.setStatus_Slots(true);
        customer_slot_repository.save(customerSlot);

        List<Booking> list = bookingRepository.findAll();

        Booking booking1 = new Booking(Long.parseLong(list.size() + 1 + ""),
                dto.getStartDate(), dto.getEndDate(), dto.getStartTime(), dto.getEndTime(),
                customerSlot, customerRepository.findById(dto.getIdUser()).get());
        bookingRepository.save(booking1);

        bookingCustomerResponseDTO =  new BookingCustomerResponseDTO(booking1.getId_Booking(), dto.getFullname(), dto.getEmail(), dto.getPhone(),
                dto.getId_Building(), dto.getType_Of_Vehicle(), dto.getId_C_Slot(), dto.getStartDate(),
                dto.getEndDate(), dto.getStartTime(), dto.getEndTime(), 26);
        return  bookingCustomerResponseDTO;
    }

    @Override
    public BookingCustomerResponseDTO findBooking() {
        return bookingCustomerResponseDTO;
    }

    @Override
    public List<BookingAPI> findAllBooking() {
        List<Booking> bookingList = bookingRepository.findAll();
        List<BookingAPI>list = new ArrayList<>();
        for (Booking booking : bookingList)
        {
            list.add(new BookingAPI(booking.getId_Booking(), booking.getStartDate(), booking.getEndDate(), booking.getStartTime(),
                    booking.getEndTime(), booking.getCustomer().getIdUser(), customer_slot_repository.findById(booking.getCustomer_slot().getIndex()).get().getId_C_Slot()));
        }
        return list;
    }

    @Override
    public BookingAPI findBookingById(Long id) {
        Booking booking = bookingRepository.findById(id).get();
        return new BookingAPI(booking.getId_Booking(), booking.getStartDate(), booking.getEndDate(), booking.getStartTime(),
                booking.getEndTime(), booking.getCustomer().getIdUser(), customer_slot_repository.findById(booking.getCustomer_slot().getIndex()).get().getId_C_Slot());
    }
}

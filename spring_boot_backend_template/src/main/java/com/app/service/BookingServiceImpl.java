package com.app.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.dto.BookingDTO;
import com.app.dto.BookingResponseDTO;
import com.app.entity.Booking;
import com.app.entity.Seat;
import com.app.entity.Station;
import com.app.entity.Trains;
import com.app.dao.BookingRepository;
import com.app.dao.SeatDao;
import com.app.dao.StationDao;
import com.app.dao.TrainDao;
import com.app.dao.TrainStationDao;
import com.app.service.BookingService;

@Service
@Transactional
public class BookingServiceImpl implements BookingService {

    @Autowired
    private SeatDao seatDao;

    @Autowired
    private TrainDao trainDao;
    
    @Autowired
	private StationDao stationDao;

    @Autowired
    private ModelMapper modelMapper;
    
    @Autowired
    private BookingRepository bookingRepository;

    @Override
    public BookingDTO createBooking(BookingDTO bookingDTO) {
        Booking booking = new Booking();
        booking.setBookingDate(bookingDTO.getBookingDate());

        Seat seat = seatDao.findById(bookingDTO.getSeatId())
                .orElseThrow(() -> new RuntimeException("Seat not found"));
        booking.setSeat(seat);

        Trains train = trainDao.findById(bookingDTO.getTrainId())
                .orElseThrow(() -> new RuntimeException("Train not found"));
        booking.setTrain(train);

        Station fromStation = stationDao.findById(bookingDTO.getFromStationId())
                .orElseThrow(() -> new RuntimeException("From Station not found"));
        booking.setFromStation(fromStation);

        Station toStation = stationDao.findById(bookingDTO.getToStationId())
                .orElseThrow(() -> new RuntimeException("To Station not found"));
        booking.setToStation(toStation);

        booking = bookingRepository.save(booking);
        return modelMapper.map(booking, BookingDTO.class);
    }

    @Override
    public BookingDTO updateBooking(Long id, BookingDTO bookingDTO) {
        Booking booking = bookingRepository.findById(id).orElseThrow(() -> new RuntimeException("Booking not found"));
        modelMapper.map(bookingDTO, booking);
        booking = bookingRepository.save(booking);
        return modelMapper.map(booking, BookingDTO.class);
    }

    @Override
    public BookingDTO getBookingById(Long id) {
        Booking booking = bookingRepository.findById(id).orElseThrow(() -> new RuntimeException("Booking not found"));
        return modelMapper.map(booking, BookingDTO.class);
    }

    @Override
    public List<BookingResponseDTO> getAllBookings() {
        List<Booking> bookings = bookingRepository.findAll();
        List<BookingResponseDTO> bookingResponseDTOs = new ArrayList<>();

        for (Booking booking : bookings) {
            BookingResponseDTO bookingResponseDTO = new BookingResponseDTO();
            bookingResponseDTO.setBookingId(booking.getId());
            bookingResponseDTO.setBookingDate(booking.getBookingDate());

            if (booking.getSeat() != null) {
                bookingResponseDTO.setSeatId(booking.getSeat().getId());
                bookingResponseDTO.setSeatNumber(booking.getSeat().getSeatNumber());
            }
            if (booking.getTrain() != null) {
                bookingResponseDTO.setTrainId(booking.getTrain().getId());
                bookingResponseDTO.setTrainName(booking.getTrain().getName());
            }
            if (booking.getFromStation() != null) {
                bookingResponseDTO.setSourceStationName(booking.getFromStation().getName());
            }
            if (booking.getToStation() != null) {
                bookingResponseDTO.setDestinationStationName(booking.getToStation().getName());
            }

            bookingResponseDTOs.add(bookingResponseDTO);
        }

        return bookingResponseDTOs;
    }



    @Override
    public void deleteBooking(Long id) {
        bookingRepository.deleteById(id);
    }
}

package com.app.dto;

import java.time.LocalDate;

import lombok.Data;
@Data
public class BookingResponseDTO {

	
	 private Long bookingId;
	    private Long seatId;
	    private Long trainId;
	    private String trainName;
	    private String sourceStationName;
	    private String destinationStationName;
	    private String seatNumber;
	    private LocalDate bookingDate;
}

package com.flightReservation.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.flightReservation.dto.ReservationRequest;
import com.flightReservation.entity.Flight;
import com.flightReservation.entity.Passenger;
import com.flightReservation.entity.Reservation;
import com.flightReservation.repository.FlightRepository;
import com.flightReservation.repository.PassengerRepository;
import com.flightReservation.repository.ReservationRepository;
//import com.flightReservation.utilities.EmailUtil;
import com.flightReservation.utilities.PDFGenerator;

@Service
public class ReservationServiceImpl implements ReservationService {
	
	@Autowired
	private PassengerRepository passengerRepo;
	
	@Autowired
	private FlightRepository flightRepo;
	
	@Autowired
	private ReservationRepository reservationRepo;
	
	@Autowired
	private PDFGenerator pdfGenerator;
	
	//@Autowired
	//private EmailUtil emailUtil;

	@Override
	public Reservation bookFlight(ReservationRequest request) {
		
		
		
		Passenger passenger = new Passenger();
		passenger.setFirstName(request.getFirstName());
		passenger.setLastName(request.getLastName());
		passenger.setMiddleName(request.getMiddleName());
		passenger.setEmail(request.getEmail());
		passenger.setPhone(request.getPhone());
		passengerRepo.save(passenger);
		
		long flightId = request.getFlightId();
		Optional<Flight> findById = flightRepo.findById(flightId);
		Flight flight = findById.get();
		
		Reservation reservation = new Reservation();
		reservation.setFlight(flight);
		reservation.setPassenger(passenger);
		reservation.setCheckedIn(false);
		reservation.setNumberofBags(0);
	
		String filePath = "E:\\coding-sts\\flightReservation-1\\tickets\\reservation"+reservation.getId()+".pdf";	
		reservationRepo.save(reservation);
		
		pdfGenerator.generateItinerary(reservation, filePath);
		
		//emailUtil.sendItinerary(passenger.getEmail(), filePath);
		
		return reservation;
	}

}

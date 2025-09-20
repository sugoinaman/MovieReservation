package dev.sugoi.moviereservationroadmapssh.Reservation;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;

    //ToDo: Only admins and people who made the reservation can interact with the reservations.

    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    // Save a new reservation
    public Reservation addReservation(Reservation reservation) {
        return reservationRepository.save(reservation);
    }

    // Get reservation by ID
    public Optional<Reservation> getReservationById(Integer id) {
        return reservationRepository.findById(id);
    }

    // Delete a reservation
    public void deleteReservation(Reservation reservation) {
        reservationRepository.delete(reservation);
    }

    // Get all reservations
    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

}   //ToDo: Modify a reservation? maybe user/admin change timings and date

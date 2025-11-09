package dev.sugoi.moviereservationroadmapssh.Reservation;

import dev.sugoi.moviereservationroadmapssh.Exceptions.NotEnoughSeatException;
import dev.sugoi.moviereservationroadmapssh.Exceptions.ShowTimeNotFoundException;
import dev.sugoi.moviereservationroadmapssh.Exceptions.UserNotFoundException;
import dev.sugoi.moviereservationroadmapssh.Showtime.ShowTime;
import dev.sugoi.moviereservationroadmapssh.Showtime.ShowTimeRepository;
import dev.sugoi.moviereservationroadmapssh.User.User;
import dev.sugoi.moviereservationroadmapssh.User.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ShowTimeRepository showTimeRepository;
    private final UserRepository userRepository;

    //ToDo: Only admins and people who made the reservation can interact with the reservations.

    public ReservationService(ReservationRepository reservationRepository, ShowTimeRepository showTimeRepository, UserRepository userRepository) {
        this.reservationRepository = reservationRepository;
        this.showTimeRepository = showTimeRepository;
        this.userRepository = userRepository;
    }

    // Get all reservations
    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    // Save a new reservation
    public Reservation addReservation(Integer userId, Integer showTimeId, int numberOfSeatsToBook) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found"));
        ShowTime showTime = showTimeRepository.findById(showTimeId).orElseThrow(() -> new ShowTimeNotFoundException("Show time not found"));
        int numberOfAvailableSeats = showTime.getAvailableSeatsInAShow();
        if (numberOfAvailableSeats < numberOfSeatsToBook) {
            throw new NotEnoughSeatException("Not enough available seats. Available seats: " + numberOfAvailableSeats);
        }

        showTime.setAvailableSeatsInAShow(numberOfAvailableSeats - numberOfSeatsToBook);

        return new Reservation(Calendar.getInstance(), showTime.getMovie(), numberOfSeatsToBook, user);
    }

    public Optional<Reservation> getReservationById(Integer id) {
        return reservationRepository.findById(id);
    }

    Reservation modifyReservation(Reservation newReservation, Integer id) {
        return reservationRepository.findById(id)
                .map(reservation -> {
                    reservation.setMovie(newReservation.getMovie());
                    //ToDo before changing number of seats we need to check if they are available
                    reservation.setNumberOfSeats(newReservation.getNumberOfSeats());
                    return reservation;
                })
                .orElseGet(() -> {
                    return reservationRepository.save(newReservation);
                });
    }

    public void deleteReservation(Reservation reservation) {
        reservationRepository.delete(reservation);
    }

}

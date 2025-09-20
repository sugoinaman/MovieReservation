package dev.sugoi.moviereservationroadmapssh.Reservation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation,Integer> {
}
// I made this a separate entity with a repo because if I don't i'll have to queue
// all users to get the reservations
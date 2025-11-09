package dev.sugoi.moviereservationroadmapssh.Showtime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShowTimeRepository extends JpaRepository<ShowTime, Integer> {

}

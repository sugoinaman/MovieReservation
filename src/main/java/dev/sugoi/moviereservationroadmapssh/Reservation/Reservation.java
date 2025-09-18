package dev.sugoi.moviereservationroadmapssh.Reservation;

import dev.sugoi.moviereservationroadmapssh.User.User;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Reservation {

    @Id
    private Integer id;
    // reservation created at this time
    private String createdAt;

    @ManyToOne
    @JoinColumn (name = "user_id")
    private User user;
}

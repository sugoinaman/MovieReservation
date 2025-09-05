package dev.sugoi.moviereservationroadmapssh.Reservation;

import jakarta.persistence.Id;

public class Reservation {

    @Id
    private Integer id;
    // reservation created at this time
    private String createdAt;
}

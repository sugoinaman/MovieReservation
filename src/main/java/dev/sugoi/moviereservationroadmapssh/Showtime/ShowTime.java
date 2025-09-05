package dev.sugoi.moviereservationroadmapssh.Showtime;

import jakarta.persistence.Id;

public class ShowTime {

    @Id
    private Integer id;
    private Integer startTime;
    private Integer stopTime;
    private Integer price;
}

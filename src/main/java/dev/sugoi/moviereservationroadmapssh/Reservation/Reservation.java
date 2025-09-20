package dev.sugoi.moviereservationroadmapssh.Reservation;

import dev.sugoi.moviereservationroadmapssh.Movie.Movie;
import dev.sugoi.moviereservationroadmapssh.User.User;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Reservation {

    @Id
    private Integer id; //This could be a uuid ?
    // reservation created at this time
    private String createdAt;

    //Note to self: One reservation can only have 1 movie but more than 1 seat booking
    // to book another movie make another reservation hence the many to one.
    @ManyToOne
    @JoinColumn (name = "movie_id")
    private Movie movie;

    private Integer numberOfSeats;

    @ManyToOne
    @JoinColumn (name = "user_id")
    private User user;


}

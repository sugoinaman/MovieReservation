package dev.sugoi.moviereservationroadmapssh.Reservation;

import dev.sugoi.moviereservationroadmapssh.Movie.Movie;
import dev.sugoi.moviereservationroadmapssh.User.User;
import jakarta.persistence.*;

@Entity
public class Reservation {

    @Id
    private Integer id;
    private String createdAt;  // reservation created at this time

    @ManyToOne
    @JoinColumn (name = "movie_id")
    private Movie movie;
    private Integer numberOfSeats;
    @ManyToOne
    @JoinColumn (name = "user_id")
    private User user;

    //Note to self: One reservation can only have 1 movie but more than 1 seat booking
    // to book another movie make another reservation hence the many to one.


    public Reservation(String createdAt, Movie movie, Integer numberOfSeats, User user) {
        this.createdAt = createdAt;
        this.movie = movie;
        this.numberOfSeats = numberOfSeats;
        this.user = user;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public Integer getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setNumberOfSeats(Integer numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

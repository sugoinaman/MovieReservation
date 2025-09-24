package dev.sugoi.moviereservationroadmapssh.Movie;

import dev.sugoi.moviereservationroadmapssh.Reservation.Reservation;
import dev.sugoi.moviereservationroadmapssh.Showtime.ShowTime;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class Movie {

    @Id
    private Integer Id;
    private String title;
    private String description;
    private String genre;

    //ToDo: need a poster image ?

    @OneToMany(mappedBy = "movie")
    private List<ShowTime> showTimes;

    @OneToMany
    private List<Reservation> reservations;

    public Movie() {

    }

    public Movie(String title, String description, String genre, List<ShowTime> showTimes, List<Reservation> reservations) {
        this.title = title;
        this.description = description;
        this.genre = genre;
        this.showTimes = showTimes;
        this.reservations = reservations;
    }


    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public List<ShowTime> getShowTimes() {
        return showTimes;
    }

    public void setShowTimes(List<ShowTime> showTimes) {
        this.showTimes = showTimes;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }
}

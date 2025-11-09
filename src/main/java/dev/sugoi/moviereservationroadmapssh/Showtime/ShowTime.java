package dev.sugoi.moviereservationroadmapssh.Showtime;

import dev.sugoi.moviereservationroadmapssh.Movie.Movie;
import jakarta.persistence.*;

import java.util.Calendar;
import java.util.Date;

@Entity
public class ShowTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer availableSeatsInAShow;
    private Calendar startTime;
    private Calendar stopTime;

    private Integer price;

    @ManyToOne
    @JoinColumn(name = "movie_id")
    private Movie movie;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAvailableSeatsInAShow() {
        return availableSeatsInAShow;
    }

    public void setAvailableSeatsInAShow(Integer availableSeatsInAShow) {
        this.availableSeatsInAShow = availableSeatsInAShow;
    }

    public Calendar getStartTime() {
        return startTime;
    }

    public void setStartTime(Calendar startTime) {
        this.startTime = startTime;
    }

    public Calendar getStopTime() {
        return stopTime;
    }

    public void setStopTime(Calendar stopTime) {
        this.stopTime = stopTime;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }
}

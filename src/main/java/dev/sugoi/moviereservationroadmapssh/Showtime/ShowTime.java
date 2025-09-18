package dev.sugoi.moviereservationroadmapssh.Showtime;

import dev.sugoi.moviereservationroadmapssh.Movie.Movie;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class ShowTime {

    @Id
    private Integer id;
    private Integer startTime;
    private Integer stopTime;
    private Integer price;

    @ManyToOne
    @JoinColumn(name = "movie_id")
    private Movie movie;

}

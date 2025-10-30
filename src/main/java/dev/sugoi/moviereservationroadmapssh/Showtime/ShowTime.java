package dev.sugoi.moviereservationroadmapssh.Showtime;

import dev.sugoi.moviereservationroadmapssh.Movie.Movie;
import jakarta.persistence.*;

@Entity
public class ShowTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer startTime;
    private Integer stopTime;
    private Integer price;

    @ManyToOne
    @JoinColumn(name = "movie_id")
    private Movie movie;



}

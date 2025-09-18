package dev.sugoi.moviereservationroadmapssh.Movie;

import dev.sugoi.moviereservationroadmapssh.Showtime.ShowTime;
import dev.sugoi.moviereservationroadmapssh.User.User;
import jakarta.persistence.*;

import java.util.List;

@Entity

public class Movie {

    @Id
    private Integer Id;
    private String name;
    private String description;

    @OneToMany(mappedBy = "movie")
    private List<ShowTime> showTimes;

}

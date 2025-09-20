package dev.sugoi.moviereservationroadmapssh.Movie;

import dev.sugoi.moviereservationroadmapssh.Showtime.ShowTime;
import dev.sugoi.moviereservationroadmapssh.User.User;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Movie {

    @Id
    private Integer Id;
    private String title;
    private String description;
    private String genre;

    //ToDo: need a poster image ?

    @OneToMany(mappedBy = "movie")
    private List<ShowTime> showTimes;


}

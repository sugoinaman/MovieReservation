package dev.sugoi.moviereservationroadmapssh.Movie;

import dev.sugoi.moviereservationroadmapssh.User.User;
import jakarta.persistence.*;

@Entity

public class Movie {

    @Id
    private Integer Id;
    private String name;
    private String description;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

}

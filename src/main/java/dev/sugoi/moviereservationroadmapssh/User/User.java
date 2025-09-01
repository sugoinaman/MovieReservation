package dev.sugoi.moviereservationroadmapssh.User;

import dev.sugoi.moviereservationroadmapssh.Movie.Movie;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Cascade;
import org.springframework.lang.NonNull;

import java.util.List;

@Entity
@Table(name = "Person")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class User {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer Id;

    @NonNull
    @Column(name = "user_name")
    private String userName;

    @NonNull
    @Column(name = "email")
    private String email;

    @NonNull
    @Column(name = "password")
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Movie> listOfBookedMovies;
}
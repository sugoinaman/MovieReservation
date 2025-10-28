package dev.sugoi.moviereservationroadmapssh.Movie;

import dev.sugoi.moviereservationroadmapssh.Reservation.Reservation;
import dev.sugoi.moviereservationroadmapssh.Showtime.ShowTime;
import jakarta.persistence.*;
import org.springframework.lang.NonNull;


import java.util.List;

@Entity
public class Movie {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer Id;

    @NonNull
    @Column(name = "movie_title")
    private String title;

    @NonNull
    @Column(name = "movie_description")
    private String description;

    @Enumerated(EnumType.STRING)
    private Genre genre;

    //ToDo: need a poster image ?

    @OneToMany(mappedBy = "movie")
    private List<ShowTime> showTimes;

    @OneToMany
    private List<Reservation> reservations;

    public Movie() {

    }

    public Movie(String title, String description, Genre genre, List<ShowTime> showTimes, List<Reservation> reservations) {
        this.title = title;
        this.description = description;
        this.genre = genre;
        this.showTimes = showTimes;
        this.reservations = reservations;
    }


    public Integer getid() {
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

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
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

    @Override
    public String toString() {
        return "Movie{" +
                "Id=" + Id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", genre=" + genre +
                ", showTimes=" + showTimes +
                ", reservations=" + reservations +
                '}';
    }
}

package dev.sugoi.moviereservationroadmapssh.User;


import dev.sugoi.moviereservationroadmapssh.Reservation.Reservation;
import jakarta.persistence.*;
import org.springframework.lang.NonNull;

import java.util.List;

@Entity
@Table(name = "Person")
public class User {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer Id;

    public User() {
    }

    public User(@NonNull String userName, @NonNull String email, @NonNull String password) {
        this.userName = userName;
        this.email = email;
        this.password = password;
    }

    public User(Integer id, @NonNull String userName, @NonNull String email, @NonNull String password) {
        Id = id;
        this.userName = userName;
        this.email = email;
        this.password = password;
    }

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

    @OneToMany(mappedBy = "user")
    private List<Reservation> reservations;

    public Integer getId() {
        return Id;
    }

    public String getUserName() {
        return userName;
    }


    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Role getRole() {
        return role;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setUserName(@NonNull String userName) {
        this.userName = userName;
    }

    public void setEmail(@NonNull String email) {
        this.email = email;
    }

    public void setPassword(@NonNull String password) {
        this.password = password;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }
}
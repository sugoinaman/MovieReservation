package dev.sugoi.moviereservationroadmapssh.Screen;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Screen {

    @Id
    private Integer id;
    private String name;
    private Integer capacity;
}

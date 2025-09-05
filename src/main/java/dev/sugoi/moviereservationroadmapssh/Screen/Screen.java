package dev.sugoi.moviereservationroadmapssh.Screen;

import jakarta.persistence.Id;

public class Screen {

    @Id
    private Integer id;
    private String name;
    private Integer capacity;
}

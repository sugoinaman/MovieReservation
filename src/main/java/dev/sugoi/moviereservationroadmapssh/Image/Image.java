package dev.sugoi.moviereservationroadmapssh.Image;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;

@Entity
public class Image {

    @Id
    @GeneratedValue
    Long id;

    @Lob
    byte[] content;

    String name;


    public Image(Long id, byte[] content, String name) {
        this.id = id;
        this.content = content;
        this.name = name;
    }

    public Image() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}

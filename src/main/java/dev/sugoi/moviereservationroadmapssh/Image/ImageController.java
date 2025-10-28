package dev.sugoi.moviereservationroadmapssh.Image;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;

@RestController()
@RequestMapping("/images")
public class ImageController {

    private final ImageRepository imageRepository;

    ImageController(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    @PostMapping
    public Long saveImage(@RequestBody MultipartFile image) throws IOException {
        Image dbimage = new Image();
        dbimage.setName(image.getName());
        dbimage.setContent(image.getBytes());

        return imageRepository.save(dbimage).getId();
    }

    @GetMapping
    Resource downloadImage(@PathVariable Long id) {
        byte[] image = imageRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND))
                .getContent();

        return new ByteArrayResource(image);
    }
}

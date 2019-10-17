package com.polywood.filmservice.controller;

import com.polywood.filmservice.model.MoviesEntity;
import com.polywood.filmservice.repositories.EntityMovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@CrossOrigin
@RequestMapping("/movies")
public class MovieController {

    private static final Pattern imdbPattern = Pattern.compile("Poster\"\\ssrc=\"([^\"]+)"); //IMDB Regex to extract image url
    private final String IMDB_FILM_API = "https://www.imdb.com/title";
    private final String regex_imdb = "Poster\"\\ssrc=\"([^\"]+)";


    private EntityMovieRepository anEntityMovieRepository;

    @Autowired
    public MovieController(EntityMovieRepository entityMovieRepository){
        this.anEntityMovieRepository = entityMovieRepository;
    }

    @GetMapping("/")
    public List<MoviesEntity> findAllMovies() {
        List<MoviesEntity> movies = null;
        try {
            movies = anEntityMovieRepository.findAll();
        } catch (Exception e) {

            ResponseEntity.notFound().build();
        }
        return movies;
    }

    @GetMapping("/{id}")
    public MoviesEntity getMovieById(@PathVariable(value = "id") String id) {
        return anEntityMovieRepository.findByImdbId(id);
    }

    @GetMapping("/image/{id}")
    public String getMovieImageById(@PathVariable(value = "id") String id) {


        RestTemplate restTemplate = new RestTemplate();
        String imdbResult = restTemplate.getForObject(
                IMDB_FILM_API + "/" + id, String.class);
        Matcher m = imdbPattern.matcher(imdbResult);

        return (m.find())? m.group(1):"";
    }

}
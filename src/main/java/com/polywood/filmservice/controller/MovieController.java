package com.polywood.filmservice.controller;

import com.polywood.filmservice.model.ActorsEntity;
import com.polywood.filmservice.model.CastingEntity;
import com.polywood.filmservice.model.MoviesEntity;
import com.polywood.filmservice.repositories.EntityActorRepository;
import com.polywood.filmservice.repositories.EntityCastingRepository;
import com.polywood.filmservice.repositories.EntityMovieRepository;
import com.polywood.filmservice.tools.OffsetBasedPageRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@CrossOrigin
@RequestMapping("/movies")
public class MovieController {

    private static final Pattern imdbPattern = Pattern.compile("Poster\"\\ssrc=\"([^\"]+)"); //IMDB Regex to extract image url
    private final String IMDB_FILM_API = "https://www.imdb.com/title";

    private EntityMovieRepository anEntityMovieRepository;
    private EntityCastingRepository anEntityCastingRepository;
    private EntityActorRepository anEntityActorRepository;

    @Autowired
    public MovieController(EntityMovieRepository entityMovieRepository,
                           EntityCastingRepository entityCastingRepository,
                           EntityActorRepository entityActorRepository){
        this.anEntityMovieRepository = entityMovieRepository;
        this.anEntityCastingRepository = entityCastingRepository;
        this.anEntityActorRepository = entityActorRepository;
    }

    @RequestMapping(value = "", method = GET)
    @ResponseBody
    public List<MoviesEntity> findAllMoviesPaged(
            @RequestParam("page") Optional<Integer> page, @RequestParam("size") Optional<Integer> size, @RequestParam("sort") Optional<String> sort) {
        Pageable pageable =
                PageRequest.of(page.orElse(0), size.orElse(Integer.MAX_VALUE), Sort.by(sort.orElse("title")));

        Page<MoviesEntity> movies = null;
        try {
            movies = anEntityMovieRepository.findAll(pageable);
        } catch (Exception e) {

            ResponseEntity.notFound().build();
        }

        if(movies == null)
            ResponseEntity.notFound().build();

        return Objects.requireNonNull(movies).getContent();
    }
    
    @RequestMapping(value = "/maxPage/{size}", method = GET)
    public int getAllMoviesPages(@PathVariable("size") Integer size, @RequestParam("search") Optional<String> search) {
        Pageable pageable = PageRequest.of(0, size);
        Page<MoviesEntity> movies = null;
        try {
            if (search.isPresent())
                movies = anEntityMovieRepository.findMoviesByTitle(search.get().replace("+"," "), pageable);
            else
                movies = anEntityMovieRepository.findAll(pageable);
        } catch (Exception e) {
            ResponseEntity.notFound().build();
        }
    
        if(movies == null)
            ResponseEntity.notFound().build();
    
        return Objects.requireNonNull(movies).getTotalPages();
    }

    @GetMapping("/{id}")
    public MoviesEntity getMovieById(@PathVariable(value = "id") String id) {

        return anEntityMovieRepository.findByMovieid(id);
    }

    @GetMapping("/genre/{genre}")
    public List<MoviesEntity> getMoviesByGenre(@PathVariable(value = "genre") String genre,
    @RequestParam("page") Optional<Integer> page, @RequestParam("size") Optional<Integer> size, @RequestParam("sort") Optional<String> sort) {

        Pageable pageable =
                PageRequest.of(page.orElse(0), size.orElse(Integer.MAX_VALUE), Sort.by(sort.orElse("title")));

        Page<MoviesEntity> movies = null;
        try {
            movies = anEntityMovieRepository.findMoviesByGenre(genre, pageable);
        } catch (Exception e) {

            ResponseEntity.notFound().build();
        }

        if(movies == null)
            ResponseEntity.notFound().build();

        return Objects.requireNonNull(movies).getContent();
    }

    @GetMapping("/director/{director}")
    public List<MoviesEntity> getMoviesByDirector(@PathVariable(value = "director") String director,
    @RequestParam("page") Optional<Integer> page, @RequestParam("size") Optional<Integer> size, @RequestParam("sort") Optional<String> sort) {

        Pageable pageable =
                PageRequest.of(page.orElse(0), size.orElse(Integer.MAX_VALUE), Sort.by(sort.orElse("title")));

        Page<MoviesEntity> movies = null;
        try {
            movies = anEntityMovieRepository.findMoviesByDirector(director.replace("+"," "), pageable);
        } catch (Exception e) {

            ResponseEntity.notFound().build();
        }

        if(movies == null)
            ResponseEntity.notFound().build();

        return Objects.requireNonNull(movies).getContent();
    }

    @GetMapping("/title/{title}")
    public List<MoviesEntity> getMoviesByTitle(@PathVariable(value = "title") String title,
    @RequestParam("page") Optional<Integer> page, @RequestParam("size") Optional<Integer> size, @RequestParam("sort") Optional<String> sort) {

        Pageable pageable =
                PageRequest.of(page.orElse(0), size.orElse(Integer.MAX_VALUE), Sort.by(sort.orElse("title")));

        Page<MoviesEntity> movies = null;
        try {
            movies = anEntityMovieRepository.findMoviesByTitle(title.replace("+"," "), pageable);
        } catch (Exception e) {

            ResponseEntity.notFound().build();
        }

        if(movies == null)
            ResponseEntity.notFound().build();

        return Objects.requireNonNull(movies).getContent();
    }

    @GetMapping("/image/{id}")
    public String getMovieImageById(@PathVariable(value = "id") String id) {

        RestTemplate restTemplate = new RestTemplate();
        String imdbResult = restTemplate.getForObject(
                IMDB_FILM_API + "/" + id, String.class);
        Matcher m = imdbPattern.matcher(imdbResult);

        return (m.find())? m.group(1):"";
    }

    @GetMapping("/casting/{id}")
    public List<ActorsEntity> getMovieCastingById(@PathVariable(value = "id") String id) {

        List<ActorsEntity> actorsEntities = new ArrayList<>();
        List<CastingEntity> castingEntities = anEntityCastingRepository.findCastingEntitiesByMovieid(id);
        for (CastingEntity castingEntity : castingEntities) {
            actorsEntities.add(castingEntity.getActorsByActorid());
        }

        return actorsEntities;
    }
    
    @GetMapping("/search/{keywords}")
    public List<MoviesEntity> getMoviesByKeywords(@PathVariable("keywords") String keywords,
                                                  @RequestParam("page") Optional<Integer> page,
                                                  @RequestParam("size") Optional<Integer> size,
                                                  @RequestParam("sort") Optional<String> sort) {
        try {
            keywords = URLDecoder.decode(keywords, StandardCharsets.UTF_8.name());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    
        Pageable pageable = PageRequest.of(page.orElse(0), size.orElse(Integer.MAX_VALUE), Sort.by(sort.orElse("title")));
    
        Page<MoviesEntity> movies = null;
        try {
            movies = anEntityMovieRepository.findMoviesByKeywords(keywords, pageable);
        } catch (Exception e) {
            ResponseEntity.notFound().build();
        }
    
        if(movies == null)
            ResponseEntity.notFound().build();
    
        return Objects.requireNonNull(movies).getContent();
    }
}
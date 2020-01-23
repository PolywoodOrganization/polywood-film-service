package com.polywood.filmservice.repositories;


import com.polywood.filmservice.model.MoviesEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Pageable;

import java.util.List;


@Repository
public interface EntityMovieRepository extends PagingAndSortingRepository<MoviesEntity, Integer> {

    MoviesEntity findByMovieid(String movieid);

    Page<MoviesEntity> findAll(Pageable pageable);

    @Query(
            value = "SELECT * FROM movies m WHERE m.genre LIKE CONCAT('%',:genre,'%')",
            nativeQuery = true)
    Page<MoviesEntity> findMoviesByGenre(@Param("genre") String genre, Pageable pageable);

    @Query(
            value = "SELECT * FROM movies m WHERE m.directors LIKE CONCAT('%',:director,'%')",
            nativeQuery = true)
    Page<MoviesEntity> findMoviesByDirector(@Param("director") String director, Pageable pageable);

    @Query(
            value = "SELECT * FROM movies m WHERE m.title LIKE CONCAT('%',:title,'%')",
            nativeQuery = true)
    Page<MoviesEntity> findMoviesByTitle(@Param("title") String title, Pageable pageable);
    
    @Query(
            value = "SELECT * FROM movies m WHERE m.title LIKE CONCAT('%', :keywords,'%')" +
                    "OR m.releaseyear LIKE CONCAT('%', :keywords, '%')" +
                    "OR m.genre LIKE CONCAT('%', :keywords, '%')" +
                    "OR m.writers LIKE CONCAT('%', :keywords, '%')" +
                    "OR m.actors LIKE CONCAT('%', :keywords, '%')" +
                    "OR m.directors LIKE CONCAT('%', :keywords, '%')",
            nativeQuery = true)
    Page<MoviesEntity> findMoviesByKeywords(@Param("keywords") String keywords, Pageable pageable);
}


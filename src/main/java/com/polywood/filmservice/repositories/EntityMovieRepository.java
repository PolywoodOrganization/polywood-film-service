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

    public MoviesEntity findByMovieid(String movieid);

    public Page<MoviesEntity> findAll(Pageable pageable);

    @Query(
            value = "SELECT * FROM movies m WHERE m.genre LIKE CONCAT('%',:genre,'%')",
            nativeQuery = true)
    public List<MoviesEntity> findMoviesByGenre(@Param("genre") String genre);

    @Query(
            value = "SELECT * FROM movies m WHERE m.directors LIKE CONCAT('%',:director,'%')",
            nativeQuery = true)
    public List<MoviesEntity> findMoviesByDirector(@Param("director") String director);

}


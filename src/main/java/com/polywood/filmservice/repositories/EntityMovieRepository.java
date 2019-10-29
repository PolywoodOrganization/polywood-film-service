package com.polywood.filmservice.repositories;


import com.polywood.filmservice.model.MoviesEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Pageable;


@Repository
public interface EntityMovieRepository extends PagingAndSortingRepository<MoviesEntity, Integer> {

    public MoviesEntity findByMovieid(String movieid);

}


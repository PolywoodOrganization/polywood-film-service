package com.polywood.filmservice.repositories;

import com.polywood.filmservice.model.CastingEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EntityCastingRepository extends PagingAndSortingRepository<CastingEntity, Integer> {

    public Page<CastingEntity> findAll(Pageable pageable);

    public List<CastingEntity> findCastingEntitiesByMovieid(String movieid);

}

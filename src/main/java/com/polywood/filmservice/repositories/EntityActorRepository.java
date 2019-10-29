package com.polywood.filmservice.repositories;

import com.polywood.filmservice.model.ActorsEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EntityActorRepository extends PagingAndSortingRepository<ActorsEntity, Integer> {

    public Page<ActorsEntity> findAll(Pageable pageable);

    public List<ActorsEntity> findActorsEntitiesByActorid(Integer actorid);

}

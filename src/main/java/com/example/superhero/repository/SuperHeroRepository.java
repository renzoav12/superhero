package com.example.superhero.repository;

import com.example.superhero.model.SuperHero;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface SuperHeroRepository extends JpaRepository<SuperHero, Long> {

    @Query("SELECT s from SuperHero s WHERE lower(s.superHeroName) like %:search%")
    List<SuperHero> findSuperHeroesWithMatchName(@Param("search") final String search);
}

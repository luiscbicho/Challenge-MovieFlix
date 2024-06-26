package com.devsuperior.movieflix.repositories;

import com.devsuperior.movieflix.entities.Genre;
import com.devsuperior.movieflix.entities.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {


    @Query(value = """
            SELECT obj FROM Movie obj WHERE :genre IS NULL OR obj.genre = :genre""", countQuery = """
            SELECT count(obj) FROM Movie obj WHERE :genre IS NULL OR obj.genre = :genre""")
    public Page<Movie> searchByGenre(Genre genre, Pageable pageable);


}

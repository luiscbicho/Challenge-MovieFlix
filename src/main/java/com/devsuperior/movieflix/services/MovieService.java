package com.devsuperior.movieflix.services;

import com.devsuperior.movieflix.dto.MovieCardDTO;
import com.devsuperior.movieflix.dto.MovieDetailsDTO;
import com.devsuperior.movieflix.entities.Genre;
import com.devsuperior.movieflix.entities.Movie;
import com.devsuperior.movieflix.repositories.GenreRepository;
import com.devsuperior.movieflix.repositories.MovieRepository;
import com.devsuperior.movieflix.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class MovieService {

    @Autowired
    private MovieRepository repository;

    @Autowired
    private GenreRepository genreRepository;

    @Transactional(readOnly = true)
    public MovieDetailsDTO findById(Long id) {
        Optional<Movie> movie = repository.findById(id);
        return new MovieDetailsDTO(movie.orElseThrow(() -> new ResourceNotFoundException("Movie not found")));
    }

    @Transactional(readOnly = true)
    public Page<MovieCardDTO> searchByGenre(String genreId, Pageable pageable) {
        Genre genre = (Long.parseLong(genreId) == 0) ? null : genreRepository.getReferenceById(Long.parseLong(genreId));
        Page<Movie> page = repository.searchByGenre(genre, pageable);
        return page.map(movie -> new MovieCardDTO(movie));
    }

}

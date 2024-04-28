package com.devsuperior.movieflix.services;

import com.devsuperior.movieflix.dto.ReviewDTO;
import com.devsuperior.movieflix.entities.Movie;
import com.devsuperior.movieflix.entities.Review;
import com.devsuperior.movieflix.entities.User;
import com.devsuperior.movieflix.projections.ReviewProjection;
import com.devsuperior.movieflix.repositories.MovieRepository;
import com.devsuperior.movieflix.repositories.ReviewRepository;
import com.devsuperior.movieflix.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository repository;

    @Autowired
    private UserService userService;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<ReviewDTO> searchReviewsByMovieId(Long movieId) {
        List<ReviewProjection> result = repository.searchReviewsByMovieId(movieId);
        return result.stream().map(x -> new ReviewDTO(x)).toList();
    }

    @Transactional
    public ReviewDTO insert(ReviewDTO dto) {
        Review review = new Review();
        review.setText(dto.getText());
        Movie movie = movieRepository.getReferenceById(dto.getMovieId());
        review.setMovie(movie);
        User user = userRepository.getReferenceById(userService.getProfile().getId());
        review.setUser(user);
        review = repository.save(review);
        return new ReviewDTO(review);
    }

}

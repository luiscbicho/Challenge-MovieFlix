package com.devsuperior.movieflix.repositories;

import com.devsuperior.movieflix.entities.Review;
import com.devsuperior.movieflix.projections.ReviewProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query(nativeQuery = true, value = """
            select tb_review.id, tb_review.text, tb_movie.id as movieId, tb_user.id as userId, tb_user.name as userName, tb_user.email as userEmail
            from tb_review
            inner join tb_user on tb_review.user_id = tb_user.id
            inner join tb_movie on tb_review.movie_id = tb_movie.id
            where tb_movie.id = :movieId
            """)
    public List<ReviewProjection> searchReviewsByMovieId(Long movieId);

}

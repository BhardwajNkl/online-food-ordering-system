package dev.bhardwaj.food_order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dev.bhardwaj.food_order.entity.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

}

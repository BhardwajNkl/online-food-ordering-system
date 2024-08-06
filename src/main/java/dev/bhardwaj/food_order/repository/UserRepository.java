package dev.bhardwaj.food_order.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.bhardwaj.food_order.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByEmail(String email);
}

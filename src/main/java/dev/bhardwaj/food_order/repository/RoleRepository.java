package dev.bhardwaj.food_order.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.bhardwaj.food_order.entity.RoleEntity;

public interface RoleRepository extends JpaRepository<RoleEntity, Integer> {
	Optional<RoleEntity> findByName(String roleName);
}

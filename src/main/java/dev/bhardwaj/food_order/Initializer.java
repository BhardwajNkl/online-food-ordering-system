package dev.bhardwaj.food_order;

import java.util.Arrays;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import dev.bhardwaj.food_order.entity.RoleEntity;
import dev.bhardwaj.food_order.entity.User;
import dev.bhardwaj.food_order.repository.RoleRepository;
import dev.bhardwaj.food_order.repository.UserRepository;

@Component
public class Initializer implements CommandLineRunner {
	
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	
	
	@Override
	public void run(String... args) throws Exception {
		
		// save both roles
		RoleEntity role1 = new RoleEntity();
		RoleEntity role2 = new RoleEntity();
		
		role1.setName("NORMAL_USER");
		role2.setName("ADMIN_USER");
		try {
			roleRepository.saveAll(Arrays.asList(role1,role2));
		} catch (Exception e) {
			System.out.println("ROLES ALREADY PERSISTED!");
		}
		
		// save one admin
		
		User admin = new User();
		admin.setEmail("admin@admin.com");
		admin.setPassword(passwordEncoder.encode("adminadmin"));
		RoleEntity roles = roleRepository.findByName("ADMIN_USER").get();
        admin.setRoles(Collections.singletonList(roles));
		admin.setRoles(Arrays.asList(roles));
		try {
			userRepository.save(admin);
		} catch (Exception e) {
			System.out.println("DEFAULT ADMIN ALREADY PERSISTED!");
		}
	}

}

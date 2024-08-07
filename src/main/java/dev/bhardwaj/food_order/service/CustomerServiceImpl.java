package dev.bhardwaj.food_order.service;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import dev.bhardwaj.food_order.dto.CustomerDetailsDto;
import dev.bhardwaj.food_order.dto.CustomerDto;
import dev.bhardwaj.food_order.dto.LoginDto;
import dev.bhardwaj.food_order.dto.NewCustomerDto;
import dev.bhardwaj.food_order.dto.UpdateCustomerDto;
import dev.bhardwaj.food_order.dto.converter.CustomerConverter;
import dev.bhardwaj.food_order.entity.Customer;
import dev.bhardwaj.food_order.entity.RoleEntity;
import dev.bhardwaj.food_order.entity.User;
import dev.bhardwaj.food_order.exception.DoesNotExistException;
import dev.bhardwaj.food_order.repository.CustomerRepository;
import dev.bhardwaj.food_order.repository.RoleRepository;
import dev.bhardwaj.food_order.repository.UserRepository;
import dev.bhardwaj.food_order.security.JwtUtil;

@Service
public class CustomerServiceImpl implements CustomerService {
	
	private final CustomerRepository customerRepository;
	
	@Autowired
	private CustomerConverter customerConverter;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	
	public CustomerServiceImpl(CustomerRepository customerRepository) {
		this.customerRepository = customerRepository;
	}

	@Override
	public CustomerDto createCustomer(NewCustomerDto customerDto) {
		
		Customer customer = customerConverter.toEntity(customerDto);
		
		// create a new user first
		User user = new User();
		user.setEmail(customerDto.getEmail());
		user.setPassword(passwordEncoder.encode(customerDto.getPassword()));
		RoleEntity role = roleRepository.findByName("NORMAL_USER").orElse(null);
		user.setRoles(Arrays.asList(role));
		userRepository.save(user);
		
		customer.setUser(user);
		
		Customer createdCustomer = customerRepository.save(customer);
	
		return customerConverter.toDto(createdCustomer, CustomerDto.class);
	}

	@Override
	public CustomerDto updateCustomerDetails(UpdateCustomerDto customerDto) {
		
		Customer updatedCustomer = customerConverter.toEntity(customerDto);
		
		Customer savedUpdatedCustomer = customerRepository.save(updatedCustomer);
		
		return customerConverter.toDto(savedUpdatedCustomer, CustomerDto.class);
       	
	}

	@Override
	public void deleteCustomer(long customerId) {
		customerRepository.deleteById(customerId);
	}

	@Override
	public CustomerDetailsDto getCustomerDetails(long customerId) {
		Customer customer = customerRepository.findById(customerId)
				.orElseThrow(()->new DoesNotExistException("Customer with given id does not exist!"));
		
		return customerConverter.toDto(customer, CustomerDetailsDto.class);
		
	}
	
	@Override
	public String login(LoginDto loginDto) {
		
		Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                loginDto.getEmail(),
                loginDto.getPassword()));
		
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtUtil.generateToken(authentication);
        return token;
    }

}

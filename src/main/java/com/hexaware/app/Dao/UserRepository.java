package com.hexaware.app.Dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hexaware.app.Dto.User;
import com.hexaware.app.Enums.UserRole;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	 Optional<User> findFirstByEmail(String email);

	    User findByUserRole(UserRole userRole);
}

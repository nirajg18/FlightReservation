package com.flightReservation.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.flightReservation.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

	User findByEmail(String emailid);

}

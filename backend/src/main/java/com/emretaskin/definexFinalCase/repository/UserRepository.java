package com.emretaskin.definexFinalCase.repository;

import com.emretaskin.definexFinalCase.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByIdNumber(String idNumber);

}

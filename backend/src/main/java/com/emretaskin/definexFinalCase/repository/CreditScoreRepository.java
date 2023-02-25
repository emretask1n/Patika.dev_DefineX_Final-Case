package com.emretaskin.definexFinalCase.repository;

import com.emretaskin.definexFinalCase.entity.CreditScore;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CreditScoreRepository extends JpaRepository<CreditScore, Long> {

    Optional<CreditScore> findById(Long id);
}

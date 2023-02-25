package com.emretaskin.definexFinalCase.repository;

import com.emretaskin.definexFinalCase.entity.LoanForm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface LoanFormRepository extends JpaRepository<LoanForm, Long> {

    Optional<LoanForm> findByUserIdNumberAndBirthDate(String idNumber, Date birthDate);

    @Query("SELECT lf FROM LoanForm lf WHERE lf.user.idNumber = :idNumber AND lf.birthDate = :birthDate")
    List<LoanForm> findAllByIdNumberAndBirthDate(@Param("idNumber") String idNumber, @Param("birthDate") Date birthDate);
}

package com.emretaskin.definexFinalCase.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "credit_scores")
public class CreditScore {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int creditScore;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "creditScore")
    private User user;

    public CreditScore(Long id, int creditScore) {
        this.id = id;
        this.creditScore = creditScore;
    }

    public CreditScore(int creditScore) {
        this.creditScore = creditScore;
    }

}

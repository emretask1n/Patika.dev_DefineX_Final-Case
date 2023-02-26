package com.emretaskin.definexFinalCase.service.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CreditScoreServiceImplTest {

    @Test
    @DisplayName("Test generating credit score successfully")
    public void testGenerateCreditScore() {
        CreditScoreServiceImpl creditScoreService = new CreditScoreServiceImpl();
        int creditScore = creditScoreService.generateCreditScore();
        assertTrue(creditScore >= 0 && creditScore <= 1500);
    }
}
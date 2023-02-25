package com.emretaskin.definexFinalCase.service.impl;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CreditScoreServiceImplTest {

    @Test
    public void testGenerateCreditScore() {
        CreditScoreServiceImpl creditScoreService = new CreditScoreServiceImpl();
        int creditScore = creditScoreService.generateCreditScore();
        assertTrue(creditScore >= 0 && creditScore <= 1500);
    }
}
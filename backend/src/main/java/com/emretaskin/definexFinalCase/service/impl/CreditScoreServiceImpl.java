package com.emretaskin.definexFinalCase.service.impl;

import com.emretaskin.definexFinalCase.service.CreditScoreService;
import org.springframework.stereotype.Service;

@Service
public class CreditScoreServiceImpl implements CreditScoreService {

    @Override
    public int generateCreditScore() {
        return (int) (Math.random() * 1501);
    }

}

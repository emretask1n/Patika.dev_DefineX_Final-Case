package com.emretaskin.definexFinalCase.service.impl;

import com.emretaskin.definexFinalCase.service.CreditScoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class CreditScoreServiceImpl implements CreditScoreService {

    @Override
    public int generateCreditScore() {
        return (int) (Math.random() * 1501);
    }

}

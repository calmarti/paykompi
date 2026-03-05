package com.calmarti.paykompi.domain.payment.service;


import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class ExternalPaymentApiSimulator {

    private static final double FAILURE_RATE = 0.05; // 5%
    private final Random random = new Random();

    public void authorizePayment(){
        if(random.nextDouble() < 0.05){
            throw new RuntimeException("External payment validation failed");
        }
    }

}

package com.calmarti.paykompi.domain.payment.service;


import com.calmarti.paykompi.common.exception.ExternalPaymentException;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class ExternalPaymentApiSimulator {

    private static final double FAILURE_RATE = 0.05; // 5%
    private final Random random = new Random();

    public void approvePayment(){
        if(random.nextDouble() < 0.05){
            throw new ExternalPaymentException("External payment validation failed");
        }
    }

}

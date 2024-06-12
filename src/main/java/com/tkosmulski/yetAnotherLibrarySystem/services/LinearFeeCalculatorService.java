package com.tkosmulski.yetAnotherLibrarySystem.services;

import com.tkosmulski.yetAnotherLibrarySystem.services.serviceInterfaces.LateFeeCalculator;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Service("linearFeeCalculatorService")
public class LinearFeeCalculatorService implements LateFeeCalculator {
    static final BigDecimal BASE_LATE_FEE = new BigDecimal(10);
    static final BigDecimal PER_DAY_FEE = new BigDecimal("2.50");

    @Override
    public BigDecimal calculate(Date dueDate, Date returnDate) {

        long daysBetween = TimeUnit.DAYS.convert(returnDate.getTime() - dueDate.getTime(), TimeUnit.MILLISECONDS);

        if(daysBetween <= 0) {
            return new BigDecimal(0);
        }

        return BASE_LATE_FEE.add(PER_DAY_FEE.multiply(new BigDecimal(daysBetween)));
    }
}

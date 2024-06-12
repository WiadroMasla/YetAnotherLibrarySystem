package com.tkosmulski.yetAnotherLibrarySystem.services.serviceInterfaces;

import java.math.BigDecimal;
import java.util.Date;

public interface LateFeeCalculator {
    BigDecimal calculate(Date dueDate, Date returnDate);
}

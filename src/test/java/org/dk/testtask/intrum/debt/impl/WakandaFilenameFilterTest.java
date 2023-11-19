package org.dk.testtask.intrum.debt.impl;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

class WakandaFilenameFilterTest {
    @Test
    public void wakandaFilenameFilterAcceptTest(){
        LocalDate date = LocalDate.now();
        WakandaDebtDataProvider.WakandaFilenameFilter filter = new WakandaDebtDataProvider().new WakandaFilenameFilter(date);
        assertFalse(filter.accept(null,"WK_payouts_" + date.minusDays(1).format(DateTimeFormatter.BASIC_ISO_DATE) + "_000000.csv"),
                "Wrong date and correct time should have been filtered.");
        assertTrue(filter.accept(null,"WK_payouts_" + date.format(DateTimeFormatter.BASIC_ISO_DATE) + "_000000.csv"),
                "Correct date and time should have passed.");
        assertFalse(filter.accept(null,"WK_payouts_" + date.format(DateTimeFormatter.BASIC_ISO_DATE) + "_240100.csv"),
                "Invalid time should have been filtered.");
        assertFalse(filter.accept(null,"WK_payouts_" + date.format(DateTimeFormatter.BASIC_ISO_DATE) + "_90000.csv"),
                "Wrong time format should have been filtered.");
    }
}
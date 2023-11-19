package org.dk.testtask.intrum.debt.impl;

import com.opencsv.bean.CsvBindByName;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
public class WakandaCsvFormat{
    @CsvBindByName(column = "Company tax number")
    String id;

    @CsvBindByName(column = "Payment Date")
    String date;

    @CsvBindByName(column = "Amount")
    String amount;
}

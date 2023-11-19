package org.dk.testtask.intrum.data;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PayoutDTO {
    private String companyIdentityNumber;
    private String paymentDate;
    private String paymentAmount;
}
package org.dk.testtask.intrum.debt;

import org.dk.testtask.intrum.data.PayoutDTO;

import java.util.List;

public interface DebtDataProvider {
    public List<PayoutDTO> getPayouts();
}

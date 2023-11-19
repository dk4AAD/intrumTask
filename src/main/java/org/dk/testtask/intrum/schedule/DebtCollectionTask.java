package org.dk.testtask.intrum.schedule;

import lombok.extern.slf4j.Slf4j;
import org.dk.testtask.intrum.data.HttpDataSender;
import org.dk.testtask.intrum.debt.DebtDataProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

@Component
@Slf4j
public class DebtCollectionTask implements Runnable {

    private final List<DebtDataProvider> debtDataProviders;
    private final ReentrantLock lock;
    private final HttpDataSender dataSender;

    public DebtCollectionTask(@Autowired List<DebtDataProvider> debtDataProviders, @Autowired HttpDataSender dataSender){
        this.lock = new ReentrantLock();
        this.dataSender = dataSender;
        this.debtDataProviders = debtDataProviders;
    }

    @Override
    public void run() {
        if (lock.tryLock()) {
            try {
                for(DebtDataProvider provider : debtDataProviders){
                    provider.getPayouts().forEach(dataSender::send);
                }
            } finally {
                lock.unlock();
            }
        } else {
            log.warn("Attempted to run processing while previous did not finish");
        }
    }
}

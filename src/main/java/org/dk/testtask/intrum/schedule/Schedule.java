package org.dk.testtask.intrum.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class Schedule{
    @Autowired
    private TaskScheduler taskScheduler;

    @Autowired private DebtCollectionTask regularDebtCollectionTask;

    @PostConstruct
    public void scheduleTasks() {
        taskScheduler.schedule(regularDebtCollectionTask, new CronTrigger("0 0 0 * * *"));
    }
}

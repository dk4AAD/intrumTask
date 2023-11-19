package org.dk.testtask.intrum.controller;

import lombok.extern.slf4j.Slf4j;
import org.dk.testtask.intrum.schedule.DebtCollectionTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.io.IOException;
import java.time.Instant;

@Slf4j
@RestController
public class ManualProcessingRequestController {
    private final TaskScheduler scheduler;
    private final Runnable debtCollectionTask;

    public ManualProcessingRequestController(@Autowired TaskScheduler scheduler, @Autowired DebtCollectionTask debtCollectionTask){
        this.scheduler = scheduler;
        this.debtCollectionTask = debtCollectionTask;
    }

    @GetMapping("/process")
    public void processCsvFile() throws IOException {
        scheduler.schedule(debtCollectionTask, Instant.now());
    }
}

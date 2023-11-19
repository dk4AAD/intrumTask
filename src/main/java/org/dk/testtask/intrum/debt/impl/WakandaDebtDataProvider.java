package org.dk.testtask.intrum.debt.impl;

import com.opencsv.bean.CsvToBeanBuilder;
import lombok.extern.slf4j.Slf4j;
import org.dk.testtask.intrum.data.PayoutDTO;
import org.dk.testtask.intrum.debt.DebtDataProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Component
public class WakandaDebtDataProvider implements DebtDataProvider {

    @Value("#{environment.WK_DATA_MOUNT}")
    private String path;

    @PostConstruct
    public void checkEnvVar() {
        if (this.path == null || this.path.isBlank()) {
            throw new IllegalArgumentException("Wakanda data path must be configured as WK_DATA_MOUNT environment variable");
        }
    }

    @Override
    public List<PayoutDTO> getPayouts() {
        List<PayoutDTO> payouts = new ArrayList<>();
        for(File csvFile : listFiles()) {
            try (FileReader fr = new FileReader(csvFile,StandardCharsets.ISO_8859_1)){
                new CsvToBeanBuilder<WakandaCsvFormat>(fr)
                        .withType(WakandaCsvFormat.class)
                        .withSeparator(';')
                        .withQuoteChar('"')
                        .build()
                        .stream()
                        .map(WakandaDebtDataProvider::apply)
                        .forEach(payouts::add);
            } catch (IOException e) {
                log.error("Could not open file " + csvFile.getAbsolutePath() + " for reading.", e);
            }
        }
        return payouts;
    }

    private List<File> listFiles() {
        File[] files = new File(path).listFiles(new WakandaFilenameFilter(LocalDate.now().minusDays(1)));
        return (null == files)?new ArrayList<File>(): Arrays.asList(files);
    }

    private static PayoutDTO apply(WakandaCsvFormat wkCsv) {
        return new PayoutDTO(wkCsv.id, wkCsv.date,wkCsv.amount);
    }

    class WakandaFilenameFilter implements FilenameFilter{

        private final LocalDate date;

        WakandaFilenameFilter(LocalDate date) {
            this.date = date;
        }

        @Override
        public boolean accept(File dir, String name) {
            String wakandaFilePattern = "^WK_payouts_"
                    + date.format(DateTimeFormatter.BASIC_ISO_DATE)
                    +"_([0-1][0-9]|2[0-3])[0-5][0-9][0-5][0-9]\\.csv$";
            return name.matches(wakandaFilePattern);
        }
    }
}

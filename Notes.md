# Notes
Particular implementations for debt data collectors implement DebtDataProvider interface (default one is placed into org.dk.testtask.intrum.debt.impl package).  
The default implementation requires WK_DATA_MOUNT environment variable to be set. CSV files should be placed in corresponding folder.  
All files are filtered, the default implementation only takes into account and reads files generated on the previous date.  
The program is set up to run the processing of payout CSVs once a day at 00:00:00, which corresponds to the task demands.  
All the payouts are sent to the debt collection system in async requests.


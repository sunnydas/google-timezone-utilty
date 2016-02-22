package com.sunny.util.tzapi;

import com.sunny.util.tzapi.parser.CSVParser;
import com.sunny.util.tzapi.file.FileUtil;
import com.sunny.util.tzapi.dao.CSVRow;
import com.sunny.util.tzapi.time.TimeUtilities;

import java.lang.System;
import java.util.List;

/**
 * Created by SUND on 05-03-2015.
 */
public class App {

    public App(String inputFile) {
        this.inputFile = inputFile;
    }

    private String inputFile;

    /**
     *
     *
     * @return
     */
    public boolean runApplication(){
      //Fault tolerance can be improved by having a continue on error option (not available in current version)
      boolean success = false;
      //Step 1 : Read input file and get CSV Daos
      CSVParser csvParser = new CSVParser(this.inputFile);
      try {
          List<CSVRow> csvRows = csvParser.parse();
          //Step 2:
          // Now for each row update with localized time zone and localized time
          for(CSVRow csvRow : csvRows){
            if(csvRow != null) {
                TimeUtilities.updateLocalizedTimeZone(csvRow);
                if(csvRow.getLocalizedTimeZone() != null){
                  TimeUtilities.updateLocalizedTime(csvRow);
                }
            }
          }
          if(csvRows != null) {
              //Step 3
              // Update the same file with localized data
              FileUtil.writeToFile(this.inputFile, csvRows);
              success = true;
          }
      } catch (Exception e) {
          System.err.println(e);
      }
      return success;
    }

    public static void main(String[] args) {
      // singlethreaded version
      String file = System.getProperty("user.home") + System.getProperty("file.separator") + "MyCsv";
      App timeZoneApplication = new App(file);
      System.out.println(timeZoneApplication.runApplication());
    }

}

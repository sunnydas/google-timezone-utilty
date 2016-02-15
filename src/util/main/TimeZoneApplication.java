package util.main;

import com.sunny.trial.parser.CSVParser;
import util.file.util.FileUtil;
import com.sunny.trial.parser.parser.dao.CSVRow;
import util.time.TimeUtilities;

import java.util.List;

/**
 * Created by SUND on 05-03-2015.
 */
public class TimeZoneApplication {

    public TimeZoneApplication(String inputFile) {
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
      System.setProperty("googleapiclientid","<api key here>");
      String file = "MyCsv";
      TimeZoneApplication timeZoneApplication = new TimeZoneApplication(file);
      System.out.println(timeZoneApplication.runApplication());
    }

}

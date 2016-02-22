package com.sunny.util.tzapi.file;

import com.sunny.util.tzapi.dao.CSVRow;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * Created by SUND on 05-03-2015.
 */
public class FileUtil {

    /**
     *
     * @param fileName
     * @param csvRows
     */
    public static void writeToFile(String fileName,List<CSVRow> csvRows) throws IOException {
        File file = new File(fileName);
        BufferedWriter bufferedWriter = null;
        try {
            FileWriter fileWriter = new FileWriter(file);
            bufferedWriter = new BufferedWriter(fileWriter);
            StringBuilder stringBuilder = new StringBuilder();
            for(int i = 0 ; i < csvRows.size();i++){
              CSVRow csvRow = csvRows.get(i);
              if(csvRow != null){
                 stringBuilder.append(csvRow.getUtcDateTime());
                 stringBuilder.append(",");
                 stringBuilder.append(csvRow.getLatitude());
                 stringBuilder.append(",");
                 stringBuilder.append(csvRow.getLongitude());
                 stringBuilder.append(",");
                 stringBuilder.append(csvRow.getLocalizedTimeZone());
                 stringBuilder.append(",");
                 stringBuilder.append(csvRow.getLocalizedTime());
                 if(i < (csvRows.size() - 1)) {
                     stringBuilder.append(System.getProperty("line.separator"));
                 }
              }
            }
            bufferedWriter.write(stringBuilder.toString());
            bufferedWriter.flush();
        } finally {
            if(bufferedWriter != null){
              bufferedWriter.close();
            }
        }
    }


}

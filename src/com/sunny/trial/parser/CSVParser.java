package com.sunny.trial.parser;

import com.sunny.trial.parser.parser.dao.CSVRow;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by SUND on 04-03-2015.
 */
public class CSVParser {

    public CSVParser(String csvFileName) {
        this.csvFileName = csvFileName;
    }

    private String csvFileName;

    private List<CSVRow> csvRows;


    /**
     *
     * @return
     * @throws IOException
     */
    public List<CSVRow> parse() throws IOException{
        this.csvRows = new ArrayList<CSVRow>();
        File file = new File(csvFileName);
        FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String line = null;
        try {
            while ((line = bufferedReader.readLine()) != null) {
                String[] tokens = line.split(",");
                if (tokens != null) {
                    CSVRow csvRow = new CSVRow();
                    if (tokens.length >= 3) {
                        csvRow.setUtcDateTime(tokens[0]);
                        csvRow.setLatitude(tokens[1]);
                        csvRow.setLongitude(tokens[2]);
                    }
                    this.csvRows.add(csvRow);
                }
            }
        } finally{
            if(bufferedReader != null) {
                bufferedReader.close();
            }
        }
        return this.csvRows;
    }

    public static void main(String[] args)throws Exception {
        CSVParser csvParser = new CSVParser("MyCsv");
        System.out.println(csvParser.parse());
    }

}

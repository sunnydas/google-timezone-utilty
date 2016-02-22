package com.sunny.util.tzapi.time;

import com.sunny.util.tzapi.dao.CSVRow;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by SUND on 04-03-2015.
 */
public class TimeUtilities {


    private static final String timezoneBaseURL = "https://maps.googleapis.com/maps/api/timezone/json";


    /**
     *
     * @param csvRow
     * @throws IOException
     */
    public static void updateLocalizedTimeZone(CSVRow csvRow) throws IOException {
        String clientID = System.getProperty("googleapiclientid");
        String latitude = csvRow.getLatitude();
        String longitude = csvRow.getLongitude();
        String timeStamp = getTimeStamp(csvRow.getUtcDateTime());
        StringBuilder restURL = new StringBuilder(timezoneBaseURL);
        restURL.append("?location=").append(latitude).append(",").append(longitude).append("&timestamp=").append(timeStamp).append("&key=").append(clientID);
        //System.out.println(restURL.toString());
        String response = getRESTResponse(restURL.toString());
        //System.out.println(" response = " + response);
        JSONParser jsonParser = new JSONParser();
        Object parsed = null;
        try {
            parsed = jsonParser.parse(response);
        } catch (org.json.simple.parser.ParseException e) {
            e.printStackTrace();
        }
        JSONObject jsonObject = (JSONObject) parsed;
        if(parsed != null){
           String tzId = (String) ((JSONObject) parsed).get("timeZoneId");
           csvRow.setLocalizedTimeZone(tzId);
        }
    }


    /**
     *
     * @param csvRow
     *
     */
    public static void updateLocalizedTime(CSVRow csvRow) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-dd hh:mm:ss");
        String time = csvRow.getUtcDateTime();
        sdf.setTimeZone(TimeZone.getTimeZone(csvRow.getLocalizedTimeZone()));
        Date date = sdf.parse(time);
        csvRow.setLocalizedTime(sdf.format(date));
    }


    public static String getRESTResponse(String restURL) throws IOException {
        StringBuilder responseString = new StringBuilder();
        HttpClient client = new DefaultHttpClient();
        HttpGet request = new HttpGet(restURL);
        org.apache.http.HttpResponse response = null;
        response = client.execute(request);
        BufferedReader rd = null;
        rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        String line = null;
        try {
            while ((line = rd.readLine()) != null) {
                responseString.append(line);
            }
        } finally {
            if(rd != null){
                rd.close();
            }
        }
        return responseString.toString();
    }



    public static String getTimeStamp(String dateTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-dd hh:mm:ss");
        Date date = null;
        try {
            date = sdf.parse(dateTime);
        } catch (ParseException e) {
            //ignore and print
            e.printStackTrace();
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return ""+(calendar.getTimeInMillis()/1000);
    }

}

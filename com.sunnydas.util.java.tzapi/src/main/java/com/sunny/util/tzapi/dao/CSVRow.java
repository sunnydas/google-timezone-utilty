package com.sunny.util.tzapi.dao;

/**
 * Created by SUND on 04-03-2015.
 */
public class CSVRow {

    private String utcDateTime;

    private String latitude;

    private String longitude;

    private String localizedTimeZone;

    private String localizedTime;

    public String getLocalizedTimeZone() {
        return localizedTimeZone;
    }

    public void setLocalizedTimeZone(String localizedTimeZone) {
        this.localizedTimeZone = localizedTimeZone;
    }

    public String getLocalizedTime() {
        return localizedTime;
    }

    public void setLocalizedTime(String localizedTime) {
        this.localizedTime = localizedTime;
    }

    public String getUtcDateTime() {
        return utcDateTime;

    }

    public void setUtcDateTime(String utcDateTime) {
        this.utcDateTime = utcDateTime;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return "CSVRow{" +
                "utcDateTime='" + utcDateTime + '\'' +
                ", latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CSVRow csvRow = (CSVRow) o;

        if (!latitude.equals(csvRow.latitude)) return false;
        if (!longitude.equals(csvRow.longitude)) return false;
        if (!utcDateTime.equals(csvRow.utcDateTime)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = utcDateTime.hashCode();
        result = 31 * result + latitude.hashCode();
        result = 31 * result + longitude.hashCode();
        return result;
    }
}

package edu.mkit.myapplication.model;

public class Weather {

    private String cityName;

    private String weatherName;

    private String heightTemp;

    private String lowTemp;

    private String wind;

    private String pm25;

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getWeatherName() {
        return weatherName;
    }

    public void setWeatherName(String weatherName) {
        this.weatherName = weatherName;
    }

    public String getHeightTemp() {
        return heightTemp;
    }

    public void setHeightTemp(String heightTemp) {
        this.heightTemp = heightTemp;
    }

    public String getLowTemp() {
        return lowTemp;
    }

    public void setLowTemp(String lowTemp) {
        this.lowTemp = lowTemp;
    }

    public String getWind() {
        return wind;
    }

    public void setWind(String wind) {
        this.wind = wind;
    }

    public String getPm25() {
        return pm25;
    }

    public void setPm25(String pm25) {
        this.pm25 = pm25;
    }

    public Weather() {
    }

    public Weather(String cityName, String weatherName, String heightTemp, String lowTemp, String wind, String pm25) {
        this.cityName = cityName;
        this.weatherName = weatherName;
        this.heightTemp = heightTemp;
        this.lowTemp = lowTemp;
        this.wind = wind;
        this.pm25 = pm25;
    }
}

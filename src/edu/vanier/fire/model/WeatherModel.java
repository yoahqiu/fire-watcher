package edu.vanier.fire.model;

/**
 *
 * @author cstuser
 */
public class WeatherModel {

    private double temperature = 0;
    private double windSpeed = 0;
    private double windDeg = 0;
    private double rainVol = 0;
    private double snowVol = 0;

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public double getWindDeg() {
        return windDeg;
    }

    public void setWindDeg(double windDeg) {
        this.windDeg = windDeg;
    }

    public double getRainVol() {
        return rainVol;
    }

    public void setRainVol(double rainVol) {
        this.rainVol = rainVol;
    }

    public double getSnowVol() {
        return snowVol;
    }

    public void setSnowVol(double snowVol) {
        this.snowVol = snowVol;
    }

    @Override
    public String toString() {
        return "WeatherModel{" + "temperature=" + temperature + ", windSpeed=" + windSpeed + ", windDeg=" + windDeg + ", rainVol=" + rainVol + ", snowVol=" + snowVol + '}';
    }    
}

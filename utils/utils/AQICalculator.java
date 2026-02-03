package com.ess.utils;

public class AQICalculator {


    /**
     * 计算aqi值
     * @param pm25
     * @param pm10
     * @param so2
     * @param no2
     * @param co
     * @param o3
     * @return
     */
    public static int calculateAQI(double pm25, double pm10, double so2, double no2, double co, double o3){

    int [] aqis={
            calculateAQI("PM25", pm25),
            calculateAQI("PM10", pm10),
            calculateAQI("SO2", so2),
            calculateAQI("NO2", no2),
            calculateAQI("CO", co),
            calculateAQI("O3", o3)

    };
    int max = aqis[0];
    for(int aqi:aqis){
        if(aqi>max)
            max=aqi;
    }

    return max;
}



    public static int calculateAQI(String pollutantType, double concentration) {
        switch (pollutantType.toUpperCase()) {
            case "PM25":
                return calculateAQIFromPM25(concentration);
            case "PM10":
                return calculateAQIFromPM10(concentration);
            case "SO2":
                return calculateAQIFromSO2(concentration);
            case "NO2":
                return calculateAQIFromNO2(concentration);
            case "CO":
                return calculateAQIFromCO(concentration);
            case "O3":
                return calculateAQIFromO3(concentration);
            default:
                throw new IllegalArgumentException("未知污染物类型: " + pollutantType);
        }
    }

    private static int calculateAQIFromPM25(double pm25) {
        if (pm25 <= 35) return 50;
        else if (pm25 <= 75) return 100;
        else if (pm25 <= 150) return 150;
        else if (pm25 <= 250) return 200;
        else if (pm25 <= 350) return 300;
        else return 500;
    }

    private static int calculateAQIFromPM10(double pm10) {
        if (pm10 <= 50) return 50;
        else if (pm10 <= 150) return 100;
        else if (pm10 <= 250) return 150;
        else if (pm10 <= 350) return 200;
        else if (pm10 <= 420) return 300;
        else return 500;
    }

    private static int calculateAQIFromSO2(double so2) {
        if (so2 <= 50) return 50;
        else if (so2 <= 150) return 100;
        else if (so2 <= 475) return 150;
        else if (so2 <= 800) return 200;
        else if (so2 <= 1600) return 300;
        else return 500;
    }

    private static int calculateAQIFromNO2(double no2) {
        if (no2 <= 40) return 50;
        else if (no2 <= 80) return 100;
        else if (no2 <= 180) return 150;
        else if (no2 <= 280) return 200;
        else if (no2 <= 400) return 300;
        else return 500;
    }

    private static int calculateAQIFromCO(double co) {
        if (co <= 2) return 50;
        else if (co <= 4) return 100;
        else if (co <= 14) return 150;
        else if (co <= 24) return 200;
        else if (co <= 36) return 300;
        else return 500;
    }

    private  static int calculateAQIFromO3(double o3) {
        if (o3 <= 100) return 50;
        else if (o3 <= 160) return 100;
        else if (o3 <= 200) return 150;
        else if (o3 <= 300) return 200;
        else if (o3 <= 400) return 300;
        else return 500;
    }
}

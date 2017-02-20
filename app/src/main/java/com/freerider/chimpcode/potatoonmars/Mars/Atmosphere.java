package com.freerider.chimpcode.potatoonmars.Mars;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Random;

/**
 * Created by root on 2/18/17.
 */

public class Atmosphere {

    private Phoenix satellite;
    private double groundTemperature;
    private double atmosphereTemperature;
    private double O2Concentration;
    private double CO2Concentration;

    public Atmosphere(Phoenix satellite) {
        this.satellite = satellite;
    }

    public void refreshValues() {
        try {
            this.satellite.systemsCommunication.write("A".getBytes(), 1);
            byte[] buffer = new byte[44];
            this.satellite.systemsCommunication.read(buffer, 44);
            String str = new String(buffer, StandardCharsets.UTF_8);
            String[] tempArray = str.trim().split(",");


            groundTemperature  = Float.valueOf(tempArray[0]);

            atmosphereTemperature  = Float.valueOf(tempArray[2]);

            if ((groundTemperature==-127.0?1:0  + atmosphereTemperature==-127.0?1:0)>0) {
                Random rnd = new Random();

                double cheatValue = 0.0;
                if (groundTemperature!=-127.0) {
                    cheatValue = groundTemperature;
                }else if (atmosphereTemperature!=-127.0){
                    cheatValue = atmosphereTemperature;
                }

                if (groundTemperature==-127.0) {
                    groundTemperature = cheatValue + rnd.nextDouble();
                }

                if (atmosphereTemperature==-127.0) {
                    atmosphereTemperature = cheatValue + rnd.nextDouble();
                }
            }

            CO2Concentration  = Float.valueOf(tempArray[3]);
            O2Concentration  = Float.valueOf(tempArray[4]);

        } catch (IOException | IndexOutOfBoundsException | NumberFormatException e) {
            e.printStackTrace();
        }

    }

    public double getGroundTemperature(boolean withRefresh) {
        if (withRefresh) {
            refreshValues();
        }
        return groundTemperature;
    }

    public void setGroundTemperature(double groundTemperature) {
        this.groundTemperature = groundTemperature;
    }

    public double getAtmosphereTemperature(boolean withRefresh) {
        if (withRefresh) {
            refreshValues();
        }
        return atmosphereTemperature;
    }

    public void setAtmosphereTemperature(double atmosphereTemperature) {
        this.atmosphereTemperature = atmosphereTemperature;
    }

    public double getCO2Concentration(boolean withRefresh) {
        if (withRefresh) {
            refreshValues();
        }
        return CO2Concentration;
    }

    public void setCO2Concentration(double CO2Concentration) {
        this.CO2Concentration = CO2Concentration;
    }

    public double getO2Concentration(boolean withRefresh) {
        if (withRefresh) {
            refreshValues();
        }
        return O2Concentration;
    }

    public void setO2Concentration(double o2Concentration) {
        O2Concentration = o2Concentration;
    }
}

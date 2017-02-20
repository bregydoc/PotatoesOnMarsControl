package com.freerider.chimpcode.potatoonmars.Mars;

/**
 * Created by Bregy on 2/18/17.
 */

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EarthCommunication {

    public int pingCheck;
    FirebaseDatabase database;

    public EarthCommunication() {

        database = FirebaseDatabase.getInstance();
        DatabaseReference pingReference = database.getReference("pingState");

        pingReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists() ){
                    int value = ((Long)(dataSnapshot.getValue())).intValue();
                    pingCheck = value;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void sendCurrentMarsState(Weather weather, Atmosphere atmosphere, boolean withRefresh) {
        DatabaseReference currentState = database.getReference("current_state");
        currentState.child("co2_value").setValue(atmosphere.getCO2Concentration(withRefresh));
        currentState.child("o2_value").setValue(atmosphere.getO2Concentration(withRefresh));

        currentState.child("temperature_point_1").setValue(atmosphere.getAtmosphereTemperature(withRefresh)); // AMBIENT TEMPERATURE?
        currentState.child("temperature_point_3").setValue(atmosphere.getGroundTemperature(withRefresh)); //GROUND TEMPERATURE?

        currentState.child("o2_valve_state").setValue(weather.getO2ValveState());
        currentState.child("co2_valve_state").setValue(weather.getCO2ValveState());
        currentState.child("vacuum_valve_state").setValue(weather.getVacuumValveState());

        currentState.child("vacuum_system_state").setValue(weather.getVacuumSystemState());
        currentState.child("vacuum_cooling_state").setValue(weather.getCoolingsystemState());


    }
}

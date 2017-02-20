package com.freerider.chimpcode.potatoonmars;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.freerider.chimpcode.potatoonmars.Artificial.DebugConsole;
import com.freerider.chimpcode.potatoonmars.Mars.DateCycle;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ControlSystem extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control_system);

        //-----------------------------------------------------
        // Cosas por hacer:
        // Modificar bien los pines conectores de las valvulas y sistemas
        // YA SE LO QUE ESTA OCUERRIENDO
        // El arduino colapsa, no es la raspi la que se congela, el hecho de que el arduino deje de entregarle data, hace que no se actualize ni colapsa
        // la base de datos (y por ende, tampoco la web), ni los algoritmos de control

        //RESET POR SOFTWARE Y RESET AUTOMATICO!


        TextView txtDebugConsole = (TextView) findViewById(R.id.txtDebugConsole);
        DateCycle dateCycle = new DateCycle();
        DebugConsole console = new DebugConsole(txtDebugConsole);

        //console.printLogMessage("Current date", dateCycle.calendar.getTimeZone().getDisplayName());
        //console.printLogMessage("Current date", dateCycle.calendar.toString());

        (new Thread(() -> {
            while (!Thread.interrupted())
                try {
                    Thread.sleep(1000);
                    runOnUiThread(() -> {
                        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                        Calendar cal = Calendar.getInstance();

                        try {
                            console.printLogMessage("Current date", dateCycle.getCurrentDate().toString());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    });
                }
                catch (InterruptedException e) {
                    // ooops
                }
        })).start();



    }


}

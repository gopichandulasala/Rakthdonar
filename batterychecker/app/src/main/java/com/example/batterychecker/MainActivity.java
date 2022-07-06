package com.example.batterychecker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
Button b1,b2;
EditText battery;
int value,batteryper;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        b1=findViewById(R.id.start);
        b2=findViewById(R.id.stop);
        battery=findViewById(R.id.battery);
        String n=battery.getText().toString();
        value=Integer.parseInt(n);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int flag=0;
                batteryper=baterylevel();
                if(value<batteryper){
                    Toast.makeText(getApplicationContext(), "Battery percentage is already reached please reenter", Toast.LENGTH_SHORT).show();
                }
                else{
                    flag=1;
                }
                if (flag == 1) {
                    Intent in = new Intent(getBaseContext(), Myservice.class);
                    in.putExtra("value", value);
                    in.putExtra("battery",batteryper);
                    startService(in);
                }

            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopService(new Intent(getBaseContext(),Myservice.class));
            }
        });


    }
    public int baterylevel(){
        Intent blevel= registerReceiver(null,new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        int level=blevel.getIntExtra(BatteryManager.EXTRA_LEVEL,-1);
        int scale=blevel.getIntExtra(BatteryManager.EXTRA_SCALE,-1);
        if(level==-1 || scale==-1){
            return 50;
        }
        return (level/scale)*100;
    }

}
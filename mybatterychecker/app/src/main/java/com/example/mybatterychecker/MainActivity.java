package com.example.mybatterychecker;

import static java.lang.Double.parseDouble;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.BatteryManager;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private TextView bp;
    private Button st,sto;
    Handler han;
    private double lev;
    Runnable runnable;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bp=findViewById(R.id.battery);
        bp.setInputType(InputType.TYPE_CLASS_NUMBER);
        st=findViewById(R.id.start);
        sto=findViewById(R.id.stop);
        final MediaPlayer mp=MediaPlayer.create(getApplicationContext(),R.raw.battery_charged);

        st.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                runnable=new Runnable() {
                    @Override
                    public void run() {
                        if(!bp.getText().toString().isEmpty()){
                            lev=Double.parseDouble(bp.getText().toString());
                        }


                        int level=(int) baterylevel();
                        if(lev==level){
                            Toast.makeText(getApplicationContext(), "sucess", Toast.LENGTH_SHORT).show();
                            mp.start();
                            han.removeCallbacks(runnable);
                        }
                        else{
                            mp.pause();
                        }

                        han.postDelayed(runnable,5000);
                    }
                };
                han=new Handler();
                han.postDelayed(runnable,0);

            }
        });
        sto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                han.removeCallbacks(runnable);
            }
        });




    }
    public float baterylevel(){
        Intent blevel= registerReceiver(null,new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        int level=blevel.getIntExtra(BatteryManager.EXTRA_LEVEL,-1);
        int scale=blevel.getIntExtra(BatteryManager.EXTRA_SCALE,-1);
        if(level==-1 || scale==-1){
            return 50.0f;
        }
        return ((float) level/(float) scale)*100.0f;
    }
}
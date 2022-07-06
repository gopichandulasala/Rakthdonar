package com.example.practicechecker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.os.BatteryManager;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
Button b1,b2;
EditText bp;
Double lev;
Handler han;
Runnable runnable;
int flag=0;

Myservice sp=new Myservice();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final MediaPlayer mp=MediaPlayer.create(getApplicationContext(),Settings.System.DEFAULT_RINGTONE_URI);
        mp.setLooping(true);
        setContentView(R.layout.activity_main);
        b1=findViewById(R.id.start);
        b2=findViewById(R.id.stop);
        bp=findViewById(R.id.editTextNumber);
        b1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    runnable=new Runnable() {
                        @Override
                        public void run() {
                            if (!bp.getText().toString().isEmpty()) {
                                lev = Double.parseDouble(bp.getText().toString());


                            } else {
                                han.removeCallbacks(runnable);
                            }
                            if (check(lev)) {
                                han.removeCallbacks(runnable);
                                int level = (int) baterylevel();
                                if (lev == level) {
                                    Toast.makeText(getBaseContext(), "sucess", Toast.LENGTH_SHORT).show();
                                    startService(new Intent(getApplicationContext(), Myservice.class));
                                    han.removeCallbacks(runnable);
                                }
                            }
                        }
                    };
                    han=new Handler();
                    han.postDelayed(runnable,1000);


                }
            });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                han.removeCallbacks(runnable);
                Toast.makeText(getBaseContext(), "stopped", Toast.LENGTH_SHORT).show();
                stopService(new Intent(getApplicationContext(),Myservice.class));
            }
        });
    }

    private boolean check(Double lev) {
        
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
package com.example.batterychecker;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.os.BatteryManager;
import android.os.Build;
import android.os.IBinder;
import android.provider.Settings;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

public class Myservice extends Service {
    MediaPlayer mp;
    int flag=0;
    boolean b;
    @Nullable
    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mp = MediaPlayer.create(this, Settings.System.DEFAULT_RINGTONE_URI);
        int v = intent.getIntExtra("value",0);
        int bp=intent.getIntExtra("battery",0);
        Intent blevel= registerReceiver(null,new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        int stat=blevel.getIntExtra(BatteryManager.EXTRA_STATUS,-1);
        b=((stat==BatteryManager.BATTERY_STATUS_CHARGING)||(stat==BatteryManager.BATTERY_STATUS_FULL));
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            if(b){
                while (true){
                    if(bp==v){
                       flag=1;
                       break;
                    }
                }
                if (flag==1){
                    startmusic();
                }
                else{
                    Toast.makeText(getBaseContext(), "Something happened restart", Toast.LENGTH_SHORT).show();
                }

            }
            else{
                Toast.makeText(getBaseContext(), "Plugin the charger and proceed", Toast.LENGTH_LONG).show();
            }

        }
        return START_STICKY;
    }

    private void startmusic() {
        mp.setLooping(true);
        mp.start();
    }

    @Override
    public void onDestroy()
    {
        mp.stop();
    }
}

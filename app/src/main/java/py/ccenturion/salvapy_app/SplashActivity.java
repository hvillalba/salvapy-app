package py.ccenturion.salvapy_app;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class SplashActivity extends AppCompatActivity {
    //Basic data
String TAG = "SplashActivity";
private Context context;
SharedPreferences customPreferenceManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        context= this;
        customPreferenceManager = getSharedPreferences("preferences", MODE_PRIVATE);
        //Se verifica si es la primera carga.
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2500);
                    if(customPreferenceManager.getBoolean("logueado",false)){
                        Intent i = new Intent(SplashActivity.this, HomeActivity.class);
                        startActivity(i);
                        SplashActivity.this.finish();
                    }else {
                        if(!customPreferenceManager.getBoolean("first",false)){
                            if (!customPreferenceManager.getBoolean("acepto",false)){
                                Intent i = new Intent(SplashActivity.this, AcercaDeActivity.class);
                                startActivity(i);
                                SplashActivity.this.finish();
                            }
                        }else {
                            Intent i = new Intent(SplashActivity.this, LoginActivity.class);
                            startActivity(i);
                            SplashActivity.this.finish();
                        }

                    }
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        }).start();
    }

}

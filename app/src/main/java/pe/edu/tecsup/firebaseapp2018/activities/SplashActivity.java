package pe.edu.tecsup.firebaseapp2018.activities;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import pe.edu.tecsup.firebaseapp2018.R;
import pe.edu.tecsup.firebaseapp2018.activities.LoginActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        /* DESPUES DE 3 SEGUNDOS SE LANZARA EL MAIN ACTIVITY - HILO */

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this,LoginActivity.class));
                finish();
            }
            /* TIEMPO EN EL QUE SE EJECUTARA EL MAIN ACTIVITY*/
        }, 3000);
    }
}

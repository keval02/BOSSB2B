package com.nivida.bossb2b;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import java.io.IOException;

public class SplashActivity extends AppCompatActivity {
    private static final int TIME_OUT = 1300;
    AppPref prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        try {
            Process process = new ProcessBuilder()
                    .command("logcat", "-c")
                    .redirectErrorStream(true)
                    .start();
        } catch (IOException e) {
            e.printStackTrace();
        }

        prefs = new AppPref(getApplicationContext());
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if (prefs.isLoggedIn()) {
                    if (prefs.isPasswordChanged == true) {
                        Intent iGo = new Intent(SplashActivity.this,
                                ChangePasswordForFirstTime.class);
                        iGo.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(iGo);
                        SplashActivity.this.finish();


                    } else {


                        if (prefs.getRole_id().equalsIgnoreCase(C.COMP_SALES_ROLE)) {

                            Intent iGo = new Intent(SplashActivity.this,
                                    HomeActivity.class);
                            iGo.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(iGo);
                            SplashActivity.this.finish();
                        } else if (prefs.getRole_id().equalsIgnoreCase(C.DIS_SALES_ROLE)) {
                            Intent iGo = new Intent(SplashActivity.this,
                                    HomeActivity.class);
                            iGo.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(iGo);
                            SplashActivity.this.finish();
                        }else if(prefs.getRole_id().equalsIgnoreCase(C.DISTRIBUTOR_ROLE)){
                            Intent iGo = new Intent(SplashActivity.this,
                                    DistributorLogin.class);
                            iGo.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(iGo);
                            SplashActivity.this.finish();
                        }
                    }
                } else {
                    Intent iGo = new Intent(SplashActivity.this,
                            LoginActivity.class);
                    iGo.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(iGo);
                    SplashActivity.this.finish();
                }
            }


        }, TIME_OUT);

    }
}

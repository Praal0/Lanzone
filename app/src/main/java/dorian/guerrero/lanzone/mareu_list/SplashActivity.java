package dorian.guerrero.lanzone.mareu_list;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;

import dorian.guerrero.lanzone.R;

public class SplashActivity extends AppCompatActivity {

    ProgressBar splashProgress;
    int SPLASH_TIME = 5000; //This is 5 seconds

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getSupportActionBar().hide();

        //Rn a progress bar
        splashProgress = findViewById(R.id.splashProgress);
        playProgress();

        //Code to start timer and take action after the timer ends
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //We are moving to next page
                Intent mySuperIntent = new Intent(SplashActivity.this, HomeActivity.class);
                startActivity(mySuperIntent);
                //This 'finish()' is for exiting the app when back button pressed from Home page which is HomeActivity
                finish();

            }
        }, SPLASH_TIME);
    }

    //Void to run progress bar for 5 seconds
    private void playProgress() {
        ObjectAnimator.ofInt(splashProgress, "progress", 100)
                .setDuration(SPLASH_TIME)
                .start();
    }
}
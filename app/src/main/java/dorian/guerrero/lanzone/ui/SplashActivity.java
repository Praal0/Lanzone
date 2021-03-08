package dorian.guerrero.lanzone.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import dorian.guerrero.lanzone.R;
import dorian.guerrero.lanzone.ui.meeting_list.HomeActivity;

public class SplashActivity extends AppCompatActivity {

    @BindView(R.id.splashProgress) ProgressBar splashProgress;
    int SPLASH_TIME = 5000; //This is 5 seconds

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        getSupportActionBar().hide();

        //Rn a progress bar
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
package dorian.guerrero.lanzone.mareu_list;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import dorian.guerrero.lanzone.R;

public class HomeActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        final FloatingActionButton button = findViewById(R.id.addMeeting);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                Intent addIntent = new Intent(HomeActivity.this, AddReuActivity.class);
                startActivity(addIntent);
            }
        });

    }




}



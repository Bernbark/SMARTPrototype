package com.example.smartprototype;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class HomeScreen extends AppCompatActivity {

    Button goToChoiceForm;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {
        }
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_home_screen);

        goToChoiceForm = findViewById(R.id.second_page_button);
        goToChoiceForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToChoiceForm();
            }
        });
    }

    public void goToChoiceForm(){
        Intent secondPage = new Intent(this, ChoiceForm.class);
        startActivity(secondPage);
        finish();
    }

    /**
     * Experimenting with exiting completely, this method works, but on older phones/API 15 and below/
     * it doesn't seem possible to completely exit
     */
    @Override
    public void onBackPressed(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            finishAndRemoveTask();
        }
        else{
            this.finishAffinity();
        }
    }

}
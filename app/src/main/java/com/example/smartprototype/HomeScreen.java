package com.example.smartprototype;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class HomeScreen extends AppCompatActivity {

    Button form_start;
    Button selectFromRadioButton;
    Button go_to_chart;
    FragmentTransaction ft;
    FragmentManager manager = getSupportFragmentManager();
    RadioButton hidden_radio;
    RadioButton career_radio;
    RadioButton college_radio;
    RadioButton residence_radio;
    RadioGroup preset_radio;
    CareerPresetFragment careerFrag;
    CollegePresetFragment collegeFrag;
    ResidencePresetFragment residenceFrag;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        careerFrag = new CareerPresetFragment();
        collegeFrag = new CollegePresetFragment();
        residenceFrag = new ResidencePresetFragment();
        preset_radio = findViewById(R.id.radioGroup);
        career_radio = findViewById(R.id.career_preset);
        college_radio = findViewById(R.id.college_preset);
        hidden_radio = findViewById(R.id.hidden_radio);

        residence_radio = findViewById(R.id.residence_preset);
        if (savedInstanceState == null) {
            manager = getSupportFragmentManager();
            ft = manager.beginTransaction();
            ft.setReorderingAllowed(true);
            ft.add(R.id.frag_container, careerFrag);
            ft.add(R.id.frag_container,collegeFrag);
            ft.add(R.id.frag_container,residenceFrag);
            //ft.setCustomAnimations(android.R.animator.fade_in,
                    //android.R.animator.fade_out);
            ft.hide(collegeFrag);
            ft.hide(careerFrag);
            ft.hide(residenceFrag);
            ft.commit();
            /**
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.your_placeholder, CareerPresetFragment.class, null)
                    .commit();
            */
        }
        /**
         * Hiding app title for more space
         */
        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {
        }
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        setContentView(R.layout.activity_home_screen);

        go_to_chart = findViewById(R.id.second_page_button);
        go_to_chart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToQuestionForm();
            }
        });
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.career_preset:
                if (checked){
                    Log.e("HOMESCREEN", "onRadioButtonClicked: is careerFrag null here? "+ careerFrag   );
                    ft = manager.beginTransaction();
                    ft.replace(R.id.frag_container,careerFrag);
                    ft.commit();
                }
                    break;
            case R.id.college_preset:
                if (checked) {
                    ft = manager.beginTransaction();
                    ft.replace(R.id.frag_container,collegeFrag);
                    ft.commit();
                }
                    break;
            case R.id.residence_preset:
                if (checked)
                {
                    ft = manager.beginTransaction();
                    ft.replace(R.id.frag_container,residenceFrag);
                    ft.commit();
                }
                    break;
        }
    }

    public void goToQuestionForm(){
        Intent secondPage = new Intent(this, QuestionForm.class);
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
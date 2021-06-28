package com.example.smartprototype;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

public class QuestionForm extends AppCompatActivity {


    Button finishForm;
    ArrayList<String> results;
    /**
     * A good lesson in handling large amounts of initialization, combine these fields
     * with the initialize method down below
     */
    int[] editTextIds = {R.id.descriptor0,R.id.descriptor1,R.id.descriptor2,
    R.id.descriptor3,R.id.descriptor4,R.id.descriptor5,R.id.descriptor6};
    ArrayList<EditText> editTexts = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {
        }
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_question_form);
        results = new ArrayList<>();
        initialize();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        CollapsingToolbarLayout toolBarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        toolBarLayout.setTitle(getTitle());

        finishForm = findViewById(R.id.go_to_chart);
        finishForm.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                getTexts();
                goToSecondQuestionForm();
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //getTexts();
                //goToChart();
                showToast("Button disabled for testing");
            }
        });
    }

    public void goToChart(){
        if (results.size() == 0){
            showToast("Nothing to send to the charts!");
            return;
        }
        Intent secondPage = new Intent(this, MainActivity.class);
        Bundle bundle = new Bundle();
        bundle.putStringArrayList("texts",results);

        secondPage.putExtras(bundle);
        startActivity(secondPage);
    }

    public void goToSecondQuestionForm(){
        Intent secondQuestion = new Intent(this, QuestionForm2.class);
        Bundle bundle = new Bundle();
        //secondQuestion.putExtra("texts",texts);
        bundle.putStringArrayList("results",results);
        secondQuestion.putExtras(bundle);
        startActivity(secondQuestion);
        finish();
    }

    public void initialize(){
        for(int i = 0; i < editTextIds.length; i++){
            EditText editText = findViewById(editTextIds[i]);
            editTexts.add(editText);
        }
    }

    public void getTexts(){

        for(int i = 0; i < editTexts.size(); i++){
            results.add(editTexts.get(i).getText().toString());
        }
    }

    private void showToast(String message) {

        Snackbar snack = Snackbar.make(findViewById(R.id.q_1_layout), message,
                Snackbar.LENGTH_SHORT);
        snack.setDuration(1000);
        snack.show();
    }

    public void goBackProperly(){
        Intent back = new Intent(this,HomeScreen.class);
        startActivity(back);
        finish();
    }

    /**
     * An easy way to go back to the previous page for the user, this also makes a new activity which
     * is beneficial for the program because the purpose of going back is to change data, and without this,
     * data was not being changed from the first time an activity was created.
     */
    @Override
    public void onBackPressed(){
        goBackProperly();
    }


}
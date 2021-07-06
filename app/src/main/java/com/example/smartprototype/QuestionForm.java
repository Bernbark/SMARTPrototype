package com.example.smartprototype;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class QuestionForm extends AppCompatActivity {

    public static final String TAG = "QuestionForm";
    Button finishForm;
    ArrayList<String> results;
    /**
     * A good lesson in handling large amounts of initialization, combine these fields
     * with the initialize method down below
     */
    EditText et;
    LinearLayout layout;
    LinearLayout.LayoutParams params;
    ArrayList<EditText> editTexts = new ArrayList<>();
    int choicesAmount, descriptorsAmount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {
        }
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_question_form);
        Intent i = getIntent();
        Bundle extras = i.getExtras();
        if(extras != null){
            choicesAmount =  Integer.parseInt(i.getStringExtra("choiceAmount"));
            descriptorsAmount = Integer.parseInt(i.getStringExtra("descriptorAmount"));
            Log.e(TAG, "onCreate: choices and descriptors amounts = "+choicesAmount+" "+descriptorsAmount );
        }
        layout = findViewById(R.id.q_1_layout);
        results = new ArrayList<>();
        makeEditTexts();
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

    public void makeEditTexts(){
        params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,140);
        for(int i = 0; i < descriptorsAmount; i++){
            et = new EditText(this);
            et.setHint("Descriptors go here");
            et.setPadding(5,40,5,5);
            editTexts.add(et);
            layout.addView(et,params);
        }
    }

    public void goToSecondQuestionForm(){
        Intent secondQuestion = new Intent(this, QuestionForm2.class);
        Bundle bundle = new Bundle();
        //secondQuestion.putExtra("texts",texts);
        bundle.putStringArrayList("results",results);
        secondQuestion.putExtra("choiceAmount",choicesAmount+"");
        secondQuestion.putExtras(bundle);
        startActivity(secondQuestion);
        finish();
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
        Intent back = new Intent(this,ChoiceForm.class);
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
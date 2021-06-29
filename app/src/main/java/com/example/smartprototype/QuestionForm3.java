package com.example.smartprototype;

import android.content.Intent;
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

public class QuestionForm3 extends AppCompatActivity {

    int[] editTextIds = {R.id.choice0,R.id.choice1,R.id.choice2,
            R.id.choice3,R.id.choice4,R.id.choice5,R.id.choice6};
    ArrayList<EditText> editTexts = new ArrayList<>();
    Button goToMain;
    ArrayList<String> results, weights;
    ArrayList<String> choices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_form3);
        Intent i = getIntent();
        Bundle extras = i.getExtras();
        if(extras != null){
            results = (ArrayList<String>) extras.getStringArrayList("results");
            weights = (ArrayList<String>) extras.getStringArrayList("weights");
        }
        choices = new ArrayList<>();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        CollapsingToolbarLayout toolBarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        toolBarLayout.setTitle(getTitle());
        goToMain = findViewById(R.id.go_to_main_activity);
        goToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getTexts();
                goToForm4();
            }
        });
        initialize();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getTexts();
                goToForm4();
            }
        });
    }

    public void initialize(){
        for(int i = 0; i < editTextIds.length; i++){
            EditText editText = findViewById(editTextIds[i]);
            editTexts.add(editText);
        }
    }

    private void goToMain(){
        Intent goToMain = new Intent(this,MainActivity.class);
        Bundle bundle = new Bundle();
        bundle.putStringArrayList("results",results);
        bundle.putStringArrayList("choices",choices);
        bundle.putStringArrayList("weights",weights);
        goToMain.putExtras(bundle);
        startActivity(goToMain);
        finish();
    }

    /**
     * Must call finish when going to a new activity, otherwise multiple of the same activity will exist
     */
    private void goToForm4(){
        Intent goTo4 = new Intent(this,QuestionForm4.class);
        Bundle bundle = new Bundle();
        bundle.putStringArrayList("results",results);
        bundle.putStringArrayList("choices",choices);
        bundle.putStringArrayList("weights",weights);
        goTo4.putExtras(bundle);
        startActivity(goTo4);
        finish();
    }

    public void getTexts(){

        for(int i = 0; i < editTexts.size(); i++){
            choices.add(editTexts.get(i).getText().toString());
        }
    }

    public void goBackProperly(){
        Intent back = new Intent(this,QuestionForm2.class);
        Bundle bundle = new Bundle();
        bundle.putStringArrayList("results",results);
        //bundle.putStringArrayList("choices",choices);
        //bundle.putStringArrayList("weights",weights);
        back.putExtras(bundle);
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
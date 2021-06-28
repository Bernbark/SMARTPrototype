package com.example.smartprototype;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.slider.Slider;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class QuestionForm4 extends AppCompatActivity {

    public static final int QUESTION_AMOUNT = 7;
    public static final int RADIO_AMOUNT = 10;
    int questionCounter;
    ArrayList<String> results, choices, weights, questions;
    ArrayList<Float> values;
    LinearLayout layout;
    LinearLayout.LayoutParams params;
    TextView tv;
    RadioGroup grp;
    Slider slider;
    Button goToChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_form4);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        CollapsingToolbarLayout toolBarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        toolBarLayout.setTitle(getTitle());
        layout = findViewById(R.id.q_4_layout);
        goToChart = findViewById(R.id.go_to_chart);
        questionCounter = 0;
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if(extras != null){
            //the descriptors
            results = extras.getStringArrayList("results");
            //the things that are hard to choose
            choices = extras.getStringArrayList("choices");
            //the weights given to those choices
            weights = extras.getStringArrayList("weights");
        }
        questions = new ArrayList<>();
        values = new ArrayList<>();
        makeQuestions();
        for(int i = 0; i < QUESTION_AMOUNT; i++){
            for(int j = 0; j < QUESTION_AMOUNT; j++){
                makeSliders();
            }
        }
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        goToChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // take all results from the radio buttons
                for(int i=0;i<layout.getChildCount();i++)  //From the parent layout (Linear Layout) get the child
                {
                    View child = layout.getChildAt(i);

                    if(child instanceof Slider)     //Check weather its RadioGroup using its INSTANCE
                    {
                        Slider sd = (Slider) child; //create a Slider for each group
                        float value = sd.getValue(); // get the selected button
                        values.add(value);
                        Log.e("questionForm4", "onClick: testing values "+ value );
                    }
                }
                goToChart();
            } });
    }

    private void goToChart(){
        Intent chart = new Intent(this,MainActivity.class);
        Bundle bundle = new Bundle();
        bundle.putStringArrayList("results",results);
        bundle.putStringArrayList("choices",choices);
        bundle.putStringArrayList("weights",weights);
        float[] array = new float[values.size()];

        for (int i = 0; i < values.size(); i++) {
            array[i] = values.get(i);
        }
        bundle.putFloatArray("values",array);
        chart.putExtras(bundle);
        startActivity(chart);
        finish();
    }

    private void makeQuestions(){
        if(results == null || choices == null){
            Intent intent = getIntent();
            Bundle extras = intent.getExtras();
            if(extras != null){
                //the descriptors
                results = extras.getStringArrayList("results");
                //the things that are hard to choose
                choices = extras.getStringArrayList("choices");
                //the weights given to those choices
                weights = extras.getStringArrayList("weights");
            }
        }
        for(int i = 0; i < results.size(); i++){
            for(int j = 0; j < choices.size(); j++){
                Log.e("QuestionForm4", "makeQuestions: size of arrays "+choices.size()+"   " +results.size() );
                questions.add("How do you rate " + choices.get(i) + "'s \"" + results.get(j) +"\" factor. 10 being better and 1 being worse");
            }
        }
    }

    private void makeSliders(){

        grp = new RadioGroup(this);

        for(int i = 2; i > 0; i--){

            if(i == 2){
                tv = new TextView(this);
                tv.setText(questions.get(questionCounter));
                questionCounter++;
                params = new RadioGroup.LayoutParams(RadioGroup.LayoutParams.MATCH_PARENT, RadioGroup.LayoutParams.WRAP_CONTENT);
                layout.addView(tv,params);
            }
            //TODO figure out a good way to handle asking many questions with an expected response of 1-10,
            // possibly a slider?
            else{
                slider = new Slider(new ContextThemeWrapper(this,R.style.SliderTheme));
                //params = new RadioGroup.LayoutParams(RadioGroup.LayoutParams.WRAP_CONTENT, RadioGroup.LayoutParams.WRAP_CONTENT);
                slider.setValueFrom(1);
                slider.setValueTo(10);
                slider.setStepSize(1);
                slider.setValue(10);
                layout.addView(slider);
            }
        }
        //layout.addView(grp);
    }

    public void goBackProperly(){
        Intent back = new Intent(this,QuestionForm3.class);
        Bundle bundle = new Bundle();
        bundle.putStringArrayList("results",results);
        bundle.putStringArrayList("choices",choices);
        bundle.putStringArrayList("weights",weights);
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
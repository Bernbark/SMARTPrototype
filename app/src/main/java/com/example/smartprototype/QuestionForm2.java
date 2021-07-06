package com.example.smartprototype;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.slider.Slider;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class QuestionForm2 extends AppCompatActivity {

    private static final String TAG = "QuestionForm2";
    private static final int RADIO_BUTTON_AMOUNT = 5;
    private static final int QUESTION_AMOUNT = 21;
    ArrayList<Integer> ids;
    ArrayList<String> results, weights;
    ArrayList<String> questions;
    //int[] textResultIds = {R.id.result1,R.id.result12,R.id.result13,R.id.result14,R.id.result15,
            //R.id.result16,R.id.result17};
    ArrayList<TextView> textViews = new ArrayList<>();
    Button goToForm3;
    int radioCounter;
    LinearLayout layout;
    Slider slider;

    RadioGroup grp;
    RadioButton[] radios;
    RadioGroup.LayoutParams params;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_form2);
        Intent i = getIntent();
        Bundle extras = i.getExtras();
        if(extras != null){
            results = (ArrayList<String>) i.getStringArrayListExtra("results");
        }
        weights = new ArrayList<>();
        ids = new ArrayList<>();
        goToForm3 = findViewById(R.id.go_to_main_activity);
        goToForm3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // take all results from the radio buttons
                for(int i=0;i<layout.getChildCount();i++)  //From the parent layout (Linear Layout) get the child
                {
                    View child = layout.getChildAt(i);

                    if(child instanceof Slider)     //Check weather its RadioGroup using its INSTANCE
                    {
                        Slider sd = (Slider) child; //create a Slider for each group
                        String tempString = Float.toString(sd.getValue()); // get the selected button
                        weights.add(tempString);
                    }
                }
                goToForm3();

            } });
        questions = new ArrayList<>();
        makeQuestions();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        layout = findViewById(R.id.q_2_linear_layout);
        CollapsingToolbarLayout toolBarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        toolBarLayout.setTitle(getTitle());
        radioCounter = 0;
        initializeResults();
        //makeRadio();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showToast("Button disabled for testing");
            }
        });
    }

    //TODO hook up questions by manipulating results arraylist and concactenating 22 strings
    // containing the questions to be asked
    public void makeQuestions(){
        for(int i = 0; i < 6; i++){
            for(int j = i + 1; j < results.size(); j++){
                questions.add("How do you rate " + results.get(i) + " in importance compared to "+ results.get(j));
            }
        }
        Log.e(TAG, "makeQuestions: questions size = "+questions.size() );
        /**
        for (int i = 0; i < 6; i++){
            questions.add("How do you rate " + results.get(0) + " in importance compared to "+ results.get(i+1));
        }
        for (int i = 1; i < 6; i++){
            questions.add("How do you rate " + results.get(1) + " in importance compared to "+ results.get(i+1));
        }
        for (int i = 2; i < 6; i++){
            questions.add("How do you rate " + results.get(2) + " in importance compared to "+ results.get(i+1));
        }
        for (int i = 3; i < 6; i++){
            questions.add("How do you rate " + results.get(3) + " in importance compared to "+ results.get(i+1));
        }
        for (int i = 4; i < 6; i++){
            questions.add("How do you rate " + results.get(4) + " in importance compared to "+ results.get(i+1));
        }
        questions.add("How do you rate " + results.get(5) + " in importance compared to "+ results.get(6));
         */
    }

    public void initializeResults(){
        if(results.size() == 0){
            showToast("This array has nothing in it!");
        }
        else{
            for (int i = 0; i < questions.size(); i++){
                //TextView text = findViewById(textResultIds[i]);
                //Log.e(TAG, "initializeResults: checking results before setting text in QF2 = " +results[i] );
                //text.setText(results[i]);
                //textViews.add(text);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    radioCounter++;
                    makeSlider();
                }

            }
        }
    }

    private void showToast(String message) {

        Snackbar snack = Snackbar.make(findViewById(R.id.q_2_linear_layout), message,
                Snackbar.LENGTH_SHORT);
        snack.setDuration(1000);
        snack.show();
    }

    // TODO wrap all radio button results into the bundle
    private void goToForm3(){
        Intent form3 = new Intent(this,QuestionForm3.class);
        Bundle bundle = new Bundle();
        bundle.putStringArrayList("results",results);
        bundle.putStringArrayList("weights",weights);
        form3.putExtras(bundle);
        startActivity(form3);
        finish();
    }

    private void goToMain(){
        Intent goToMain = new Intent(this,MainActivity.class);
        Bundle bundle = new Bundle();
        goToMain.putExtra("results",results);
        goToMain.putExtras(bundle);
        startActivity(goToMain);
    }

    //TODO change radios to sliders, requires changing the onClick to take in a float array
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void makeSlider(){
        grp = new RadioGroup(this);
        grp.setId(radioCounter);
        radios = new RadioButton[5];
        for(int i = 2; i > 0; i--){

            // id's should be 1005, 2005, 3005, 4005, 5005 etc.
            if(i == 2){
                TextView text = new TextView(this);
                text.setId(100+radioCounter);
                text.setText(questions.get(radioCounter-1));
                params = new RadioGroup.LayoutParams(RadioGroup.LayoutParams.MATCH_PARENT, RadioGroup.LayoutParams.WRAP_CONTENT);
                layout.addView(text,params);
            }
            // id's should be 2004
            else{
                slider = new Slider(new ContextThemeWrapper(this,R.style.SliderTheme));
                //params = new RadioGroup.LayoutParams(RadioGroup.LayoutParams.WRAP_CONTENT, RadioGroup.LayoutParams.WRAP_CONTENT);
                slider.setValueFrom(1);
                slider.setValueTo(5);
                slider.setStepSize(1);
                slider.setValue(5);
                layout.addView(slider);
            }
        }
    }

    public void goToForm1(){
        Intent start = new Intent(this,QuestionForm.class);
        startActivity(start);
        finish();
    }

    /**
     * An easy way to go back to the previous page for the user, this also makes a new activity which
     * is beneficial for the program because the purpose of going back is to change data, and without this,
     * data was not being changed from the first time an activity was created.
     */
    @Override
    public void onBackPressed(){
        goToForm1();
    }
}
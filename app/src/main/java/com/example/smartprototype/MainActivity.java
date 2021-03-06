package com.example.smartprototype;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    String value;
    TableLayout table;
    Button addBtn;

    int rowCount = 7;
    int rowCounter = 0;
    int columnCount, weightCounter, endValueCounter;
    int columnCounter = 0;

    Drawable image;

    double[] weightedValues;

    //int[] textResultIds = {R.id.column10,R.id.column20,R.id.column30,R.id.column40,R.id.column50,
            //R.id.column60,R.id.column70};
    ArrayList<TextView> textViews = new ArrayList<>();
    ArrayList<String> results, choices, weights;
    ArrayList<Float> values;
    ArrayList<Integer> endValues, choiceSumTotal;


    public static final String TAG = "MainActivity";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /**
         * Hiding app title for more space
         */
        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}
        setContentView(R.layout.activity_main);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            image = getDrawable(R.drawable.cell_rectangle_even_lighter_purple);
        }
        Intent i = getIntent();
        Bundle extras = i.getExtras();
        if(extras != null){
            results = extras.getStringArrayList("results");
            choices = extras.getStringArrayList("choices");
            weights = extras.getStringArrayList("weights");
            float[] tempArray = extras.getFloatArray("values");
            values = new ArrayList<>();
            for (int j = 0; j < tempArray.length; j++) {
                values.add(tempArray[j]);
            }
        }
        endValues = new ArrayList<>();
        choiceSumTotal = new ArrayList<>();
        weightedValues = new double[results.size()];
        table = (TableLayout)findViewById(R.id.table0);
        initializeResults();

        endValueCounter = 0;
        columnCounter = 0;
        makeValues();
        makeRow();
        //addBtn = (Button) findViewById(R.id.add_button0);
        //addBtn.setOnClickListener(new View.OnClickListener() {
            //@Override
            //public void onClick(View v) {

            //}
        //});
    }

    //TODO make the update method dynamic so that it stops adding unnecessary columns, and adds
    // the correct amount of rows

    private void update() {

        columnCounter = 0;

        if(rowCounter < choices.size()){
            TextView tv;
            Typeface typeface = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                typeface = ResourcesCompat.getFont(this,R.font.robotoboldraw);
            }


            TableRow tr = new TableRow(this);
            tr.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.MATCH_PARENT));
            for(int i = 0; i < results.size()+2; i++){
                //TODO add 7 colors for alternating row headers
                if(columnCounter == 0){
                    tv = new TextView(new ContextThemeWrapper(this,R.style.cell_style_dark_purple));
                    tv.setEllipsize(TextUtils.TruncateAt.END);
                    tv.setMaxEms(4);
                    tv.setMaxLines(1);
                    tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.MATCH_PARENT));
                    tv.setText(value);
                    endValueCounter--;
                }
                else if(columnCounter == results.size()+1){
                    tv = new TextView(new ContextThemeWrapper(this,R.style.cell_style_final_sum));
                    tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.MATCH_PARENT));
                    tv.setText(String.valueOf(choiceSumTotal.get(rowCounter)));
                    endValueCounter--;
                }
                else{
                    // Must take i-1 to get chart in order
                    String tempText = Float.toString(endValues.get(endValueCounter));
                    tv = new TextView(new ContextThemeWrapper(this,R.style.cell_style_light_purple));
                    tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.MATCH_PARENT));
                    tv.setText(tempText);
                }
                tv.setTypeface(typeface);
                tv.setTextSize(18);
                tr.addView(tv);
                endValueCounter++;
                columnCounter++;
            }
            rowCounter++;
            table.addView(tr);
        }
        else{
            showToast("You can't add anymore rows for this exercise");
        }
    }

    private void makeRow(){
        for(int i = 0; i < choices.size(); i++){
            value = choices.get(i);
            update();
        }
        value = "";
    }

    private void showToast(String message) {

        Snackbar snack = Snackbar.make(findViewById(R.id.coordinator_layout), message,
                Snackbar.LENGTH_SHORT);
        snack.setDuration(1000);
        snack.show();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Toast.makeText(this, "landscape", Toast.LENGTH_SHORT).show();
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            Toast.makeText(this, "portrait", Toast.LENGTH_SHORT).show();
        }
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }

    //TODO hook up results as an ArrayList
    public void initializeResults(){
        if(results.size() == 0){
            showToast("This array has nothing in it!");
        }
        else{
            TableRow tr = new TableRow(this);
            tr.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.MATCH_PARENT));
            TextView tv;
            for (int i = 0; i < results.size()+2; i++){
                if(i == 0){
                    tv = new TextView(new ContextThemeWrapper(this,R.style.cell_style_black));
                }
                else if(i == results.size()+1){
                    tv = new TextView(new ContextThemeWrapper(this,R.style.cell_style_green));
                    tv.setText("Totals");
                }
                else{
                    tv = new TextView(new ContextThemeWrapper(this,R.style.cell_style_dark_purple));
                    tv.setText(results.get(i-1));
                }
                tv.setEllipsize(TextUtils.TruncateAt.END);
                tv.setMaxEms(4);
                tv.setMaxLines(1);
                tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.MATCH_PARENT));



                textViews.add(tv);
                tr.addView(tv);
            }
            table.addView(tr);
        }
    }

    //TODO hook up the second half of this, weights is the second half which needs to be hooked up with
    // weightedValues, then divide each weightedValue by results.size() - 1 to get the real weight
    private void makeValues(){
        weightCounter = 0;
        // For each value in results
        for(int j = 0; j < results.size(); j++){
            // For each value in results, starting with the second value in weightedValues, we are
            // starting by filling in the second half of the array first.
            for(int k = j + 1 ; k < results.size(); k++){
                // Get the weightedValue by parsing weights
                double weightedValue = Double.parseDouble(weights.get(weightCounter));
                // Depending on the result, it must be flipped
                if(weightedValue == 5){
                    weightedValue = 1;
                }
                else if(weightedValue == 4){
                    weightedValue = 2;
                }
                else if(weightedValue == 2){
                    weightedValue = 4;
                }
                else if(weightedValue == 1){
                    weightedValue = 5;
                }
                else{
                    weightedValue = 3;
                }
                weightedValues[k] += weightedValue;
                weightCounter++;
            }
        }
        weightCounter = 0;
        // This fills in the first half of the array by using the original values from weights
        // and notice the weightedValues[j] instead of weightedValues[j]. This directs the flow of
        // information exactly how I need it2
        for(int j = 0; j < results.size(); j++){
            for(int k = j + 1 ; k < results.size(); k++){
                double weightedValue = Double.parseDouble(weights.get(weightCounter));
                weightedValues[j] += weightedValue;
                weightCounter++;
            }
        }
        // Here we take those final results and make them equal to themselves divided by the amount of
        // attributes that the current attribute is being compared against
        for(int i = 0; i < weightedValues.length; i++){
            weightedValues[i] = weightedValues[i]/(results.size()-1);
            Log.e(TAG, "makeValues: weighted values " + weightedValues[i] );
            Log.e(TAG, "makeValues: values length is "+ values.size() );
        }
        int valueCounter = 0;
        int tempInt;
        int sum = 0;
        for(int i = 0; i < choices.size(); i++){
            for(int j = 0; j < results.size(); j++){
                float tempFloat = values.get(valueCounter);
                tempFloat *= weightedValues[j];
                tempInt = (int) tempFloat;
                Log.e(TAG, "makeValues: tempFloat = "+tempFloat );
                endValues.add(tempInt);
                sum += tempInt;
                valueCounter++;
            }
            choiceSumTotal.add(sum);
            sum = 0;
        }
    }

    /**
     * Build an intent to go back to the previous screen when the user hits the android given
     * back button, it also needs to remember the arraylists that existed, otherwise an error
     * occurs where the program attempts to find the size of an arraylist that doesn't exist.
     */
    public void goBackProperly(){
        Intent back = new Intent(this,QuestionForm4.class);
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
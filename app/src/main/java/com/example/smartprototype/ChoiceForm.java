package com.example.smartprototype;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class ChoiceForm extends AppCompatActivity {

    ImageButton goToFormOne;
    RadioGroup radioChoiceAmountSelect, radioDescriptorAmountSelect;
    RadioButton radioButton;
    String choiceAmount, descriptorAmount;
    boolean proceed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice_form);
        radioDescriptorAmountSelect = findViewById(R.id.radioGroup);
        radioChoiceAmountSelect = findViewById(R.id.radioGroup2);
        proceed = false;
        goToFormOne = findViewById(R.id.go_to_form_one);
        goToFormOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkRadios();
                if(proceed){
                    goToFormOne();
                }
            }
        });
    }

    public void checkRadios(){
        int selectedId;
        if (radioChoiceAmountSelect.getCheckedRadioButtonId() == -1)
        {
            // no radio buttons are checked
            showToast("Need to select amount of choices");
        }
        else
        {
            // one of the radio buttons is checked
            selectedId = radioChoiceAmountSelect.getCheckedRadioButtonId();
            radioButton = findViewById(selectedId);
            choiceAmount = String.valueOf(radioButton.getText());

            if (radioDescriptorAmountSelect.getCheckedRadioButtonId() == -1)
            {
                showToast("Need to select amount of descriptors");
            }
            else
            {
                // one of the radio buttons is checked
                selectedId = radioDescriptorAmountSelect.getCheckedRadioButtonId();
                radioButton = findViewById(selectedId);
                descriptorAmount = String.valueOf(radioButton.getText());
                Log.e("ChoiceForm", "checkRadios: descriptorAmount = "+descriptorAmount );
                proceed = true;
            }
        }
    }

    private void showToast(String message) {

        Snackbar snack = Snackbar.make(findViewById(R.id.outer_layout), message,
                Snackbar.LENGTH_SHORT);
        snack.setDuration(1000);
        snack.show();
    }

    public void goToFormOne(){
        Intent secondPage = new Intent(this, QuestionForm.class);
        secondPage.putExtra("choiceAmount",choiceAmount+"");
        secondPage.putExtra("descriptorAmount",descriptorAmount+"");
        //secondPage.putExtras(bundle);
        startActivity(secondPage);
        finish();
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
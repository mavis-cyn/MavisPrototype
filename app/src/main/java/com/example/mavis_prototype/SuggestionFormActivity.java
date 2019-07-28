package com.example.mavis_prototype;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;

public class SuggestionFormActivity extends Activity {
    EditText editText1;
    Button submitButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.suggestion_form_activity);
        editText1 = (EditText) findViewById(R.id.editText1);
        submitButton = (Button) findViewById(R.id.submitButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message=editText1.getText().toString();
                Intent intent=new Intent();
                intent.putExtra("MESSAGE",message);
                setResult(2,intent);
                finish();//finishing activity
            }
        });
    }
}

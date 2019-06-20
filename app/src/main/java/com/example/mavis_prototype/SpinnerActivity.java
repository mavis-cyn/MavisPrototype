package com.example.mavis_prototype;

import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

public class SpinnerActivity extends Activity implements AdapterView.OnItemSelectedListener {
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        // An item was selected. You can retrieve the selected item using
        String selectedCategory = parent.getItemAtPosition(pos).toString();

        //Spinner spinner = (Spinner) findViewById(R.id.category_spinner);
        //spinner.setOnItemSelectedListener(this);
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }


}
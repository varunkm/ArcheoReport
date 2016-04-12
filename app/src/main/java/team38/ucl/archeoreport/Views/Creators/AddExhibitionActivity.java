package team38.ucl.archeoreport.Views.Creators;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import team38.ucl.archeoreport.Models.Exhibition;
import team38.ucl.archeoreport.R;

public class AddExhibitionActivity extends AppCompatActivity {
    private EditText addName;
    private EditText addLocation;
    private DatePicker addStart;
    private DatePicker addEnd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_exhibition_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Add Exhibition...");
        addName = (EditText) findViewById(R.id.exhibitionTxt);
        addLocation = (EditText) findViewById(R.id.locationTxt);
        addStart = (DatePicker) findViewById(R.id.startinput);
        addEnd   = (DatePicker) findViewById(R.id.endinput);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = addName.getText().toString();
                String loc = addLocation.getText().toString();
                Date start = getDateFromDatePicker(addStart);
                Date end =   getDateFromDatePicker(addEnd);

                Exhibition ex = new Exhibition(name,loc,start,end);
                ex.save();
                Log.i("ADD EXHIBITIONS","EXHIBITION ADDED");
                finish(); //go back to viewexhibitions
            }
        });
    }

    private static Date getDateFromDatePicker(DatePicker dp)
    {
        int day = dp.getDayOfMonth();
        int mo  = dp.getMonth();
        int yr  = dp.getYear();
        Calendar cal = Calendar.getInstance();
        cal.set(yr, mo, day);
        return cal.getTime();
    }

}

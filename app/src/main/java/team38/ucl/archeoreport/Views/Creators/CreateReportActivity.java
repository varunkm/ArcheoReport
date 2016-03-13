package team38.ucl.archeoreport.Views.Creators;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.Calendar;
import java.util.Date;

import team38.ucl.archeoreport.Models.Exhibition;
import team38.ucl.archeoreport.Models.Report;
import team38.ucl.archeoreport.R;

public class CreateReportActivity extends AppCompatActivity {
    private Exhibition ExhibitionContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_report);
        Intent intent = getIntent();
        Long id = intent.getLongExtra("ExhibitionID", 0);
        ExhibitionContext = Exhibition.findById(Exhibition.class,id);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Report rep = genNewReport();
                rep.save();
                finish();
            }
        });
    }

    private Report genNewReport()
    {
        String nrInv = ((EditText)(findViewById(R.id.nrInv))).getText().toString();
        String det1_et = ((EditText)(findViewById(R.id.det1))).getText().toString();
        String det2_et = ((EditText)(findViewById(R.id.det2))).getText().toString();
        String det3_et = ((EditText)(findViewById(R.id.det3))).getText().toString();
        String det4_et = ((EditText)(findViewById(R.id.det4))).getText().toString();
        String det5_et = ((EditText)(findViewById(R.id.det5))).getText().toString();
        String det6_et = ((EditText)(findViewById(R.id.det6))).getText().toString();
        String det7_et = ((EditText)(findViewById(R.id.det7))).getText().toString();
        String det8_et = ((EditText)(findViewById(R.id.det8))).getText().toString();
        String det9_et = ((EditText)(findViewById(R.id.det9))).getText().toString();
        String crateNum = ((EditText)(findViewById(R.id.crateNumber))).getText().toString();
        String specialCare = ((EditText)(findViewById(R.id.specialCare))).getText().toString();

        RadioGroup radioButtonGroup = (RadioGroup)findViewById(R.id.conditionGroup);
        int radioButtonID = radioButtonGroup.getCheckedRadioButtonId();
        View radioButton = radioButtonGroup.findViewById(radioButtonID);
        int idx = radioButtonGroup.indexOfChild(radioButton);
        RadioButton r = (RadioButton)  radioButtonGroup.getChildAt(idx);
        String condition = r.getText().toString();

        boolean supportF = ((CheckBox)(findViewById(R.id.supporFondazioneCheckBox))).isChecked();
        boolean plastic = ((CheckBox)(findViewById(R.id.plasticCheck))).isChecked();
        boolean paper = ((CheckBox)(findViewById(R.id.paperCheck))).isChecked();
        boolean noTape = ((CheckBox)(findViewById(R.id.tapeCheck))).isChecked();
        boolean ethafoam = ((CheckBox)(findViewById(R.id.foamCheck))).isChecked();
        boolean foamRubber = ((CheckBox)(findViewById(R.id.foamRubberCheck))).isChecked();
        boolean vert = ((CheckBox)(findViewById(R.id.verticalCheck))).isChecked();
        boolean flat = ((CheckBox)(findViewById(R.id.flatCheck))).isChecked();
        boolean side = ((CheckBox)(findViewById(R.id.sideCheck))).isChecked();
        String position = "";
        if (vert)
            position = "Vertical";

        if(flat)
            position = "Flat";

        if(side)
            position = "On One Side";
        //TODO Get dates from report creation screen
        Report rep = new Report(ExhibitionContext,nrInv, Calendar.getInstance().getTime(),det1_et,det2_et,det3_et,det4_et,det5_et,det6_et,det7_et,det8_et,det9_et,
                condition,specialCare,crateNum,supportF,plastic,paper,noTape,ethafoam,foamRubber,position);

        return rep;
    }
}

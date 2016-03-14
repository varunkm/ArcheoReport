package team38.ucl.archeoreport.Views.Creators;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.pdf.PdfDocument;
import android.graphics.pdf.PdfRenderer;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import team38.ucl.archeoreport.Models.Defect;
import team38.ucl.archeoreport.Models.DefectSet;
import team38.ucl.archeoreport.Models.Detail;
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
        ExhibitionContext = Exhibition.findById(Exhibition.class, id);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        LinearLayout defContainer = (LinearLayout) findViewById(R.id.defsContainer);
        int count = defContainer.getChildCount();
        String[] names = getResources().getStringArray(R.array.defect_choices);
        for(int i = 0; i < count; i++)
        {
            CheckBox v = (CheckBox)defContainer.getChildAt(i);
            v.setText(names[i]);
        }


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Report rep = genNewReport();
                finish();
            }
        });
    }

    private Report genNewReport() {
        boolean def1 = false;
        boolean def2 = false;
        boolean def3 = false;
        boolean def4 = false;
        boolean def5 = false;
        boolean def6 = false;
        boolean def7 = false;
        boolean def8 = false;
        boolean def9 = false;
        boolean def10 = false;
        boolean def11 = false;
        boolean def12 = false;

        String nrInv = ((EditText) (findViewById(R.id.nrInv))).getText().toString();
        String det1_et = ((EditText) (findViewById(R.id.det1))).getText().toString();
        String det2_et = ((EditText) (findViewById(R.id.det2))).getText().toString();
        String det3_et = ((EditText) (findViewById(R.id.det3))).getText().toString();
        String det4_et = ((EditText) (findViewById(R.id.det4))).getText().toString();
        String det5_et = ((EditText) (findViewById(R.id.det5))).getText().toString();
        String det6_et = ((EditText) (findViewById(R.id.det6))).getText().toString();
        String det7_et = ((EditText) (findViewById(R.id.det7))).getText().toString();
        String det8_et = ((EditText) (findViewById(R.id.det8))).getText().toString();
        String det9_et = ((EditText) (findViewById(R.id.det9))).getText().toString();
        String crateNum = ((EditText) (findViewById(R.id.crateNumber))).getText().toString();
        String specialCare = ((EditText) (findViewById(R.id.specialCare))).getText().toString();

        RadioGroup radioButtonGroup = (RadioGroup) findViewById(R.id.conditionGroup);
        int radioButtonID = radioButtonGroup.getCheckedRadioButtonId();
        View radioButton = radioButtonGroup.findViewById(radioButtonID);
        int idx = radioButtonGroup.indexOfChild(radioButton);
        RadioButton r = (RadioButton) radioButtonGroup.getChildAt(idx);
        String condition = r.getText().toString();

        boolean supportF = ((CheckBox) (findViewById(R.id.supporFondazioneCheckBox))).isChecked();
        boolean plastic = ((CheckBox) (findViewById(R.id.plasticCheck))).isChecked();
        boolean paper = ((CheckBox) (findViewById(R.id.paperCheck))).isChecked();
        boolean noTape = ((CheckBox) (findViewById(R.id.tapeCheck))).isChecked();
        boolean ethafoam = ((CheckBox) (findViewById(R.id.foamCheck))).isChecked();
        boolean foamRubber = ((CheckBox) (findViewById(R.id.foamRubberCheck))).isChecked();
        boolean vert = ((CheckBox) (findViewById(R.id.verticalCheck))).isChecked();
        boolean flat = ((CheckBox) (findViewById(R.id.flatCheck))).isChecked();
        boolean side = ((CheckBox) (findViewById(R.id.sideCheck))).isChecked();


        String position = "";
        if (vert)
            position = "Vertical";

        if (flat)
            position = "Flat";

        if (side)
            position = "On One Side";
        //TODO Get dates from report creation screen


        LinearLayout defContainer = (LinearLayout) findViewById(R.id.defsContainer);
        int count = defContainer.getChildCount();
        CheckBox v = null;
        v = (CheckBox) defContainer.getChildAt(0);
        def1 = v.isChecked();
        v = (CheckBox) defContainer.getChildAt(1);
        def2 = v.isChecked();
        v = (CheckBox) defContainer.getChildAt(2);
        def3 = v.isChecked();
        v = (CheckBox) defContainer.getChildAt(3);
        def4 = v.isChecked();
        v = (CheckBox) defContainer.getChildAt(4);
        def5 = v.isChecked();
        v = (CheckBox) defContainer.getChildAt(5);
        def6 = v.isChecked();
        v = (CheckBox) defContainer.getChildAt(6);
        def7 = v.isChecked();
        v = (CheckBox) defContainer.getChildAt(7);
        def8 = v.isChecked();
        v = (CheckBox) defContainer.getChildAt(8);
        def9 = v.isChecked();
        v = (CheckBox) defContainer.getChildAt(9);
        def10 = v.isChecked();
        v = (CheckBox) defContainer.getChildAt(10);
        def11 = v.isChecked();
        v = (CheckBox) defContainer.getChildAt(11);
        def12 = v.isChecked();



        Report rep = new Report(ExhibitionContext, nrInv, Calendar.getInstance().getTime(), det1_et, det2_et, det3_et, det4_et, det5_et, det6_et, det7_et, det8_et, det9_et,
                condition, specialCare, crateNum, supportF, plastic, paper, noTape, ethafoam, foamRubber, position);
        rep.save();

        DefectSet repdefs = new DefectSet(def1,def2,def3,def4,def5,def6,def7,def8,def9,def10,def11,def12,this,rep);
        repdefs.save();

        setContentView(R.layout.report_pdf);
        LinearLayout detscontain = (LinearLayout)findViewById(R.id.detscontainer);
        detscontain.setOrientation(LinearLayout.VERTICAL);
        ArrayList<Detail> dets = (ArrayList) rep.getDetailsAsList();
        for(Detail d : dets)
        {
            TextView title = new TextView(this);
            title.setText(d.getTitle());
            title.setTextColor(Color.LTGRAY);
            TextView det = new TextView(this);
            det.setTextColor(Color.BLACK);
            det.setText(d.getDetail());
            detscontain.addView(title);
            detscontain.addView(det);
        }

        LinearLayout defscontain = (LinearLayout)findViewById(R.id.defectscontainer);
        ArrayList<String> defs = repdefs.getDefectsAsListofStrings();
        defscontain.setOrientation(LinearLayout.VERTICAL);
        for(String d : defs)
        {
            TextView title = new TextView(this);
            title.setText(d);
            title.setTextColor(Color.BLACK);
            defscontain.addView(title);
        }

        TextView wrap = (TextView) findViewById(R.id.wrappingcontent);
        wrap.setText(rep.wrappingToString());
        TextView prot = (TextView) findViewById(R.id.protectioncontent);
        prot.setText(rep.protectionToString());
        TextView pos = (TextView) findViewById(R.id.positioncontent);
        pos.setText(rep.getPosition());
        View reportview = findViewById(R.id.pdfviewcontainer);
        reportview.setDrawingCacheEnabled(true);

        reportview.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        reportview.layout(0, 0, reportview.getMeasuredWidth(), reportview.getMeasuredHeight());

        reportview.buildDrawingCache(true);
        Bitmap b = Bitmap.createBitmap(reportview.getDrawingCache());
        PdfDocument document = new PdfDocument();

        // crate a page description
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(595,842,1).create();

        // start a page
        PdfDocument.Page page = document.startPage(pageInfo);

        // draw something on the page
        reportview.draw(page.getCanvas());

        // finish the page
        document.finishPage(page);
        verifyStoragePermissions(this);
        try {
            File root = Environment.getExternalStorageDirectory();
            root = new File(root,"ArcheoReport/");
            root.mkdir();
            String name = rep.getInvNum().replace(".","_");
            name = name.replace(" ","-");
            File docfile = new File(root,name+".pdf");
            FileOutputStream pdfos = new FileOutputStream(docfile);
            document.writeTo(pdfos);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // close the document
        document.close();
        return rep;
    }



    // Storage Permissions
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    /**
     * Checks if the app has permission to write to device storage
     *
     * If the app does not has permission then the user will be prompted to grant permissions
     *
     * @param activity
     */
    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }
}

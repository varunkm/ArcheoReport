package team38.ucl.archeoreport.Views.Creators;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.pdf.PdfDocument;
import android.graphics.pdf.PdfRenderer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
import team38.ucl.archeoreport.Views.Viewers.AnnotateActivity;

public class CreateReportActivity extends AppCompatActivity {
    private Exhibition ExhibitionContext;
    static final int REQUEST_TAKE_PHOTO = 1;
    private static final int PICKFILE_RESULT_CODE = 2;
    private Uri currentImgUri;
    private int imageCount = 0;

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
        for(int i = 1; i < count; i++)
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
        Button addFromDevice = (Button)findViewById(R.id.addDeviceBttn);
        Button addFromCamera = (Button)findViewById(R.id.launchCameraBttn);

        addFromCamera.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 String nrInv = ((EditText) (findViewById(R.id.nrInv))).getText().toString();
                 if (!nrInv.matches("")) {
                     dispatchTakePictureIntent();
                 } else {
                     Toast.makeText(getApplicationContext(), "Please fill out report completely", Toast.LENGTH_SHORT).show();
                 }
             }
         }
        );
        addFromDevice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nrInv = ((EditText) (findViewById(R.id.nrInv))).getText().toString();
                if (!nrInv.matches("")) {
                    dispatchGetFromFileIntent();
                } else {
                    Toast.makeText(getApplicationContext(), "Please fill out report completely", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private Report genNewReport() {
        boolean def1;
        boolean def2;
        boolean def3;
        boolean def4;
        boolean def5;
        boolean def6;
        boolean def7;
        boolean def8;
        boolean def9;
        boolean def10;
        boolean def11;
        boolean def12;

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
        int start = 1;
        CheckBox v = null;
        v = (CheckBox) defContainer.getChildAt(start);
        def1 = v.isChecked();
        v = (CheckBox) defContainer.getChildAt(start+1);
        def2 = v.isChecked();
        v = (CheckBox) defContainer.getChildAt(start+2);
        def3 = v.isChecked();
        v = (CheckBox) defContainer.getChildAt(start+3);
        def4 = v.isChecked();
        v = (CheckBox) defContainer.getChildAt(start+4);
        def5 = v.isChecked();
        v = (CheckBox) defContainer.getChildAt(start+5);
        def6 = v.isChecked();
        v = (CheckBox) defContainer.getChildAt(start+6);
        def7 = v.isChecked();
        v = (CheckBox) defContainer.getChildAt(start+7);
        def8 = v.isChecked();
        v = (CheckBox) defContainer.getChildAt(start+8);
        def9 = v.isChecked();
        v = (CheckBox) defContainer.getChildAt(start+9);
        def10 = v.isChecked();
        v = (CheckBox) defContainer.getChildAt(start+10);
        def11 = v.isChecked();
        v = (CheckBox) defContainer.getChildAt(start+11);
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
            rep.setPdfpath(docfile.getAbsolutePath());
            rep.save();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // close the document
        document.close();
        return rep;
    }


    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
                currentImgUri = Uri.fromFile(photoFile);
            } catch (IOException ex) {
                // Error occurred while creating the File
                ex.printStackTrace();
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(photoFile));
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }
    private void dispatchGetFromFileIntent(){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICKFILE_RESULT_CODE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Uri unannotated = currentImgUri;
        if (requestCode == PICKFILE_RESULT_CODE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            unannotated  = data.getData();

            }

        else if((requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK && data != null && data.getData() != null)){
            unannotated = currentImgUri;
        }
        /*
        initialUri = callingIntent.getParcelableExtra("imageURI");
        nrInv= callingIntent.getStringExtra("nrInv");
         */
        final Uri imageUri = unannotated;
        if(imageUri != null) {

            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which){
                        case DialogInterface.BUTTON_POSITIVE:
                            String nrInv = ((EditText) (findViewById(R.id.nrInv))).getText().toString();

                            Intent myIntent = new Intent(CreateReportActivity.this, AnnotateActivity.class);
                            myIntent.putExtra("nrInv", nrInv); //Optional parameters
                            myIntent.putExtra("imageURI", imageUri);
                            myIntent.putExtra("Exhibition",ExhibitionContext.getId().toString());
                            CreateReportActivity.this.startActivity(myIntent);
                            break;

                        case DialogInterface.BUTTON_NEGATIVE:
                            //No button clicked
                            break;
                    }
                }
            };

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Annotate Photo?").setPositiveButton("Yes", dialogClickListener)
                    .setNegativeButton("No", dialogClickListener).show();

        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String nrInv = ((EditText) (findViewById(R.id.nrInv))).getText().toString();
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = nrInv + timeStamp + "_unannotated";
        String root = Environment.getExternalStorageDirectory().toString();
        File storageDir = new File(root+"/ArcheoReport/Images/"+nrInv);
        storageDir.mkdirs();
        File image = File.createTempFile
        (
                imageFileName,  /* prefix */
                ".png",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        return image;
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

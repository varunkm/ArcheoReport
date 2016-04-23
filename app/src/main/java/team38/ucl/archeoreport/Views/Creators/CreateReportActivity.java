package team38.ucl.archeoreport.Views.Creators;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.ColorRes;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import team38.ucl.archeoreport.Models.AnnotatedImage;
import team38.ucl.archeoreport.Models.AutoFillRow;
import team38.ucl.archeoreport.Models.DBListHelper;
import team38.ucl.archeoreport.Models.Detail;
import team38.ucl.archeoreport.Models.Exhibition;
import team38.ucl.archeoreport.Models.PDFTextFactory;
import team38.ucl.archeoreport.Models.Report;
import team38.ucl.archeoreport.R;
import team38.ucl.archeoreport.Views.Viewers.AnnotateActivity;

public class CreateReportActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnTouchListener{
    private Exhibition ExhibitionContext;
    static final int REQUEST_TAKE_PHOTO = 1;
    private static final int PICKFILE_RESULT_CODE = 2;
    private Uri currentImgUri;
    private int imageCount = 0;
    private ArrayList<String> defectsChosen;
    private Spinner defectChooser;
    private boolean touch;
    String nr_inv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_report);
        Intent intent = getIntent();
        Long id = intent.getLongExtra("ExhibitionID", 0);
        Long modifyPast = intent.getLongExtra("ModifyOld",0);
        defectsChosen = new ArrayList<>();
        if(modifyPast == 1) {
            Long repId = intent.getLongExtra("OldReport", -1);
            Report oldrep = Report.findById(Report.class, repId);
            EditText nrinv = (EditText)findViewById(R.id.nrInv);
            EditText det1  = (EditText)findViewById(R.id.det1);
            EditText det2  = (EditText)findViewById(R.id.det2);
            EditText det3  = (EditText)findViewById(R.id.det3);
            EditText det4  = (EditText)findViewById(R.id.det4);
            EditText det5  = (EditText)findViewById(R.id.det5);
            EditText det6  = (EditText)findViewById(R.id.det6);
            EditText det7  = (EditText)findViewById(R.id.det7);
            EditText sc    = (EditText)findViewById(R.id.specialCare);
            EditText cn    = (EditText)findViewById(R.id.crateNumber);
            EditText c1    = (EditText)findViewById(R.id.changes1);
            EditText c2    = (EditText)findViewById(R.id.changes2);
            EditText d1    = (EditText)findViewById(R.id.exitDate);
            EditText d2    = (EditText)findViewById(R.id.installDate);
            EditText d3    = (EditText)findViewById(R.id.endDate);
            EditText d4    = (EditText)findViewById(R.id.returnDate);
            CheckBox sf    = (CheckBox)findViewById(R.id.supporFondazioneCheckBox);
            CheckBox pl    = (CheckBox)findViewById(R.id.plasticCheck);
            CheckBox pa    = (CheckBox)findViewById(R.id.paperCheck);
            CheckBox no    = (CheckBox)findViewById(R.id.tapeCheck);
            CheckBox et    = (CheckBox)findViewById(R.id.foamCheck);
            CheckBox fr    = (CheckBox)findViewById(R.id.foamRubberCheck);
            CheckBox s1    = (CheckBox)findViewById(R.id.samecondition1);
            CheckBox s2    = (CheckBox)findViewById(R.id.samecondition2);

            nrinv.setText(oldrep.getInvNum());
            det1.setText(oldrep.getDet1());
            det2.setText(oldrep.getDet2());
            det3.setText(oldrep.getDet3());
            det4.setText(oldrep.getDet4());
            det5.setText(oldrep.getDet5());
            det6.setText(oldrep.getDet6());
            det7.setText(oldrep.getDet7());
            sc.setText(oldrep.getSpecialCare());
            cn.setText(oldrep.getCrateNumber());
            c1.setText(oldrep.getChanges1());
            c2.setText(oldrep.getChanges2());
            d1.setText(oldrep.getExitDate());
            d2.setText(oldrep.getInstallDate());
            d3.setText(oldrep.getEndDate());
            d4.setText(oldrep.getReturnDate());
            sf.setChecked(oldrep.isSupportF());
            pl.setChecked(oldrep.isPlastic());
            pa.setChecked(oldrep.isPaper());
            no.setChecked(oldrep.isNoTape());
            et.setChecked(oldrep.isEthafoam());
            fr.setChecked(oldrep.isFoamRubber());
            s1.setChecked(oldrep.isSameCondition1());
            s2.setChecked(oldrep.isSameCondition2());
            for(String def : oldrep.getDefectsAsListofStrings()) {
                defectsChosen.add(def);
                LinearLayout l = (LinearLayout) findViewById(R.id.defsContainer);
                TextView newDefect = new TextView(this);
                newDefect.setText(def);
                l.addView(newDefect);
            }
            RadioGroup radioButtonGroup = (RadioGroup) findViewById(R.id.conditionGroup);
            for(int i = 0; i < radioButtonGroup.getChildCount(); i++){
                if(((RadioButton)radioButtonGroup.getChildAt(i)).getText().toString().equals(oldrep.getGenCondition())){
                    ((RadioButton)radioButtonGroup.getChildAt(i)).setChecked(true);
                    break;
                }
            }

        }


        ExhibitionContext = Exhibition.findById(Exhibition.class, id);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        LinearLayout defContainer = (LinearLayout) findViewById(R.id.defsContainer);
        int count = defContainer.getChildCount();
        String[] names = getResources().getStringArray(R.array.defect_choices);

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

        addFromCamera.setOnClickListener(
                new View.OnClickListener()
                {
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

        final EditText nr_inv_edit = (EditText)findViewById(R.id.nrInv);
        nr_inv_edit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    String nrinv  = nr_inv_edit.getText().toString();
                    nr_inv = nrinv;
                    if(nrinv!= null && nrinv.length()>0){
                        List<AutoFillRow> rows = AutoFillRow.find(AutoFillRow.class, "nrinv = ?",nrinv);
                        if(rows.size()>0){
                        AutoFillRow row = rows.get(0);
                        autofillForm(row);
                        }
                    }
                }
            }
        });
        touch = false;
        defectsChosen = new ArrayList<String>();
        defectChooser = (Spinner)findViewById(R.id.defect_choose);
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(this,R.array.defects_report,android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        defectChooser.setAdapter(spinnerAdapter);
        defectChooser.setPrompt("Choose Defect...");
        defectChooser.setOnItemSelectedListener(this);
        defectChooser.setOnTouchListener(this);

    }

    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id){
        if (touch) {
            //user has added a defect from the dropdown
            Spinner s = (Spinner) parent;
            final String defect = (String) s.getItemAtPosition(pos);
            if (!defectsChosen.contains(defect)) {
                defectsChosen.add(defect);
                LinearLayout l = (LinearLayout) findViewById(R.id.defsContainer);
                TextView newDefect = new TextView(this);
                newDefect.setText(defect);
                l.addView(newDefect);
                final EditText nr_inv_edit = (EditText)findViewById(R.id.nrInv);
                final String nrinv  = nr_inv_edit.getText().toString();
                //Clicking on specific defect will open prompt opening of photos annotated with that defect
                newDefect.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //user has clicked defect
                        AlertDialog.Builder alertadd = new AlertDialog.Builder(
                                CreateReportActivity.this);
                        LayoutInflater factory = LayoutInflater.from(CreateReportActivity.this);
                        ArrayList<AnnotatedImage> imgs = (ArrayList)AnnotatedImage.find(AnnotatedImage.class,"nr_inv = ?",nrinv);
                        final ArrayList<String> paths = new ArrayList<String>();
                        for(AnnotatedImage ai : imgs){
                            if(ai.getDefects().contains(defect)){
                                paths.add(ai.getPath());
                            }
                        }
                        final CharSequence[] paths_array  =  paths.toArray(new CharSequence[paths.size()]);

                        alertadd.setItems(paths_array, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent();
                                intent.setAction(android.content.Intent.ACTION_VIEW);
                                intent.setDataAndType(Uri.fromFile(new File(paths.get(which))), "image/png");
                                startActivity(intent);
                            }
                        });
                        alertadd.setNeutralButton("Here!", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dlg, int sumthin) {

                            }
                        });

                        alertadd.show();
                    }
                });

            }
        }
    }
    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        touch = true;
        return false;
    }
    private Report genNewReport() {


        String nrInv = ((EditText) (findViewById(R.id.nrInv))).getText().toString();
        String det1_et = ((EditText) (findViewById(R.id.det1))).getText().toString();
        String det2_et = ((EditText) (findViewById(R.id.det2))).getText().toString();
        String det3_et = ((EditText) (findViewById(R.id.det3))).getText().toString();
        String det4_et = ((EditText) (findViewById(R.id.det4))).getText().toString();
        String det5_et = ((EditText) (findViewById(R.id.det5))).getText().toString();
        String det6_et = ((EditText) (findViewById(R.id.det6))).getText().toString();
        String det7_et = ((EditText) (findViewById(R.id.det7))).getText().toString();
        String crateNum = ((EditText) (findViewById(R.id.crateNumber))).getText().toString();
        String specialCare = ((EditText) (findViewById(R.id.specialCare))).getText().toString();
        String defects = DBListHelper.listToString(defectsChosen);

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

        String changes1 = ((EditText) (findViewById(R.id.changes1))).getText().toString();
        String changes2 = ((EditText) (findViewById(R.id.changes2))).getText().toString();
        boolean checked1 = ((CheckBox) (findViewById(R.id.samecondition1))).isChecked();
        boolean checked2 = ((CheckBox) (findViewById(R.id.samecondition2))).isChecked();

        Date exitDate = null;
        Date installDate = null;
        Date endDate = null;
        Date returnDate = null;

        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");

        EditText exit = (EditText) findViewById(R.id.exitDate);
        EditText install = (EditText) findViewById(R.id.installDate);
        EditText end = (EditText) findViewById(R.id.endDate);
        EditText ret = (EditText) findViewById(R.id.returnDate);

        try{
            if (!(exit.getText().toString().equals(""))){
                exitDate = df.parse(exit.getText().toString());
            }
            if (!(install.getText().toString().equals(""))){
                installDate = df.parse(install.getText().toString());
            }
            if (!(end.getText().toString().equals(""))){
                endDate = df.parse(end.getText().toString());
            }
            if (!(ret.getText().toString().equals(""))){
                returnDate = df.parse(ret.getText().toString());
            }

        }catch (ParseException e){
            e.printStackTrace();
        }

        String position = "";
        if (vert)
            position = "Vertical";

        if (flat)
            position = "Flat";

        if (side)
            position = "On One Side";


        final Report rep = new Report(ExhibitionContext, nrInv, Calendar.getInstance().getTime(), det1_et, det2_et, det3_et, det4_et, det5_et, det6_et, det7_et,
                condition, specialCare, crateNum, supportF, plastic, paper, noTape, ethafoam, foamRubber, position,defects,exitDate,installDate,endDate,returnDate,
                changes1,changes2,checked1,checked2);
        rep.save();
        final Activity context = this;
        /*
            PDF creation begins below
         */
        new Thread(
            new Runnable(){
                public void run(){
                    LinearLayout cont = new LinearLayout(context);
                    ArrayList<Detail> dets = (ArrayList) rep.getDetailsAsList();

                    final int left_margin = 20;
                    final int right_margin = 575;
                    final int cent_padding = 22;
                    final int column_width = 170;
                    final int t1b = left_margin;
                    final int t1e = left_margin+column_width;
                    final int t2b = t1e+cent_padding;
                    final int t2e = t2b+column_width;
                    final int t3b = t2e+cent_padding;
                    final int t3e = right_margin;

                    PDFTextFactory tf = new PDFTextFactory(context);
                    TextView mainTitle = tf.makeSubtitle();
                    mainTitle.setText("Condition Report: Inv. #" + rep.getInvNum());
                    mainTitle.layout(20, 20, 595, 50);
                    cont.addView(mainTitle);
                    for(int i = 0; i < dets.size(); i++)
                    {
                        Detail d = dets.get(i);

                        TextView title = tf.makeSubtitle();
                        title.setText(d.getTitle());

                        TextView det = tf.makeBodyText();
                        det.setText(d.getDetail());

                        title.layout(t1b, 50 + (i * 79), t1e, 50 + (i * 79) + 20);
                        det.layout(t1b, 50 + (i * 79) + 20, t1e, 50 + (i * 79) + 20 + 59);
                        cont.addView(title);
                        cont.addView(det);

                    }

                    TextView wrap = tf.makeBodyText();
                    wrap.setText(rep.wrappingToString());

                    TextView prot = tf.makeBodyText();
                    prot.setText(rep.protectionToString());

                    TextView pos = tf.makeBodyText();
                    pos.setText(rep.getPosition());

                    TextView wraptitle = tf.makeSubtitle();
                    TextView prottitle = tf.makeSubtitle();
                    TextView postitle = tf.makeSubtitle();
                    //second column

                    wraptitle.setText("Wrapping"); postitle.setText("Position"); prottitle.setText("Protection");
                    wraptitle.layout(t2b, 50, t2e, 65); prottitle.layout(t2b, 85, t2e, 100); postitle.layout(t2b, 120, t2e, 135);
                    wrap.layout(t2b, 65, t2e, 80); prot.layout(t2b, 100, t2e, 115); pos.layout(t2b, 135, t2e, 150);

                    cont.addView(wraptitle); cont.addView(wrap); cont.addView(prottitle); cont.addView(prot); cont.addView(postitle); cont.addView(pos);

                    ArrayList<String> defs = rep.getDefectsAsListofStrings();
                    TextView defectstitle = tf.makeSubtitle();
                    defectstitle.setText("Defects");
                    defectstitle.layout(t2b, 160, t2e, 175);
                    cont.addView(defectstitle);

                    for(int i = 0; i < defs.size(); i++)
                    {
                        String d = defs.get(i);
                        TextView title = tf.makeBodyText();
                        title.setText(d);
                        title.layout(t2b, 175 + (i * 15), t2e, 175 + (i * 15) + 15);
                        cont.addView(title);
                    }
                    final int dates_begin = 355;
                    final int text_line = 15;
                    TextView datesandchange = tf.makeSubtitle();
                    datesandchange.setText("Dates and Changes");
                    datesandchange.layout(t2b,dates_begin,t2e,dates_begin+text_line);
                    cont.addView(datesandchange);
                    int vertical_cursor = dates_begin+text_line;
                    if (rep.hasExitDate()){
                        TextView datetext = tf.makeBodyText();
                        datetext.setText("Exit Date:\n"+rep.getExitDate());
                        datetext.layout(t2b, vertical_cursor, t2e, vertical_cursor + (2 * text_line));
                        cont.addView(datetext);
                        vertical_cursor += (3*text_line);
                    }
                    if (rep.hasInstallDate()){
                        TextView datetext = tf.makeBodyText();
                        datetext.setText("Install Date:\n"+rep.getInstallDate());
                        datetext.layout(t2b, vertical_cursor, t2e, vertical_cursor + (2 * text_line));
                        cont.addView(datetext);
                        vertical_cursor += (3*text_line);
                    }
                    TextView condition1 = tf.makeBodyText();
                    condition1.layout(t2b, vertical_cursor, t2e, vertical_cursor+(3*text_line));
                    if(rep.isSameCondition1())
                        condition1.setText("Condition unchanged");
                    else
                        condition1.setText("Condition Changed:\n"+rep.getChanges1());
                    cont.addView(condition1);
                    vertical_cursor += (4*text_line);

                    if (rep.hasEndDate()){
                        TextView datetext = tf.makeBodyText();
                        datetext.setText("End Date:\n"+rep.getEndDate());
                        datetext.layout(t2b, vertical_cursor, t2e, vertical_cursor + (2 * text_line));
                        cont.addView(datetext);
                        vertical_cursor += (3*text_line);
                    }

                    TextView condition2 = tf.makeBodyText();
                    condition2.layout(t2b, vertical_cursor, t2e, vertical_cursor+(3*text_line));
                    if(rep.isSameCondition2())
                        condition2.setText("Condition unchanged");
                    else
                        condition2.setText("Condition Changed:\n"+rep.getChanges2());
                    cont.addView(condition2);
                    vertical_cursor += (4*text_line);

                    if(rep.hasReturnDate()){
                        TextView datetext = tf.makeBodyText();
                        datetext.setText("Return Date:\n"+rep.getReturnDate());
                        datetext.layout(t2b, vertical_cursor, t2e, vertical_cursor + (2 * text_line));
                        vertical_cursor += (3*text_line);
                        cont.addView(datetext);
                    }

                    ArrayList<AnnotatedImage> imgs = (ArrayList)AnnotatedImage.find(AnnotatedImage.class, "nr_inv = ?", rep.getInvNum());
                    vertical_cursor = 50;
                    for(int i = 0; (i < 4 || i >= imgs.size()); i++){
                        ImageView iv = new ImageView(context);
                        iv.layout(t3b,vertical_cursor,t3e,vertical_cursor+column_width);
                        AnnotatedImage img = imgs.get(i);
                        Bitmap bitmap = BitmapFactory.decodeFile(img.getPath());
                        iv.setImageBitmap(bitmap);
                        cont.addView(iv);
                        vertical_cursor+=185;
                    }

                    PdfDocument document = new PdfDocument();

                    // crate a page description
                    PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(595,842,1).create();

                    // start a page
                    PdfDocument.Page page = document.startPage(pageInfo);

                    // draw something on the page
                    //LinearLayout datesandwrap = (LinearLayout)findViewById(R.id.datesandwrappingcont);


                    cont.draw(page.getCanvas());
                    // finish the page
                    document.finishPage(page);
                    try {
                        verifyStoragePermissions(context);
                        File root = Environment.getExternalStorageDirectory();
                        root = new File(root,"ArcheoReport/Reports/"+rep.getExhibition().getName()+"/");
                        root.mkdirs();
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
                }
            }).start();

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

        imageCount++;
        TextView imgticker = (TextView)findViewById(R.id.imageticker);
        imgticker.setText(imageCount + " Photos Added.");

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

    private void autofillForm(AutoFillRow row) {
        ((EditText) (findViewById(R.id.det1))).setText(row.getOggetto());
        ((EditText) (findViewById(R.id.det2))).setText(row.getTecnica());
        ((EditText) (findViewById(R.id.det3))).setText(row.getDimensione());
        ((EditText) (findViewById(R.id.det4))).setText(row.getDatazione());
        ((EditText) (findViewById(R.id.det5))).setText(row.getCollocazione());
        ((EditText) (findViewById(R.id.det6))).setText(row.getStato_conserv());
        ((EditText) (findViewById(R.id.det7))).setText(row.getInterventi());
    }
}

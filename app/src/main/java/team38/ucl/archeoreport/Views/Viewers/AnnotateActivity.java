package team38.ucl.archeoreport.Views.Viewers;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.AnticipateOvershootInterpolator;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import team38.ucl.archeoreport.AnnotationView;
import team38.ucl.archeoreport.Models.AnnotatedImage;
import team38.ucl.archeoreport.Models.Annotation;
import team38.ucl.archeoreport.Models.Defect;
import team38.ucl.archeoreport.Models.Exhibition;
import team38.ucl.archeoreport.R;

public class AnnotateActivity extends AppCompatActivity implements OnItemSelectedListener {
    static final int REQUEST_IMAGE_CAPTURE = 1;
    public Spinner colSpin;
    public Spinner defSpin;
    public Spinner sizeSpin;
    public AnnotationView v;
    Exhibition exhibitionContext;
    public final String IMAGE_PATH_HEAD = "/ArcheoReport/images/";
    private Uri initialUri;
    private String nrInv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent callingIntent = getIntent();
        initialUri = callingIntent.getParcelableExtra("imageURI");
        nrInv= callingIntent.getStringExtra("nrInv");
        String ex_ID = callingIntent.getStringExtra("Exhibition");
        exhibitionContext = Exhibition.findById(Exhibition.class,Long.parseLong(ex_ID));

        try {
            Bitmap unannotated = MediaStore.Images.Media.getBitmap(getContentResolver(), initialUri);
            v = (AnnotationView)findViewById(R.id.annotation);
            v.setBgimg(unannotated);

        } catch (IOException e) {
            e.printStackTrace();
        }
        colSpin = (Spinner)findViewById(R.id.colorspinner);
        defSpin = (Spinner)findViewById(R.id.defspinner);
        sizeSpin = (Spinner)findViewById(R.id.pensizespin);
        Button saveButton = (Button)findViewById(R.id.savebutton);
        ToggleButton eraserButton = (ToggleButton)findViewById(R.id.eraserbutton);

        ArrayAdapter<CharSequence> colAdapter = ArrayAdapter.createFromResource(this,R.array.color_choices,android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> defAdapter = ArrayAdapter.createFromResource(this,R.array.defect_choices,android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> sizeAdapter = ArrayAdapter.createFromResource(this,R.array.size_choices,android.R.layout.simple_spinner_item);

        colAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        defAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sizeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        colSpin.setAdapter(colAdapter);
        defSpin.setAdapter(defAdapter);
        sizeSpin.setAdapter(sizeAdapter);

        colSpin.setPrompt("Choose Color");
        defSpin.setPrompt("Defect");
        sizeSpin.setPrompt("Pen Size");
        colSpin.setOnItemSelectedListener(this);
        defSpin.setOnItemSelectedListener(this);
        sizeSpin.setOnItemSelectedListener(this);

        eraserButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // The toggle is enabled
                    v = (AnnotationView) findViewById(R.id.annotation);
                    v.setEraserMode(true);
                } else {
                    // The toggle is disabled
                    v = (AnnotationView) findViewById(R.id.annotation);
                    v.setEraserMode(false);
                }
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                v = (AnnotationView) findViewById(R.id.annotation);
                saveAnnotatedImage(v);
                finish();
            }
        });

    }

    private void saveAnnotatedImage(AnnotationView view)
    {
        view.setDrawingCacheEnabled(true);
        Bitmap b = view.getDrawingCache();
        String root = Environment.getExternalStorageDirectory().toString();
        File storageDir = new File(root+"/ArcheoReport/Images/"+nrInv);
        storageDir.mkdirs();
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = nrInv + timeStamp + "_annotated";
        try {
            File image = File.createTempFile
                    (
                            imageFileName,  /* prefix */
                            ".png",         /* suffix */
                            storageDir      /* directory */
                    );
            b.compress(Bitmap.CompressFormat.PNG,100,new FileOutputStream(image));
            AnnotatedImage anImage = new AnnotatedImage(image.getPath(),nrInv,exhibitionContext);
            anImage.save();
            ArrayList<String> defs = view.getDefects();
            for (String s : defs){
                Annotation a = new Annotation(s,anImage);
                a.save();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }



    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)
        Spinner spinner = (Spinner)parent;
        v = (AnnotationView)findViewById(R.id.annotation);
        Toast toast;
        if(spinner.getId() == R.id.colorspinner){
            String col = (String)spinner.getItemAtPosition(pos);
            v.setColor(col);
            toast = Toast.makeText(this,col +" Chosen",Toast.LENGTH_SHORT);
            toast.show();
        }

        if(spinner.getId() == R.id.pensizespin){
            int size = Integer.parseInt((String)spinner.getItemAtPosition(pos));
            v.setPenSize(size);
            toast = Toast.makeText(this,"Marker size: "+size,Toast.LENGTH_SHORT);
            toast.show();
        }
        if(spinner.getId() == R.id.defspinner){
            String defect = (String)spinner.getItemAtPosition(pos);
            v.setDefect(defect);
            toast = Toast.makeText(this,"Annotations will be registered as: "+defect,Toast.LENGTH_LONG);
            toast.show();
        }
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

package team38.ucl.archeoreport;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.media.Image;
import android.os.Bundle;
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

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements OnItemSelectedListener {
    static final int REQUEST_IMAGE_CAPTURE = 1;
    public Spinner colSpin;
    public Spinner defSpin;
    public Spinner sizeSpin;
    public AnnotationView v;
    public final String IMAGE_PATH_HEAD = getString(R.string.imagepath);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dispatchTakePictureIntent();
            }
        });

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
            }
        });

    }

    private void saveAnnotatedImage(AnnotationView view)
    {
        view.setDrawingCacheEnabled(true);
        Bitmap b = view.getDrawingCache();
        String invnum = view.getInvNum();

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        Date now = Calendar.getInstance().getTime();
        String imgPath = IMAGE_PATH_HEAD+invnum+"/"+df.format(now);
        try {
            b.compress(Bitmap.CompressFormat.PNG,100,new FileOutputStream(imgPath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        SQLiteDatabase myDB  = openOrCreateDatabase("ArcheoReport",MODE_PRIVATE,null);
        myDB.execSQL("CREATE TABLE IF NOT EXISTS Images(invNum VARCHAR, Path VARCHAR, Defects VARCHAR);");


    }
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            v = (AnnotationView)findViewById(R.id.annotation);
            v.setBgimg(imageBitmap);
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

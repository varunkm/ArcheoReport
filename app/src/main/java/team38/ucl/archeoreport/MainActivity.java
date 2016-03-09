package team38.ucl.archeoreport;

import android.content.Intent;
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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity implements OnItemSelectedListener {
    static final int REQUEST_IMAGE_CAPTURE = 1;
    public Spinner colSpin;
    public Spinner defSpin;
    public AnnotationView v;
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
        Button saveButton = (Button)findViewById(R.id.savebutton);
        ToggleButton eraserButton = (ToggleButton)findViewById(R.id.eraserbutton);
        ArrayAdapter<CharSequence> colAdapter = ArrayAdapter.createFromResource(this,R.array.color_choices,android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> defAdapter = ArrayAdapter.createFromResource(this,R.array.defect_choices,android.R.layout.simple_spinner_item);

        colAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        defAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        colSpin.setAdapter(colAdapter);
        defSpin.setAdapter(defAdapter);
        colSpin.setOnItemSelectedListener(this);
        defSpin.setOnItemSelectedListener(this);

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

        if(spinner.getId() == R.id.colorspinner){
            String col = (String)spinner.getItemAtPosition(pos);
            v.setColor(col);
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

package team38.ucl.archeoreport.Models;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.opencsv.CSVReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import team38.ucl.archeoreport.R;
public class AddCSVFileActivity extends AppCompatActivity {
    final int PICKFILE_RESULT_CODE = 4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_csvfile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("text/csv");
        startActivityForResult(Intent.createChooser(intent, "Select CSV File"), PICKFILE_RESULT_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case PICKFILE_RESULT_CODE:
                if(resultCode == RESULT_OK){
                    parseCSV(data.getData().getPath());
                    finish();
                }
                break;
            default:
                finish();
                break;
        }
        finish();
    }

    private void parseCSV(String path){
        try {
            CSVReader reader = new CSVReader(new FileReader(path));
            String [] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                // there should be 11 columns
                AutoFillRow row = new AutoFillRow(nextLine[0],nextLine[1],nextLine[2],nextLine[3],
                        nextLine[4],nextLine[5],nextLine[6],nextLine[7],nextLine[8],nextLine[9],nextLine[10]);
                row.save();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ArrayIndexOutOfBoundsException e){
            e.printStackTrace();
        }

    }
}

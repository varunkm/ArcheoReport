package team38.ucl.archeoreport.Views.Viewers;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import team38.ucl.archeoreport.Models.AddCSVFileActivity;
import team38.ucl.archeoreport.Models.AnnotatedImage;
import team38.ucl.archeoreport.Models.Exhibition;
import team38.ucl.archeoreport.Models.Report;
import team38.ucl.archeoreport.R;
import team38.ucl.archeoreport.Views.Creators.CreateReportActivity;

/*
* ViewReportsActivity
*
* This is the controller for the reports view. This is the screen where the user is presented with a list of reports loaded from the database under
* the context of a specific exhibition
*
*
*
* @author Varun Mathur
* */

public class ViewReportsActivity extends AppCompatActivity {
    private List<Report> reports;
    private Exhibition exContext;
    private ReportListAdapter repsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_view_reports);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        Intent intent = getIntent();
        Long id = intent.getLongExtra("ExhibitionID", 0);
        //load contextual exhibition from intent
        exContext = Exhibition.findById(Exhibition.class,id);

        reports = Report.find(Report.class, "Exhibition = ?", exContext.getId().toString());
        ListView reportslst = (ListView)findViewById(R.id.reportslist);

        repsAdapter = new ReportListAdapter(this);
        reportslst.setAdapter(new ReportListAdapter(this));

        reportslst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(final AdapterView<?> adapter, View v, final int position, long id) {
                CharSequence[] choices = {"View PDF", "Modify"};
                AlertDialog.Builder builder = new AlertDialog.Builder(ViewReportsActivity.this);
                builder.setTitle("Choose Action...")
                        .setItems(choices, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                if(which == 0) {
                                    //if the user selects a report in the list, open the corresponding PDF in the device's native document viewer
                                    Report item = (Report) adapter.getItemAtPosition(position);
                                    File file = new File(item.getPdfpath());
                                    Intent intent = new Intent(Intent.ACTION_VIEW);
                                    intent.setDataAndType(Uri.fromFile(file), "application/pdf");
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                                    startActivity(intent);
                                }
                                else{
                                    Intent intent = new Intent(ViewReportsActivity.this, CreateReportActivity.class);
                                    intent.putExtra("ExhibitionID", exContext.getId());
                                    intent.putExtra("ModifyOld",new Long(1));
                                    Report item = (Report) adapter.getItemAtPosition(position);
                                    intent.putExtra("OldReport",item.getId());
                                    startActivity(intent);
                                }
                            }
                        });
                builder.show();
            }

        });


        //The user is presented with two actions, view gallery and create new report.

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ViewReportsActivity.this, CreateReportActivity.class);
                intent.putExtra("ExhibitionID", exContext.getId());
                intent.putExtra("ModifyOld",new Long(0));
                startActivity(intent);
            }
        });

        FloatingActionButton fab2 = (FloatingActionButton)findViewById(R.id.fab2);
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ViewReportsActivity.this, GalleryActivity.class);
                startActivity(intent);
            }
        });
    }

    /*
    * Handle user clicking the "import csv" button in the action bar. Launches AddCSVFileActivity
    * */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_importcsv:
                Intent intent = new Intent(this, AddCSVFileActivity.class);
                startActivity(intent);

            case R.id.action_settings:

                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }
    @Override
    protected void onRestart() {
        super.onRestart();
        reInitialiseList();
    }

    @Override
    protected void onResume() {
        super.onResume();
        reInitialiseList();
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_exhibition, menu);
        return true;
    }

    private void reInitialiseList()
    {
        reports =  Report.find(Report.class, "Exhibition = ?", exContext.getId().toString());
        ListView reportslst = (ListView)findViewById(R.id.reportslist);
        repsAdapter = new ReportListAdapter(this);
        reportslst.setAdapter(repsAdapter);
        repsAdapter.notifyDataSetChanged();
    }

    /*
    * Custom array adapter for the reports listview. Populates R.layout.report_list_item with data from the report
    * */
    private class ReportListAdapter extends ArrayAdapter<Report> {
        private Context context;
        public ReportListAdapter(Context context){
            super(ViewReportsActivity.this, R.layout.report_list_item, reports);
            this.context=context;
        }

        //adding an element to our list
        @Override
        public View getView(int position, View view, ViewGroup parent){
            if(view == null)
                view = getLayoutInflater().inflate(R.layout.report_list_item, parent, false);

            Report curRep = reports.get(position);

            TextView name = (TextView) view.findViewById(R.id.invnum);
            name.setText(curRep.getInvNum());

            TextView date = (TextView) view.findViewById(R.id.date);
            date.setText(curRep.getDate().toString());

            //Set image in list item to be the first image found that belongs to the report
            ArrayList<AnnotatedImage> images = (ArrayList)AnnotatedImage.find(AnnotatedImage.class,"nr_inv = ?",curRep.getInvNum());
            ImageView imageView = (ImageView)view.findViewById(R.id.reportthumb);
            if (images.size() > 0){
                Log.i("IMAGE PATH", images.get(0).getPath());
                File f = new File(images.get(0).getPath());
                Picasso.with(ViewReportsActivity.this).load(f).centerCrop().resize(200,200).into(imageView);
            }
            else{
                imageView.setImageDrawable(null);
            }
            return view;
        }

        private List<File> getListFiles(File parentDir) {
            ArrayList<File> inFiles = new ArrayList<File>();
            File[] files = parentDir.listFiles();
            for (File file : files) {
                if (file.isDirectory()) {
                    inFiles.addAll(getListFiles(file));
                } else {
                    if (file.getName().endsWith(".png")) {
                        inFiles.add(file);
                    }
                }
            }
            return inFiles;
        }

        public Report getItem(int position){
            return reports.get(position);
        }
    }

}

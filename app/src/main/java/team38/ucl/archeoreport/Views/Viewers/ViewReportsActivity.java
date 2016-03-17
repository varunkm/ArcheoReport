package team38.ucl.archeoreport.Views.Viewers;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import team38.ucl.archeoreport.Models.Exhibition;
import team38.ucl.archeoreport.Models.Report;
import team38.ucl.archeoreport.R;
import team38.ucl.archeoreport.Views.Creators.CreateReportActivity;


public class ViewReportsActivity extends AppCompatActivity {
    private List<Report> reports;
    private Exhibition exContext;
    private ReportListAdapter repsAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        //TODO Implement image adding

        setContentView(R.layout.activity_view_reports);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        Intent intent = getIntent();
        Long id = intent.getLongExtra("ExhibitionID", 0);
        exContext = Exhibition.findById(Exhibition.class,id);

        reports = Report.find(Report.class, "Exhibition = ?", exContext.getId().toString());
        ListView reportslst = (ListView)findViewById(R.id.reportslist);
        repsAdapter = new ReportListAdapter();
        reportslst.setAdapter(new ReportListAdapter());
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ViewReportsActivity.this, CreateReportActivity.class);
                intent.putExtra("ExhibitionID", exContext.getId());

                startActivity(intent);
                //TODO: Handle onResume,
            }
        });
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

    private void reInitialiseList()
    {
        reports =  Report.find(Report.class, "Exhibition = ?", exContext.getId().toString());
        ListView reportslst = (ListView)findViewById(R.id.reportslist);
        repsAdapter = new ReportListAdapter();
        reportslst.setAdapter(repsAdapter);
        repsAdapter.notifyDataSetChanged();
    }


    private class ReportListAdapter extends ArrayAdapter<Report> {
        public ReportListAdapter(){
            super(ViewReportsActivity.this, R.layout.report_list_item, reports);
        }

        //adding an element to our list
        @Override
        public View getView(int position, View view, ViewGroup parent){;

            if(view == null)
                view = getLayoutInflater().inflate(R.layout.report_list_item, parent, false);

            Report curRep = reports.get(position);

            TextView name = (TextView) view.findViewById(R.id.invnum);
            name.setText(curRep.getInvNum());

            TextView date = (TextView) view.findViewById(R.id.date);
            date.setText(curRep.getDate().toString());

            view.setBackgroundResource(R.drawable.rounded_corner);

            return view;
        }
    }

}

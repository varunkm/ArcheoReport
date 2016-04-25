package team38.ucl.archeoreport.Views.Viewers;

import android.content.Intent;
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
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import team38.ucl.archeoreport.Models.AddCSVFileActivity;
import team38.ucl.archeoreport.Models.Exhibition;
import team38.ucl.archeoreport.R;
import team38.ucl.archeoreport.Views.Creators.AddExhibitionActivity;

public class ViewExhibitionsActivity extends AppCompatActivity {

    private List<Exhibition> exhibitions;
    ExhibitionListAdapter exAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //BEGIN BOILERPLATE
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_exhibitions);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Exhibitions");
        //END BOILERPLATE


        //Initialise collection, ListView and adapter:
        exhibitions = Exhibition.listAll(Exhibition.class);
        ListView exListView = (ListView)findViewById(R.id.exhibitionlist);
        exAdapter = new ExhibitionListAdapter();
        exListView.setAdapter(exAdapter);
        exAdapter.notifyDataSetChanged();
        if (exhibitions.size() == 0){
            TextView exviewtitle = (TextView)findViewById(R.id.exviewtitle);
            exviewtitle.setText("Add an Exhibition by clicking the icon in the bottom right...");
        }
        else
        {
            TextView exviewtitle = (TextView)findViewById(R.id.exviewtitle);
            exviewtitle.setText("Exhibitions");
        }
        exListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position,long id) {
                //If user clicks on exhibition, open reports view passing contextual exhibition with it
                Exhibition item = exhibitions.get(position);
                Intent intent = new Intent(ViewExhibitionsActivity.this, ViewReportsActivity.class);
                intent.putExtra("ExhibitionID",item.getId());
                startActivity(intent);
            }
        });


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ViewExhibitionsActivity.this, AddExhibitionActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        reInitialiseList();
        if (exhibitions.size() == 0){
            TextView exviewtitle = (TextView)findViewById(R.id.exviewtitle);
            exviewtitle.setText("Add an Exhibition by clicking the icon in the bottom right...");
        }
        else
        {
            TextView exviewtitle = (TextView)findViewById(R.id.exviewtitle);
            exviewtitle.setText("Exhibitions");
        }
        Log.i("VIEW EXHIBITIONS","ONRESTART");
    }
    private void reInitialiseList()
    {
        exhibitions = Exhibition.listAll(Exhibition.class);
        ListView exListView = (ListView)findViewById(R.id.exhibitionlist);
        exAdapter = new ExhibitionListAdapter();
        exListView.setAdapter(exAdapter);
        exAdapter.notifyDataSetChanged();
    }



    @Override
    protected void onResume() {
        super.onResume();
        reInitialiseList();
        Log.i("VIEW EXHIBITIONS", "ONRESUME");
        if (exhibitions.size() == 0){
            TextView exviewtitle = (TextView)findViewById(R.id.exviewtitle);
            exviewtitle.setText("Add an Exhibition by clicking the icon in the bottom right...");
        }
        else
        {
            TextView exviewtitle = (TextView)findViewById(R.id.exviewtitle);
            exviewtitle.setText("Exhibitions");
        }

    }

    private class ExhibitionListAdapter extends ArrayAdapter<Exhibition> {
        public ExhibitionListAdapter(){
            super(ViewExhibitionsActivity.this, R.layout.exhibition_list_item, exhibitions);
        }

        //adding an element to our list
        @Override
        public View getView(int position, View view, ViewGroup parent){
            if(view == null)
                view = getLayoutInflater().inflate(R.layout.exhibition_list_item, parent, false);

            final Exhibition currentExhibition = exhibitions.get(position);

            TextView name = (TextView) view.findViewById(R.id.exhibitName);
            name.setText(currentExhibition.getName());

            TextView location = (TextView) view.findViewById(R.id.location);
            location.setText(currentExhibition.getLocation());

            TextView date = (TextView) view.findViewById(R.id.date);
            String dateStr = "";
            SimpleDateFormat formatter=new SimpleDateFormat("dd-MM-yyyy");
            dateStr+=formatter.format(currentExhibition.getStartDate());
            dateStr+=" - ";
            dateStr+=formatter.format(currentExhibition.getEndDate());
            date.setText(dateStr);

            ImageButton btn = (ImageButton)view.findViewById(R.id.deletebutton);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ExhibitionListAdapter.this.remove(currentExhibition);
                    ExhibitionListAdapter.this.notifyDataSetChanged();
                    currentExhibition.delete();
                }
            });
            return view;
        }

    }
}

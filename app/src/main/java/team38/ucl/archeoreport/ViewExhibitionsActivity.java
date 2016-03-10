package team38.ucl.archeoreport;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ViewExhibitionsActivity extends AppCompatActivity {

    private ArrayList<Exhibition> exhibitions;
    private SQLiteDatabase myDB;
    ExhibitionListAdapter exAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_exhibitions);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Exhibitions");
        exhibitions = new ArrayList<Exhibition>();
        ListView exListView = (ListView)findViewById(R.id.exhibitionlist);
        exAdapter = new ExhibitionListAdapter();
        exListView.setAdapter(exAdapter);

        LoadExhibitionsFromDB();
        exAdapter.notifyDataSetChanged();

        myDB = openOrCreateDatabase("ArcheoReport", MODE_PRIVATE,null);
        myDB.execSQL("CREATE TABLE IF NOT EXISTS Exhibitions(Name VARCHAR,Location VARCHAR,Date INT);");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private void LoadExhibitionsFromDB(){
        Cursor resultset = myDB.rawQuery("Select * from Exhibitions",null);
        if (resultset.getCount() == 0)
        {
            exhibitions = new ArrayList<Exhibition>();
            return;
        }
        else{
            resultset.moveToFirst();
            do{
                Exhibition ex;
                String name = resultset.getString(1);
                String loc = resultset.getString(2);
                Date date = new Date(resultset.getInt(3));
                resultset.moveToNext();
                ex = new Exhibition(name,loc,date);
                exhibitions.add(ex);

            } while(!resultset.isLast());
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

            Exhibition currentExhibition = exhibitions.get(position);

            TextView name = (TextView) view.findViewById(R.id.exhibitName);
            name.setText(currentExhibition.getName());

            TextView location = (TextView) view.findViewById(R.id.location);
            location.setText(currentExhibition.getLocation());

            TextView date = (TextView) view.findViewById(R.id.date);
            date.setText(currentExhibition.getDate().toString());

            return view;
        }
    }
}

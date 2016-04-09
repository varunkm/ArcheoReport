package team38.ucl.archeoreport.Views.Viewers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import team38.ucl.archeoreport.Models.AnnotatedImage;
import team38.ucl.archeoreport.Models.Exhibition;
import team38.ucl.archeoreport.Models.Report;
import team38.ucl.archeoreport.R;

public class GalleryActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    String currentExhibition = null;
    String currentInvNum = null;
    String currentDefect = null;
    GalleryAdapter galleryAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ArrayList<AnnotatedImage> images = (ArrayList)AnnotatedImage.listAll(AnnotatedImage.class);
        galleryAdapter = new GalleryAdapter(this,R.layout.image_in_gallery,images);
        GridView galleryView = (GridView)findViewById(R.id.gallery);
        galleryView.setAdapter(galleryAdapter);
        galleryAdapter.notifyDataSetChanged();

        galleryView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AnnotatedImage selected= (AnnotatedImage)parent.getItemAtPosition(position);
                Intent intent = new Intent();
                intent.setAction(android.content.Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.fromFile(new File(selected.getPath())), "image/png");
                startActivity(intent);
            }
        });

        Spinner exSpinner = (Spinner)findViewById(R.id.exhibFilter);
        Spinner repSpinner = (Spinner)findViewById(R.id.reportFilter);
        Spinner defSpinner = (Spinner)findViewById(R.id.defectFilter);

        ArrayList<Exhibition> exhibitions= (ArrayList<Exhibition>)Exhibition.listAll(Exhibition.class);
        ArrayList<Report> reports = (ArrayList<Report>)Report.listAll(Report.class);

        ArrayList<String> defects_string = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.defect_choices)));
        ArrayList<String> exhibs_string  = new ArrayList<>();
        ArrayList<String> reps_string = new ArrayList<>();
        for(Exhibition s : exhibitions)
            exhibs_string.add(s.getName());
        for(Report r : reports)
            reps_string.add(r.getInvNum());

        ArrayAdapter<String> exAdapt = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,exhibs_string);
        ArrayAdapter<String> repAdapt = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,reps_string);
        ArrayAdapter<String> defAdapt = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,defects_string);


        exAdapt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        repAdapt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        defAdapt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        exSpinner.setAdapter(exAdapt);
        repSpinner.setAdapter(repAdapt);
        defSpinner.setAdapter(defAdapt);

        exSpinner.setOnItemSelectedListener(this);
        repSpinner.setOnItemSelectedListener(this);
        defSpinner.setOnItemSelectedListener(this);



    }

    public void onItemSelected(AdapterView<?> parent,View view, int pos, long id){
        Log.i("FILTER", "itemSelected");
        switch (parent.getId()){
            case R.id.exhibFilter:
                currentExhibition = (String) parent.getItemAtPosition(pos);
                Log.i("FILTER", "exhib");
                break;
            case R.id.reportFilter:
                currentInvNum = (String) parent.getItemAtPosition(pos);
                Log.i("FILTER", "report");
                break;
            case R.id.defectFilter:
                currentDefect = (String) parent.getItemAtPosition(pos);
                Log.i("FILTER", "defect");
                break;
        }
        reloadGallery();
    }

    public void onNothingSelected(AdapterView<?> parent){
        resetGallery();
    }

    private void reloadGallery(){
        ArrayList<AnnotatedImage> images = (ArrayList)AnnotatedImage.listAll(AnnotatedImage.class);
        Log.i("RELOAD GALLERY BEFORE", ""+images.size());
        Log.i("RELOAD GALLERY", "curEx"+currentExhibition);
        Log.i("RELOAD GALLERY", "curRep"+currentInvNum);
        Log.i("RELOAD GALLERY", "curDefect"+currentDefect);

        if(currentExhibition != null) {
            Exhibition ex = Exhibition.find(Exhibition.class, "name = ?", currentExhibition).get(0);
            Iterator i = images.iterator();
            while(i.hasNext()){
                AnnotatedImage x = (AnnotatedImage)i.next();
                if(!(x.getEx().getId() == ex.getId())){
                    i.remove();
                }
            }

        }
        Log.i("AFTER EXHIBITION", ""+images.size());

        if(currentInvNum!=null){
            Iterator i = images.iterator();
            while(i.hasNext()){
                AnnotatedImage x = (AnnotatedImage)i.next();
                Log.i("InvNums: ", x.getNrInv()+", "+currentInvNum);
                if(!(x.getNrInv().equals(currentInvNum))){
                    i.remove();
                }
            }
        }
        Log.i("AFTER REPORT", ""+images.size());

        if(currentDefect != null && !currentDefect.equals("General")) {
            String defect = currentDefect;
            Iterator i = images.iterator();
            while(i.hasNext()){
                AnnotatedImage x = (AnnotatedImage)i.next();
                Log.i("DEFECTS ITEM", x.getDefects().size()+"");
                if(!(x.getDefects().contains(defect))){
                    i.remove();
                }
            }
        }
        Log.i("RELOAD GALLERY AFTER", ""+images.size());
        galleryAdapter = new GalleryAdapter(this,R.layout.image_in_gallery,images);
        GridView galleryView = (GridView)findViewById(R.id.gallery);
        galleryView.setAdapter(galleryAdapter);
        galleryAdapter.notifyDataSetChanged();

    }
    private void resetGallery(){
        Log.i("RESET", "Nothing Selected");
        currentDefect = null;
        currentInvNum = null;
        currentExhibition = null;
        reloadGallery();
    }

}

class GalleryAdapter extends ArrayAdapter<AnnotatedImage>{
    private Context context;
    private int layoutResourceId;
    private ArrayList<AnnotatedImage> data = new ArrayList<>();

    public GalleryAdapter(Context context, int layoutResourceId, ArrayList data){
        super(context,layoutResourceId,data);
        this.context = context;
        this.layoutResourceId = layoutResourceId;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;

        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        row = inflater.inflate(layoutResourceId, parent, false);
        TextView inv = (TextView)row.findViewById(R.id.itemnum);
        ImageView img = (ImageView)row.findViewById(R.id.itemimage);
        AnnotatedImage item = (AnnotatedImage)getItem(position);
        File f = new File(item.getPath());
        inv.setText(item.getNrInv());
        Picasso.with(context).load(f).into(img);

        return row;
    }
}
package team38.ucl.archeoreport.Models;

import com.orm.SugarRecord;

import java.util.ArrayList;

/**
 * Created by varunmathur on 20/03/16.
 */
public class AnnotatedImage extends SugarRecord {
    Exhibition ex;
    String path;
    String nrInv;

    public AnnotatedImage(){

    }

    public AnnotatedImage(String path, String nrInv, Exhibition ex) {
        this.path = path;
        this.nrInv = nrInv;
        this.ex = ex;
    }

    public String getPath() {
        return path;
    }

    public String getNrInv() {
        return nrInv;
    }

    public ArrayList<Annotation> getAnnotations(){
        return (ArrayList)Annotation.find(Annotation.class, "image = ?", this.getId().toString());
    }

    public ArrayList<Defect> getDefects(){
        ArrayList<Defect> defects = new ArrayList<>();
        for(Annotation a : getAnnotations()){
            defects.add(a.getDefect());
        }
        return defects;
    }
}

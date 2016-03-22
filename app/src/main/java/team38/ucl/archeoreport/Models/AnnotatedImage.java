package team38.ucl.archeoreport.Models;

import com.orm.SugarRecord;

import java.util.ArrayList;

/**
 * Created by varunmathur on 20/03/16.
 */
public class AnnotatedImage extends SugarRecord {
    public Exhibition getEx() {
        return ex;
    }

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

    public ArrayList<String> getDefects(){
        ArrayList<String> defects = new ArrayList<>();
        for(Annotation a : getAnnotations()){
            if(a.getDefect()!=null)
                defects.add(a.getDefect());
        }
        return defects;
    }

    public boolean equals(AnnotatedImage other){
        boolean exhib = ex.equals(other.ex);
        boolean p = path.equals(other.getPath());
        boolean inv = nrInv.equals(other.getNrInv());
        return exhib && p && inv;
    }
}

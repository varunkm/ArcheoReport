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
    String defects;
    public AnnotatedImage(){

    }

    public AnnotatedImage(String path, String nrInv, Exhibition ex, ArrayList<String> defects) {
        this.path = path;
        this.nrInv = nrInv;
        this.ex = ex;
        this.defects = DBListHelper.listToString(defects);
    }

    public String getPath() {
        return path;
    }

    public String getNrInv() {
        return nrInv;
    }

    public ArrayList<String> getDefects(){
        return DBListHelper.stringToList(defects);
    }

    public boolean equals(AnnotatedImage other){
        boolean exhib = ex.equals(other.ex);
        boolean p = path.equals(other.getPath());
        boolean inv = nrInv.equals(other.getNrInv());
        return exhib && p && inv;
    }
}

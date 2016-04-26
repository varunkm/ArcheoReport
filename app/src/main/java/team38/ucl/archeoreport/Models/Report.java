package team38.ucl.archeoreport.Models;

import com.orm.SugarRecord;
import com.orm.dsl.Ignore;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by varunmathur on 11/03/16.
 *
 */

public class Report extends SugarRecord {
    @Ignore
    SimpleDateFormat df;

    Exhibition exhibition;
    String invNum;
    long dateCreated;
    String det1;
    String det2;
    String det3;
    String det4;
    String det5;
    String det6;
    String det7;
    String defects;
    String genCondition;
    String specialCare;
    String crateNumber;
    boolean supportF;
    boolean plastic;
    boolean paper;
    boolean noTape;
    boolean ethafoam;
    boolean foamRubber;
    String position;
    String pdfpath;

    long exitDate;
    long installDate;
    long endDate;
    long returnDate;

    String changes1;
    String changes2;

    boolean sameCondition1;
    boolean sameCondition2;

    public Report(Exhibition exhibition, String invNum, Date date, String det1, String det2, String det3, String det4, String det5, String det6, String det7, String genCondition, String specialCare, String crateNumber, boolean supportF,
                  boolean plastic, boolean paper, boolean noTape, boolean ethafoam, boolean foamRubber, String position, String defects, Date exitDate, Date installDate, Date endDate, Date
                  returnDate, String changes1, String changes2, boolean checked1, boolean checked2) {
        this.exhibition = exhibition;
        this.invNum = invNum;
        this.dateCreated = date.getTime();
        this.det1 = det1;
        this.det2 = det2;
        this.det3 = det3;
        this.det4 = det4;
        this.det5 = det5;
        this.det6 = det6;
        this.det7 = det7;
        this.genCondition = genCondition;
        this.specialCare = specialCare;
        this.crateNumber = crateNumber;
        this.supportF = supportF;
        this.plastic = plastic;
        this.paper = paper;
        this.noTape = noTape;
        this.ethafoam = ethafoam;
        this.foamRubber = foamRubber;
        this.defects = defects;
        this.position = position;
        this.changes1 = changes1;
        this.changes2 = changes2;
        this.sameCondition1 = checked1;
        this.sameCondition2 = checked2;
        if(exitDate!= null)
            this.exitDate = exitDate.getTime();
        if(installDate!=null)
            this.installDate = installDate.getTime();
        if(endDate!=null)
            this.endDate = endDate.getTime();
        if(returnDate!= null)
            this.returnDate = returnDate.getTime();
        df = new SimpleDateFormat("dd/MM/yyyy");
    }

    //TODO Generate PDF


    public Report() {
    }

    public Date getDate()
    {
        return new Date(dateCreated);
    }

    public String getChanges1() {
        if (!this.sameCondition1)
            return changes1;
        else
            return "";
    }

    public String getChanges2() {
        if(!sameCondition2)
            return changes2;
        else
            return "";
    }

    public boolean isSameCondition1() {
        return sameCondition1;
    }

    public boolean isSameCondition2() {
        return sameCondition2;
    }

    public String getExitDate(){
        df = new SimpleDateFormat("dd/MM/yyyy");
        if (hasExitDate()) {
            Date d = new Date(exitDate);
            return df.format(d);
        }
        else
            return "";
    }

    public String getInstallDate(){
        df = new SimpleDateFormat("dd/MM/yyyy");
        if (hasInstallDate())
            return df.format(new Date(installDate));
        else
            return "";
    }

    public String getEndDate(){
        df = new SimpleDateFormat("dd/MM/yyyy");
        if (hasEndDate())
            return df.format(new Date(endDate));
        else
            return "";
    }
    public String getReturnDate(){
        df = new SimpleDateFormat("dd/MM/yyyy");
        if (hasReturnDate())
            return df.format(new Date(returnDate));
        else
            return "";
    }

    public boolean hasExitDate(){
        return this.exitDate!=0;
    }
    public boolean hasInstallDate(){
        return this.installDate!=0;
    }
    public boolean hasEndDate(){
        return this.endDate!=0;
    }
    public boolean hasReturnDate()
    {
        return this.returnDate!=0;
    }
    public Exhibition getExhibition() {
        return exhibition;
    }

    public String getInvNum() {
        return invNum;
    }

    public String getDet1() {
        return det1;
    }

    public String getDet2() {
        return det2;
    }

    public String getDet3() {
        return det3;
    }

    public String getDet4() {
        return det4;
    }

    public String getDet5() {
        return det5;
    }

    public String getDet6() {
        return det6;
    }

    public String getDet7() {
        return det7;
    }


    public String getGenCondition() {
        return genCondition;
    }

    public String getSpecialCare() {
        return specialCare;
    }

    public String getCrateNumber() {
        return crateNumber;
    }

    public boolean isSupportF() {
        return supportF;
    }

    public boolean isPlastic() {
        return plastic;
    }

    public boolean isPaper() {
        return paper;
    }

    public boolean isNoTape() {
        return noTape;
    }

    public boolean isEthafoam() {
        return ethafoam;
    }

    public boolean isFoamRubber() {
        return foamRubber;
    }

    public String getPdfpath(){
        return pdfpath;
    }

    public void setPdfpath(String pdfpath){
        this.pdfpath= pdfpath;
    }

    public ArrayList<String> getDefectsAsListofStrings(){
        return DBListHelper.stringToList(this.defects);
    }

    public String wrappingToString(){
        String s = "";
        if(paper)
        {
            s+="Paper: YES\n";
        }
        if(plastic){
            s+="Plastic: YES\n";
        }
        if(!noTape){
            s+="Tape: YES";
        }
        return s;
    }

    public String protectionToString(){
        String s  = "";
        if(ethafoam){
            s+="Ethafoam: YES\n";
        }
        if(foamRubber){
            s+="Foam rubber: YES\n";
        }
        return s;
    }
    public String getPosition() {
        return position;
    }

    public List<Detail> getDetailsAsList()
    {

        ArrayList<Detail> dets = new ArrayList<>();
        dets.add(new Detail("General Condition",genCondition));
        dets.add(new Detail("Oggetto",det1));
        dets.add(new Detail("Tecnica",det2));
        dets.add(new Detail("Dimens",det3));
        dets.add(new Detail("Datazione",det4));
        dets.add(new Detail("Collacazione",det5));
        dets.add(new Detail("Stato Conserv",det6));
        dets.add(new Detail("Interventi Fatti",det7));
        dets.add(new Detail("Special Care",specialCare));
        dets.add(new Detail("Crate Number",crateNumber));
        return dets;
    }

}

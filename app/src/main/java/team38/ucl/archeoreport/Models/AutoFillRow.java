package team38.ucl.archeoreport.Models;

import com.orm.SugarRecord;

/**
 * Created by varunmathur on 24/03/16.
 */
public class AutoFillRow extends SugarRecord {
    String nrinv;
    String oggetto;
    String tecnica;
    String dimensione;
    String datazione;
    String collocazione;
    String condizioni;
    String stato_conserv;
    String priorita;
    String necesita_di;
    String interventi;

    public AutoFillRow(){}

    public AutoFillRow(String nr_inv, String oggetto, String tecnica, String dimensione, String datazione, String collocazione, String condizioni, String stato_conserv, String priorita, String necesita_di, String interventi) {
        this.nrinv = nr_inv;
        this.oggetto = oggetto;
        this.tecnica = tecnica;
        this.dimensione = dimensione;
        this.datazione = datazione;
        this.collocazione = collocazione;
        this.condizioni = condizioni;
        this.stato_conserv = stato_conserv;
        this.priorita = priorita;
        this.necesita_di = necesita_di;
        this.interventi = interventi;
    }

    public String getNr_inv() {
        return nrinv;
    }

    public String getOggetto() {
        return oggetto;
    }

    public String getTecnica() {
        return tecnica;
    }

    public String getDimensione() {
        return dimensione;
    }

    public String getDatazione() {
        return datazione;
    }

    public String getCollocazione() {
        return collocazione;
    }

    public String getCondizioni() {
        return condizioni;
    }

    public String getStato_conserv() {
        return stato_conserv;
    }

    public String getPriorita() {
        return priorita;
    }

    public String getNecesita_di() {
        return necesita_di;
    }

    public String getInterventi() {
        return interventi;
    }
}

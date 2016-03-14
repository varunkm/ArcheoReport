package team38.ucl.archeoreport.Models;

/**
 * Created by varunmathur on 14/03/16.
 */
public class Detail {
    String title;
    String detail;

    public String getTitle() {
        return title;
    }

    public String getDetail() {
        return detail;
    }

    public Detail(String title, String detail)
    {
        this.title = title;
        this.detail = detail;
    }

    public Detail(String title, boolean detail)
    {
        this.title = title;
        if(detail)
        {
            this.detail = "Yes";
        }
        else
        {
            this.detail = "No";
        }
    }
}

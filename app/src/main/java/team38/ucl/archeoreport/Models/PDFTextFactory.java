package team38.ucl.archeoreport.Models;

import android.content.Context;
import android.graphics.Color;
import android.util.TypedValue;
import android.widget.TextView;

/**
 * Created by varunmathur on 12/04/16.
 */
public class PDFTextFactory {
    private Context context;
    public PDFTextFactory(Context context)
    {
        this.context = context;
    }

    public TextView makeSubtitle(){
        TextView view = new TextView(context);
        view.setTextSize(TypedValue.COMPLEX_UNIT_DIP,12);
        view.setTextColor(Color.GRAY);
        return view;
    }

    public TextView makeBodyText(){
        TextView view = new TextView(context);
        view.setTextSize(TypedValue.COMPLEX_UNIT_DIP,12);
        view.setTextColor(Color.BLACK);
        return view;
    }
}

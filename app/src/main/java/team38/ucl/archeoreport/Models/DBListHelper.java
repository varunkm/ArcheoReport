package team38.ucl.archeoreport.Models;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by varunmathur on 01/04/16.
 */
public class DBListHelper {
    public static ArrayList<String> stringToList(String detString){
        ArrayList<String> details = new ArrayList<>(Arrays.asList(detString.split(",")));
        return details;
    }

    public static String listToString(ArrayList<String> strings){
        String str = strings.get(0);
        for(int i = 1; i < strings.size(); i++){
            str+=","+strings.get(i);
        }
        return str;

    }
}

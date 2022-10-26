package phdhtl.bomoncntt.sinhvienimage.helper;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.widget.Spinner;

public class StaticMethod {

    public  static Bitmap StringToBitMap(String encodedString){
        try {
            byte [] encodeByte=Base64.decode(encodedString,Base64.DEFAULT);
            return BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public static int idSelected(Spinner spinner, Object value)
    {
        for(int i=0;i< spinner.getCount();i++){
            if(spinner.getItemAtPosition(i).equals(value)) return i;
        }
        return -1;
    }

    public static void delUserInfo(SharedPreferences pref){
        SharedPreferences.Editor editor=pref.edit();
//        editor=pref.edit();
        editor.remove("USERNAME");
        editor.remove("PASSWORD");
        editor.apply();
    }

    public static void saveUser(SharedPreferences pref,String u, String p){
        SharedPreferences.Editor editor=pref.edit();
        editor.putString("USERNAME",u);
        editor.putString("PASSWORD",p);
        editor.apply();
    }

    public static boolean isUserNull (SharedPreferences pref){
//        pref=getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        String username=pref.getString("USERNAME","");
        String password=pref.getString("PASSWORD","");
        if(username.isEmpty()) return false;
        if(password.isEmpty()) return false;
        return true;
    }

}

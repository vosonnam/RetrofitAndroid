package phdhtl.bomoncntt.sinhvienimage.helper;

import java.util.ArrayList;
import java.util.Arrays;

import phdhtl.bomoncntt.sinhvienimage.model.LogHistory;

public class Constant {
    public static final String MyPREFERENCES="MYPREFS";
    //init spinner lop
    public static final ArrayList<String> listLop=new ArrayList(Arrays.asList("Khóa 59", "Khóa 60", "Khóa 61", "Khóa 62"));
    //user log history
    public static LogHistory userLog=null;

    //request code permission
    public static final int GALLERY_REQUEST_CODE=100;
    public static final int CAMERA_REQUEST_CODE=101;

    //URL
    public static final String URL="http://192.168.1.48";
    public static final int TIMEOUT=30;

    //Query Path
    public static final String QUERYPATH="/sinhvien/index.php";

    //Object User
    public static final String USERNAME="username";
    public static final String PASSWORD="password";

    //Object Student
    public static final String MASV="masv";
    public static final String TENSV="tensv";
    public static final String GT="gt";
    public static final String LOP="lop";
    public static final String ANHSV="photo";

    //Object Log History
    public static final String LOGIN="login";
    public static final String LOGOUT="logout";
    public static final String STATUS="status";

    //Object list data
    public static final String PAGE="page";
    public static final String RECORD="records";

    //event
    public static final String EVENT="event";
    public static final String TYPEMETHOD="multipart/form-data";

    //Key API
    public static final String KEYLOGIN="login";
    public static final String KEYSIGNUP="register";
    public static final String KEYADDSTUDENT="addstudent";
    public static final String KEYEDITSTUDENT="editstudent";
    public static final String KEYDELSTUDENT="delstudent";
    public static final String KEYGETSTUDENT="getstudent";
    public static final String KEYADDLOG="addlog";
    public static final String KEYGETLOG="getlog";

}

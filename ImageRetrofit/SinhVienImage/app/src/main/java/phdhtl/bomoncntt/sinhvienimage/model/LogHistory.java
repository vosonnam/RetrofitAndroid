package phdhtl.bomoncntt.sinhvienimage.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class LogHistory {

    private  String user;
    private String logInDate;
    private String logOutDate;
    private String Status;

    public LogHistory() {
    }

    public LogHistory(String user, String logInDate, String logOutDate, String status) {
        this.user = user;
        this.logInDate = logInDate;
        this.logOutDate = logOutDate;
        Status = status;
    }

    public LogHistory(String user, boolean status) {
        this.user = user;
        this.setDatelogin();
        this.setStatus(status);
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getLogInDate() {
        return logInDate;
    }

    public void setDatelogin() {
        DateFormat df = new SimpleDateFormat("dd MM yyyy HH:mm a");
        String date = df.format(Calendar.getInstance().getTime());
        this.logInDate = date;
    }

    public String getLogOutDate() {
        return logOutDate;
    }

    public void setLogOutDate() {
        DateFormat df = new SimpleDateFormat("dd MM yyyy HH:mm a");
        String date = df.format(Calendar.getInstance().getTime());
        this.logOutDate = date;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(boolean status) {
        if(status){
            this.Status = "Login Success";
        }else{
            this.Status = "Login Failed";
        }
    }

    @Override
    public String toString() {
        return "" + user + ',' +
                logInDate + ',' +
                logOutDate + ',' +
                Status + ',' +
                ";\n";
    }
    
    public static ArrayList<LogHistory> toLogHistory(String str){
        ArrayList<LogHistory> rs=new ArrayList<>();
        String listLog[]=str.split(";\n");
        for (String i:listLog) {
            String item[]=i.split(",");
            LogHistory itemLog=new LogHistory(item[0],item[1],item[2],item[3]);
            rs.add(itemLog);
        }
        return rs;
    }
}

package phdhtl.bomoncntt.sinhvienimage.helper;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import phdhtl.bomoncntt.sinhvienimage.API.model.GetListLog;
import phdhtl.bomoncntt.sinhvienimage.R;


public class LogHistoryAdapter extends ArrayAdapter<GetListLog.Item> {

    private static class ViewHolder {
        TextView txtId;
        TextView txtUser;
        TextView txtLogIn;
        TextView txtLogOut;
        TextView txtStatus;
    }
    public LogHistoryAdapter(Activity context, ArrayList<GetListLog.Item> data) {
        super(context, R.layout.item_loghistory, data);
        // TODO Auto-generated constructor stub

    }

    @Override
    public View getView(int position,View convertView,ViewGroup parent) {
        GetListLog.Item dataModel = getItem(position);
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_loghistory, parent, false);
            viewHolder.txtId =  convertView.findViewById(R.id.item_txtid);
            viewHolder.txtUser =  convertView.findViewById(R.id.item_txtuser);
            viewHolder.txtLogIn =  convertView.findViewById(R.id.item_txtlogin);
            viewHolder.txtLogOut=convertView.findViewById(R.id.item_txtlogout);
            viewHolder.txtStatus=convertView.findViewById(R.id.item_txtstatus);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.txtId.setText(""+position);
        viewHolder.txtUser.setText(dataModel.getUsername());
        viewHolder.txtLogIn.setText(dataModel.getLogin());
        viewHolder.txtLogOut.setText(dataModel.getLogout());
        viewHolder.txtStatus.setText(dataModel.getStatus());
        return convertView;

    }
}
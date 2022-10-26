package phdhtl.bomoncntt.sinhvienimage.helper;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import phdhtl.bomoncntt.sinhvienimage.API.model.GetListStudent;
import phdhtl.bomoncntt.sinhvienimage.R;

public class MyListAdapter extends ArrayAdapter<GetListStudent.Item> {

    //Lớp tỉnh
    //static
    private static class ViewHolder {
        TextView txtMasv;
        TextView txtTensv;
        TextView txtgt;
        TextView txtlop;
        CircleImageView imageSV;
    }
    //ViewHolder.txtMasv="001"
    public MyListAdapter(Activity context, ArrayList<GetListStudent.Item> data) {
        super(context, R.layout.item_listsv, data);
        // TODO Auto-generated constructor stub

        //listsv chứa 3 object

    }

    @Override
    public View getView(int position,View convertView,ViewGroup parent) {
        GetListStudent.Item dataModel = getItem(position); //lấy object (masv,tensv,gt,lop)
        ViewHolder viewHolder; //cục bộ
        //convertView biến quản lý cho biết các view đã xuất hiện hoặc tồn tại chưa
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());

            //khác null
            convertView = inflater.inflate(R.layout.item_listsv, parent, false);
            //Ánh xạ và gán view vào biến của thộc tính
            viewHolder.txtMasv =  convertView.findViewById(R.id.item_txtmasv);
            viewHolder.txtTensv =  convertView.findViewById(R.id.item_txttensv);
            viewHolder.txtgt=convertView.findViewById(R.id.item_txtgt);
            viewHolder.txtlop=convertView.findViewById(R.id.item_txtlop);
            viewHolder.imageSV=convertView.findViewById(R.id.imageViewsv);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        //viewHolder.txtMasv,... khác null đều có nghĩa là nó đã cấp
        viewHolder.txtMasv.setText(dataModel.masv);
        viewHolder.txtTensv.setText(dataModel.tensv);
        viewHolder.txtgt.setText(dataModel.gt);
        viewHolder.txtlop.setText(dataModel.lop);
        Glide.with(this.getContext()).load(dataModel.anhsv).into(viewHolder.imageSV);
//        viewHolder.imageSV.setImageBitmap(StringToBitMap(dataModel.anhsv));
        return convertView;

    }


}
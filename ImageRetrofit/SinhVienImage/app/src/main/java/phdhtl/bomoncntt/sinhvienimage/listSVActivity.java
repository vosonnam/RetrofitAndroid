package phdhtl.bomoncntt.sinhvienimage;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import phdhtl.bomoncntt.sinhvienimage.API.APIClient;
import phdhtl.bomoncntt.sinhvienimage.API.APIInterface;
import phdhtl.bomoncntt.sinhvienimage.API.model.GetListStudent;
import phdhtl.bomoncntt.sinhvienimage.API.model.GetQuery;
import phdhtl.bomoncntt.sinhvienimage.helper.Constant;
import phdhtl.bomoncntt.sinhvienimage.helper.MyListAdapter;
import phdhtl.bomoncntt.sinhvienimage.helper.OnSwipeTouchListener;
import phdhtl.bomoncntt.sinhvienimage.helper.StaticMethod;
import phdhtl.bomoncntt.sinhvienimage.model.SinhVien;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class listSVActivity extends AppCompatActivity {

    private ArrayList<SinhVien> checkedItemList=null;
    private ArrayList<GetListStudent.Item> arrayListStudent=null;
    private MyListAdapter adapter=null;
    private ListView lvsv = null;
    private SharedPreferences pref;
    private Integer currentPage=0;
    private Integer totalPage;
    APIInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_svactivity);

        lvsv=findViewById(R.id.listview_lh);

        pref=getSharedPreferences(Constant.MyPREFERENCES, Context.MODE_PRIVATE);
        apiInterface = APIClient.getClient().create(APIInterface.class);
        checkedItemList=new ArrayList<>();
        currentPage=0;

        showListStudent(currentPage,5, this);

        setTitle(R.string.txt_lthongtin);

        lvsv.setOnItemClickListener((parent, view, position, id) -> {
            CheckBox itemCheckBox= view.findViewById(R.id.sinhvien_list_item_checkbox);
            boolean checkboxChecked=itemCheckBox.isChecked();
            itemCheckBox.setChecked(! checkboxChecked);
            SinhVien sv=new SinhVien();
            sv.setMasv(arrayListStudent.get(position).masv);
            sv.setTensv(arrayListStudent.get(position).tensv);
            sv.setLop(arrayListStudent.get(position).lop);
            sv.setGt(arrayListStudent.get(position).gt);
            sv.setAnhsv(arrayListStudent.get(position).anhsv);
            addCheckListItem(sv, ! checkboxChecked);
        });
        lvsv.setOnTouchListener(new OnSwipeTouchListener(getApplicationContext()) {
            //            public void onSwipeTop() {
//                Toast.makeText(getApplicationContext(), "top", Toast.LENGTH_SHORT).show();
//            }
            public void onSwipeRight() {
                currentPage=previousPage(currentPage, totalPage);
                Toast.makeText(getApplicationContext(), "back:"+currentPage, Toast.LENGTH_SHORT).show();
                showListStudent(currentPage,5, listSVActivity.this);
            }
            public void onSwipeLeft() {
                currentPage=nextPage(currentPage, totalPage);
                Toast.makeText(getApplicationContext(), "next:"+currentPage, Toast.LENGTH_SHORT).show();
                showListStudent(currentPage,5, listSVActivity.this);
            }
//            public void onSwipeBottom() {
//                Toast.makeText(getApplicationContext(), "bottom", Toast.LENGTH_SHORT).show();
//            }

        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Call<GetQuery> getQueryCall=apiInterface.queryLogUser(Constant.KEYADDLOG
                , Constant.userLog.getUser(), Constant.userLog.getLogInDate()
                , Constant.userLog.getLogOutDate()
                , Constant.userLog.getStatus());
        getQueryCall.enqueue(new Callback<GetQuery>() {
            @Override
            public void onResponse(Call<GetQuery> call, Response<GetQuery> response) {
            }

            @Override
            public void onFailure(Call<GetQuery> call, Throwable t) {
                call.cancel();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menulistsv,menu);
        return super.onCreateOptionsMenu(menu);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int itemId=item.getItemId();
        if(itemId==R.id.menu_add){
            Intent in=new Intent(getApplicationContext(),inforActivity.class);
//            Intent in=new Intent(getApplicationContext(),inforActivity.class);
            in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            in.putExtra("Flag","add");//sửa
            in.putExtra("MASV","");
            in.putExtra("TENSV","");
            in.putExtra("GT","");
            in.putExtra("LOP","");
            in.putExtra("ANHSV","");
            startActivity(in);
        }else if(itemId==R.id.menu_edit){
            if(!checkedItemList.isEmpty()) {
                if(checkedItemList.size()>1){
                    Toast.makeText(getApplicationContext(), "Chỉ chọn 1 phần tử để sửa", Toast.LENGTH_SHORT).show();
                }else {
                    Intent in = new Intent(getApplicationContext(), inforActivity.class);
//                    Intent in = new Intent(getApplicationContext(), inforActivity.class);
                    in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    in.putExtra("Flag", "edit");//sửa
                    in.putExtra("MASV", checkedItemList.get(0).getMasv());
                    in.putExtra("TENSV", checkedItemList.get(0).getTensv());
                    in.putExtra("GT", checkedItemList.get(0).getGt());
                    in.putExtra("LOP", checkedItemList.get(0).getLop());
                    in.putExtra("ANHSV", checkedItemList.get(0).getAnhsv());
                    startActivity(in);
                }
            }
        }else if(itemId==R.id.menu_remove){
            if(checkedItemList!=null){
                if(checkedItemList.isEmpty()){
                    Toast.makeText(getApplicationContext(), "chọn ít nhất 1 phần tử để xóa", Toast.LENGTH_SHORT).show();
                }else {
                    for(int i=0; i<checkedItemList.size(); i++){
                        SinhVien sv=checkedItemList.get(i);
                        Call<GetQuery> getQueryCall=apiInterface.querydelStudent(Constant.KEYDELSTUDENT, ""+sv.getMasv());
                        getQueryCall.enqueue(new Callback<GetQuery>() {
                            @Override
                            public void onResponse(Call<GetQuery> call, Response<GetQuery> response) {
                                GetQuery resource=response.body();
                                if(resource.getSuccess()==0){
                                    Toast.makeText(getApplicationContext(), ""+resource.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<GetQuery> call, Throwable t) {

                            }
                        });
                    }
                    checkedItemList.clear();
                    showListStudent(0,5, this);
                }
            }
        }else if(itemId==R.id.menu_userlog){
            Intent in=new Intent(getApplicationContext(),logHistoryActivity.class);
            startActivity(in);
        }else if(itemId==R.id.menu_logout){
            StaticMethod.delUserInfo(pref);
            Constant.userLog.setLogOutDate();
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void showListStudent(Integer page, Integer records, Activity context){
        Call<GetListStudent> getListStudentCall=apiInterface.queryListStudent(Constant.KEYGETSTUDENT, page, records);
        getListStudentCall.enqueue(new Callback<GetListStudent>() {
            @Override
            public void onResponse(Call<GetListStudent> call, Response<GetListStudent> response) {
                GetListStudent resource=response.body();
                arrayListStudent=new ArrayList<>(resource.getItems());
                totalPage=(int)resource.getTotal()/records;
                adapter = new MyListAdapter(context, arrayListStudent);
                lvsv.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<GetListStudent> call, Throwable t) {
                call.cancel();
            }
        });
    }

    private void addCheckListItem(SinhVien student,boolean checked){
        if(checkedItemList!=null){
            int existPosition = getExistPosition(student);
            if(checked){
                if(existPosition==-1){

                    checkedItemList.add(student);
                }
            }else {
                if(existPosition>-1){
                    checkedItemList.remove(existPosition);
                }
            }
        }

    }

    private int getExistPosition(SinhVien student) {
        int size=checkedItemList.size();
        for(int i=0;i<size;i++){
            String  idSV=checkedItemList.get(i).getMasv();
            if(idSV.equals(student.getMasv())) return i;
        }
        return -1;
    }

    private int nextPage(int currentPage, int totalPage){
        int nextPage=currentPage+1;
        if(nextPage>totalPage) return 0;
        else return nextPage;
    }
    private int previousPage(int currentPage, int totalPage){
        int previousPage=currentPage-1;
        int finalPage=totalPage-1;
        if(previousPage<0) return finalPage;
        else return previousPage;
    }
}
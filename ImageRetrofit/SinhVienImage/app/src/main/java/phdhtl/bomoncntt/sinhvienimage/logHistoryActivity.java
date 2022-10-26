package phdhtl.bomoncntt.sinhvienimage;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Objects;

import phdhtl.bomoncntt.sinhvienimage.API.APIClient;
import phdhtl.bomoncntt.sinhvienimage.API.APIInterface;
import phdhtl.bomoncntt.sinhvienimage.API.model.GetListLog;
import phdhtl.bomoncntt.sinhvienimage.helper.Constant;
import phdhtl.bomoncntt.sinhvienimage.helper.LogHistoryAdapter;
import phdhtl.bomoncntt.sinhvienimage.helper.OnSwipeTouchListener;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class logHistoryActivity extends AppCompatActivity {

    ListView listLog;
    ArrayList<GetListLog.Item> arraylistLog;
    private LogHistoryAdapter adapter=null;
    private Integer currentPage=0;
    private Integer totalPage;
    APIInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_history);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        listLog=findViewById(R.id.listview_lh);
        apiInterface = APIClient.getClient().create(APIInterface.class);

        setTitle(R.string.txt_ldangnhap);
        showListLog(currentPage,5);

        listLog.setOnTouchListener(new OnSwipeTouchListener(getApplicationContext()) {
            public void onSwipeRight() {
                currentPage=previousPage(currentPage, totalPage);
                Toast.makeText(getApplicationContext(), "back:"+currentPage, Toast.LENGTH_SHORT).show();
                showListLog(currentPage,5);
            }
            public void onSwipeLeft() {
                currentPage=nextPage(currentPage, totalPage);
                Toast.makeText(getApplicationContext(), "next:"+currentPage, Toast.LENGTH_SHORT).show();
                showListLog(currentPage,5);
            }
        });
    }

    public boolean onOptionsItemSelected(MenuItem item){
        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }
        return  super.onOptionsItemSelected(item);
    }

    private void showListLog(Integer page, Integer records) {
        Call<GetListLog> getListLogCall=apiInterface.queryListLog(Constant.KEYGETLOG, page, records);
        getListLogCall.enqueue(new Callback<GetListLog>() {
            @Override
            public void onResponse(Call<GetListLog> call, Response<GetListLog> response) {
                GetListLog resource=response.body();
                arraylistLog=new ArrayList<>(resource.getItems());
                totalPage=(int)resource.getTotal()/records;
                adapter = new LogHistoryAdapter(logHistoryActivity.this, arraylistLog);
                listLog.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<GetListLog> call, Throwable t) {
                call.cancel();
            }
        });
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
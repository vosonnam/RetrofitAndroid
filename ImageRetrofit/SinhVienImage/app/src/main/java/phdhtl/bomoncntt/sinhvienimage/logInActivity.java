package phdhtl.bomoncntt.sinhvienimage;

import static phdhtl.bomoncntt.sinhvienimage.helper.StaticMethod.isUserNull;
import static phdhtl.bomoncntt.sinhvienimage.helper.StaticMethod.saveUser;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import phdhtl.bomoncntt.sinhvienimage.API.APIClient;
import phdhtl.bomoncntt.sinhvienimage.API.APIInterface;
import phdhtl.bomoncntt.sinhvienimage.API.model.GetQuery;
import phdhtl.bomoncntt.sinhvienimage.helper.Constant;
import phdhtl.bomoncntt.sinhvienimage.model.LogHistory;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class logInActivity extends AppCompatActivity {

    Button btnSignIn, btnRegister;
    EditText txtUser, txtPass;
    CheckBox remberuser;

    private SharedPreferences pref;
    APIInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setTitle(R.string.txt_ldangnhap);

        btnSignIn=findViewById(R.id.btn_signin);
        btnRegister=findViewById(R.id.btn_register);
        txtUser=findViewById(R.id.txt_username);
        txtPass=findViewById(R.id.txt_pass);
        remberuser=findViewById(R.id.checkBox);

        pref=getSharedPreferences(Constant.MyPREFERENCES, Context.MODE_PRIVATE);
        apiInterface = APIClient.getClient().create(APIInterface.class);

        if(isUserNull(pref)){
            String user=pref.getString("USERNAME","");
            String pass=pref.getString("PASSWORD","");
            Call<GetQuery> getQueryCall =apiInterface.queryUser(Constant.KEYLOGIN, ""+user, ""+pass);
            getQueryCall.enqueue(new Callback<GetQuery>() {
                @Override
                public void onResponse(Call<GetQuery> call, Response<GetQuery> response) {
                    GetQuery resource=response.body();
                    if(resource.getSuccess()==1){
                        Constant.userLog=new LogHistory(user,true);
                        finish();
                        Intent in=new Intent(getApplicationContext(),listSVActivity.class);
                        startActivity(in);
                    }
                }

                @Override
                public void onFailure(Call<GetQuery> call, Throwable t) {
                    call.cancel();
                }
            });
        }

        btnSignIn.setOnClickListener(v -> {
            boolean isVailed=isVailed();
            String user=txtUser.getText().toString();
            String pass=txtPass.getText().toString();
            if(isVailed){
                Call<GetQuery> getQueryCall=apiInterface.queryUser(Constant.KEYLOGIN, ""+user, ""+pass);
                getQueryCall.enqueue(new Callback<GetQuery>() {
                    @Override
                    public void onResponse(Call<GetQuery> call, Response<GetQuery> response) {
                        GetQuery resource=response.body();
                        if(resource.getSuccess()==1){
                            if(remberuser.isChecked()){
                                saveUser(pref,user,pass);
                            }
                            Constant.userLog=new LogHistory(user,true);
                            finish();
                            Intent in=new Intent(getApplicationContext(),listSVActivity.class);
                            startActivity(in);
                        }else {
                            Constant.userLog=new LogHistory(user,false);
                            Toast.makeText(getApplicationContext(), ""+resource.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<GetQuery> call, Throwable t) {
                        call.cancel();
                    }
                });
            }
        });

        btnRegister.setOnClickListener(v -> {
            Intent in=new Intent(getApplicationContext(),signUpActivity.class);
            startActivity(in);
        });
    }

    private boolean isVailed(){
        String user=txtUser.getText().toString();
        String pass=txtPass.getText().toString();
        if(user.isEmpty()) return false;
        if(pass.isEmpty()) return false;
        return true;
    }
}
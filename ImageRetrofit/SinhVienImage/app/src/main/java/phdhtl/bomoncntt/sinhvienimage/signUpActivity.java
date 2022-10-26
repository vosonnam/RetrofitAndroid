package phdhtl.bomoncntt.sinhvienimage;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Objects;

import phdhtl.bomoncntt.sinhvienimage.API.APIClient;
import phdhtl.bomoncntt.sinhvienimage.API.APIInterface;
import phdhtl.bomoncntt.sinhvienimage.API.model.GetQuery;
import phdhtl.bomoncntt.sinhvienimage.helper.Constant;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class signUpActivity extends AppCompatActivity {

    EditText txtUser, txtPass, txtConfirm;
    Button btnSignUp;

    APIInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        setTitle(R.string.txt_ttdangky);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        txtUser=findViewById(R.id.txt_newuser);
        txtPass=findViewById(R.id.txt_newpass);
        txtConfirm=findViewById(R.id.txt_confirmpass);
        btnSignUp=findViewById(R.id.btn_signup);

        apiInterface = APIClient.getClient().create(APIInterface.class);

        btnSignUp.setOnClickListener(v -> {
            boolean isRegister=isvailed();
            if(isRegister){
                String newuser=txtUser.getText().toString();
                String newpass=txtPass.getText().toString();
                Call<GetQuery> getQueryCall= apiInterface.queryUser(Constant.KEYSIGNUP, ""+newuser, ""+newpass);
                getQueryCall.enqueue(new Callback<GetQuery>() {
                    @Override
                    public void onResponse(Call<GetQuery> call, Response<GetQuery> response) {
                        GetQuery resource=response.body();
                        if(resource.getSuccess()==1){
                            showSuccessDialog();
                        }else{
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
    }

    private void showSuccessDialog(){
        AlertDialog.Builder builder= new AlertDialog.Builder(this);
        builder.setTitle(R.string.txt_ttmsg);
        builder.setMessage(R.string.txt_txtmsg);
        builder.setPositiveButton("OK", (dialog, which) -> {
            dialog.dismiss();
            finish();
        });
        AlertDialog alert= builder.create();
        alert.show();
    }

    private boolean isvailed(){
        String newuser=txtUser.getText().toString();
        String newpass=txtPass.getText().toString();
        String confirmpass=txtConfirm.getText().toString();
        if(newuser.isEmpty()) return false;
        if(newpass.isEmpty()) return false;
        if(confirmpass.isEmpty()) return false;
        if(!confirmpass.equals(newpass))return  false;
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }
        return  super.onOptionsItemSelected(item);
    }

}
package phdhtl.bomoncntt.sinhvienimage;

import static phdhtl.bomoncntt.sinhvienimage.helper.StaticMethod.StringToBitMap;
import static phdhtl.bomoncntt.sinhvienimage.helper.StaticMethod.idSelected;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.bumptech.glide.Glide;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import phdhtl.bomoncntt.sinhvienimage.API.APIClient;
import phdhtl.bomoncntt.sinhvienimage.API.APIInterface;
import phdhtl.bomoncntt.sinhvienimage.API.model.GetQuery;
import phdhtl.bomoncntt.sinhvienimage.helper.Constant;
import phdhtl.bomoncntt.sinhvienimage.helper.RealPathUtil;
import phdhtl.bomoncntt.sinhvienimage.model.SinhVien;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class inforActivity extends AppCompatActivity {

    private static final String TAG = "SVImage";
    EditText txtmsv, txttensv;
    RadioGroup gsex;
    RadioButton rsex;
    Spinner snLop;
    Button btnLuu, btnLamLai;
    CircleImageView croppedImageView;

    APIInterface apiInterface;
    SinhVien sv=null;
    Bitmap myBitmap;
    Uri picUri;


    ActivityResultLauncher<Intent> mActivityResurltLaucher=registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode()== Activity.RESULT_OK){
                        Intent data=result.getData();
                        if(data==null){
                            return;
                        }
                        picUri=data.getData();
//                        String strRealPath= RealPathUtil.getRealPath(inforActivity.this, picUri);
//                        File file=new File(strRealPath);
//                        Log.d(TAG, file.getName());
//                        String strRealPath= RealPathUtil.getRealPath(inforActivity.this, picUri);
//                        Log.d(TAG, ""+strRealPath);
                        try {
                            myBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), picUri);
                            croppedImageView.setImageBitmap(myBitmap);
                        }catch (Exception e){
                            e.printStackTrace();
                        }

                    }
                }
            }
    );

    ActivityResultLauncher<Intent> cameraActivityResurltLaucher=registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode()== Activity.RESULT_OK){
                        Bundle extras = result.getData().getExtras();
                        if(extras==null){
                            return;
                        }
                        myBitmap = (Bitmap) extras.get("data");
                        croppedImageView.setImageBitmap(myBitmap);
                    }
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infor);

        initView();
        editView();

        Intent in=getIntent();
        boolean flag= "add".equals(in.getStringExtra("Flag"));

        if(flag){
            setTitle(R.string.txt_ttthem);
            String masv = in.getStringExtra("MASV");
            String tensv = in.getStringExtra("TENSV");
            String gt = in.getStringExtra("GT");
            String lop = in.getStringExtra("LOP");
            String anhsv = in.getStringExtra("ANHSV");
            sv=new SinhVien(masv, tensv, gt, lop, anhsv);
        }else {
            setTitle(R.string.txt_ttsua);
            String masv = in.getStringExtra("MASV");
            String tensv = in.getStringExtra("TENSV");
            String gt = in.getStringExtra("GT");
            String lops = in.getStringExtra("LOP");
            String anhsv = in.getStringExtra("ANHSV");

            sv=new SinhVien(masv, tensv, gt, lops, anhsv);
            txtmsv.setText(sv.getMasv());
            txttensv.setText(sv.getTensv());
            if(sv.getGt().equalsIgnoreCase("Nam")){
                rsex= findViewById(R.id.btnradio_nam2);
            }else{
                rsex= findViewById(R.id.btnradio_nu2);
            }
            gsex.check(rsex.getId());
            int idlop=idSelected(snLop,sv.getLop());
            if(idlop>-1){
                snLop.setSelection(idlop);
            }
            if(!sv.getAnhsv().isEmpty()){
                Glide.with(inforActivity.this).load(sv.getAnhsv()).into(croppedImageView);
            }
        }

        croppedImageView.setOnClickListener(v -> showD());

        btnLuu.setOnClickListener(v -> {
            try {
                String event;
                String masv="";
                String tensv=txttensv.getText().toString().trim();
                rsex=findViewById(gsex.getCheckedRadioButtonId());
                String gt=rsex.getText().toString().trim();
                String lop=snLop.getSelectedItem().toString().trim();
                if(flag){
                    event=Constant.KEYADDSTUDENT;
                }else {
                    event=Constant.KEYEDITSTUDENT;
                    masv=txtmsv.getText().toString().trim();
                }
                queryImage(event,masv, tensv, gt, lop, getCompressBytes(picUri, myBitmap));

            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        btnLamLai.setOnClickListener(v -> {
            txtmsv.setText(sv.getMasv());
            txttensv.setText(sv.getTensv());
            if(sv.getGt().equalsIgnoreCase("")){
                rsex= findViewById(R.id.btnradio_nam2);
            }else if(sv.getGt().equalsIgnoreCase("Nam")){
                rsex= findViewById(R.id.btnradio_nam2);
            }else{
                rsex= findViewById(R.id.btnradio_nu2);
            }
            gsex.check(rsex.getId());
            int idlop=idSelected(snLop,sv.getLop());
            if(idlop>-1){
                snLop.setSelection(idlop);
            }else {
                snLop.setSelection(0);
            }
            if(sv.getAnhsv().isEmpty()){
                croppedImageView.setImageResource(R.drawable.noavatar);
            }else{
                croppedImageView.setImageBitmap(StringToBitMap(sv.getAnhsv()));
            }
        });
    }

    private void onClickRequestPermission(String permission) {

        if(permission.equals(Manifest.permission.READ_EXTERNAL_STORAGE)){
            if(Build.VERSION.SDK_INT<Build.VERSION_CODES.M){
                openGallery();
                return;
            }
            if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED){
                openGallery();
            }else{
                ActivityCompat.requestPermissions(inforActivity.this, new String[] { permission }, Constant.GALLERY_REQUEST_CODE);
            }
        }else if(permission.equals(Manifest.permission.CAMERA)){
            if(Build.VERSION.SDK_INT<Build.VERSION_CODES.M){
                openCamera();
                return;
            }
            if(checkSelfPermission(Manifest.permission.CAMERA)== PackageManager.PERMISSION_GRANTED){
                openCamera();
            }else{
                ActivityCompat.requestPermissions(inforActivity.this, new String[] { permission }, Constant.CAMERA_REQUEST_CODE);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==Constant.GALLERY_REQUEST_CODE){
            if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                openGallery();
            }
        }else if(requestCode==Constant.CAMERA_REQUEST_CODE){
            if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                openCamera();
            }
        }
    }

    private void openGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        mActivityResurltLaucher.launch(Intent.createChooser(galleryIntent,"Select Picture"));
    }

    private void openCamera() {
        Intent galleryIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraActivityResurltLaucher.launch(Intent.createChooser(galleryIntent,"take Picture"));
    }

    private void editView() {
        Intent in=getIntent();
        boolean flag= "add".equals(in.getStringExtra("Flag"));

        if(flag){
            setTitle(R.string.txt_ttthem);
            String masv = in.getStringExtra("MASV");
            String tensv = in.getStringExtra("TENSV");
            String gt = in.getStringExtra("GT");
            String lop = in.getStringExtra("LOP");
            String anhsv = in.getStringExtra("ANHSV");
            txtmsv.setText(masv);
            sv=new SinhVien(masv, tensv, gt, lop, anhsv);
        }else {
            setTitle(R.string.txt_ttsua);
            String masv = in.getStringExtra("MASV");
            String tensv = in.getStringExtra("TENSV");
            String gt = in.getStringExtra("GT");
            String lops = in.getStringExtra("LOP");
            String anhsv = in.getStringExtra("ANHSV");

            sv=new SinhVien(masv, tensv, gt, lops, anhsv);
            txtmsv.setText(sv.getMasv());
            txttensv.setText(sv.getTensv());
            if(sv.getGt().equalsIgnoreCase("Nam")){
                rsex= findViewById(R.id.btnradio_nam2);
            }else{
                rsex= findViewById(R.id.btnradio_nu2);
            }
            gsex.check(rsex.getId());
            int idlop=idSelected(snLop,sv.getLop());
            if(idlop>-1){
                snLop.setSelection(idlop);
            }
            myBitmap=StringToBitMap(anhsv);
            if(myBitmap!=null) {
                croppedImageView.setImageBitmap(myBitmap);
            }
        }
    }

    private void initView() {
        txtmsv=findViewById(R.id.txt_masv2);
        txttensv=findViewById(R.id.txt_tensv2);
        gsex=findViewById(R.id.gradio_sex2);
        btnLuu=findViewById(R.id.btn_luu2);
        btnLamLai=findViewById(R.id.btn_lamlai2);
        croppedImageView=findViewById(R.id.image_sv2);
        snLop=findViewById(R.id.spinner_lop2);

        ArrayAdapter<String> adapterLop= new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, Constant.listLop);
        snLop.setAdapter(adapterLop);
        txtmsv.setEnabled(false);

        apiInterface = APIClient.getClient().create(APIInterface.class);
    }

    private void queryImage(String event, String masv, String tensv, String gt, String lop, byte[] imageBytes) {
        String fileName;
        if(picUri!=null){
            String strRealPath= RealPathUtil.getRealPath(inforActivity.this, picUri);
            File file=new File(strRealPath);
            fileName=file.getName();
        }else{
            fileName="meomeo.jpg";
        }
        Log.d(TAG, ""+imageBytes.length);
//        RequestBody requestBodyPhoto=RequestBody.create(MediaType.parse("application/octet-stream"), imageBytes);
        RequestBody requestBodyEvent=RequestBody.create(MediaType.parse(Constant.TYPEMETHOD), event);
        RequestBody requestBodyMaSV=RequestBody.create(MediaType.parse(Constant.TYPEMETHOD), masv);
        RequestBody requestBodyTenSV=RequestBody.create(MediaType.parse(Constant.TYPEMETHOD), tensv);
        RequestBody requestBodyGT=RequestBody.create(MediaType.parse(Constant.TYPEMETHOD), gt);
        RequestBody requestBodyLop=RequestBody.create(MediaType.parse(Constant.TYPEMETHOD), lop);
        RequestBody requestBodyPhoto=RequestBody.create(MediaType.parse(Constant.TYPEMETHOD), imageBytes);
        MultipartBody.Part multipartBodyPhoto= MultipartBody.Part.createFormData(Constant.ANHSV, fileName, requestBodyPhoto);
        Call<GetQuery> getQueryCall=apiInterface.queryStudent(multipartBodyPhoto, requestBodyEvent, requestBodyMaSV, requestBodyTenSV, requestBodyGT, requestBodyLop);
        getQueryCall.enqueue(new Callback<GetQuery>() {
            @Override
            public void onResponse(Call<GetQuery> call, Response<GetQuery> response) {
                GetQuery resource=response.body();
                if(resource.getSuccess()==1){
                    Toast.makeText(getApplicationContext(), resource.getMessage(), Toast.LENGTH_SHORT).show();
                    finish();
                    Intent st = new Intent(getApplicationContext(), listSVActivity.class);
                    st.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(st);
                }else{
                    Toast.makeText(getApplicationContext(), resource.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetQuery> call, Throwable t) {
                call.cancel();
            }
        });
    }

    public byte[] getCompressBytes(Uri IUri, Bitmap IBitmap) throws IOException {
        ByteArrayOutputStream byteBuff = new ByteArrayOutputStream();
        if(IUri != null){
            IBitmap=MediaStore.Images.Media.getBitmap(getContentResolver(), IUri);
            IBitmap=getResizedBitmap(IBitmap,500);
            IBitmap.compress(Bitmap.CompressFormat.JPEG, 50, byteBuff);
        }else{
            if(IBitmap!=null){
                IBitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteBuff);
            }
        }
        return byteBuff.toByteArray();
    }

    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();
        float bitmapRatio = (float) width / (float) height;
        if (bitmapRatio > 0) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }

    private void showD(){
        AlertDialog.Builder builder= new AlertDialog.Builder(this);
        builder.setTitle(R.string.txt_ttimage);
        builder.setMessage(R.string.txt_msgimage);
        builder.setPositiveButton("CAMERA",(Dialog,which)-> onClickRequestPermission(Manifest.permission.CAMERA));
        builder.setNegativeButton("FILE", (dialog, which) -> onClickRequestPermission(Manifest.permission.READ_EXTERNAL_STORAGE));
        AlertDialog alert= builder.create();
        alert.show();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId=item.getItemId();
        if(itemId==R.id.menu_camera){
            showD();
        }
        return super.onOptionsItemSelected(item);
    }
}
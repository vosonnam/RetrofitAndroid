package phdhtl.bomoncntt.sinhvienimage.API;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import phdhtl.bomoncntt.sinhvienimage.API.model.GetListLog;
import phdhtl.bomoncntt.sinhvienimage.API.model.GetListStudent;
import phdhtl.bomoncntt.sinhvienimage.API.model.GetQuery;
import phdhtl.bomoncntt.sinhvienimage.helper.Constant;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface APIInterface {

    @FormUrlEncoded
    @POST(Constant.QUERYPATH)
    Call<GetQuery> queryUser(@Field(Constant.EVENT) String event,
                             @Field(Constant.USERNAME) String username,
                             @Field(Constant.PASSWORD) String password);
    @FormUrlEncoded
    @POST(Constant.QUERYPATH)
    Call<GetQuery> queryLogUser(@Field(Constant.EVENT) String event,
                                @Field(Constant.USERNAME) String username,
                                @Field(Constant.LOGIN) String login,
                                @Field(Constant.LOGOUT) String logout,
                                @Field(Constant.STATUS) String status);
    @FormUrlEncoded
    @POST(Constant.QUERYPATH)
    Call<GetQuery> querydelStudent(@Field(Constant.EVENT) String event,
                                   @Field(Constant.MASV) String masv);
    @FormUrlEncoded
    @POST(Constant.QUERYPATH)
    Call<GetListStudent> queryListStudent(@Field(Constant.EVENT) String event,
                                          @Field(Constant.PAGE) Integer page,
                                          @Field(Constant.RECORD) Integer records);
    @FormUrlEncoded
    @POST(Constant.QUERYPATH)
    Call<GetListLog> queryListLog(@Field(Constant.EVENT) String event,
                                  @Field(Constant.PAGE) Integer page,
                                  @Field(Constant.RECORD) Integer records);

    @Multipart
    @POST(Constant.QUERYPATH)
    Call<GetQuery> queryStudent(
            @Part MultipartBody.Part photo,
            @Part(Constant.EVENT) RequestBody event,
            @Part(Constant.MASV) RequestBody masv,
            @Part(Constant.TENSV) RequestBody tensv,
            @Part(Constant.GT) RequestBody gt,
            @Part(Constant.LOP) RequestBody lop);

}

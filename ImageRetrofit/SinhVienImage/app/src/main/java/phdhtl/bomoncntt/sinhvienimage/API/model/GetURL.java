package phdhtl.bomoncntt.sinhvienimage.API.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetURL {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("attach")
    @Expose
    private String attach;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAttach() {
        return attach;
    }

    public void setAttach(String attach) {
        this.attach = attach;
    }

}
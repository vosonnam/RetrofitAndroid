package phdhtl.bomoncntt.sinhvienimage.API.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class GetListStudent {

    @SerializedName("total")
    @Expose
    private Integer total;
    @SerializedName("items")
    @Expose
    private List<Item> items = new ArrayList<>();;

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public class Item {

        @SerializedName("masv")
        @Expose
        public String masv;
        @SerializedName("tensv")
        @Expose
        public String tensv;
        @SerializedName("gt")
        @Expose
        public String gt;
        @SerializedName("lop")
        @Expose
        public String lop;
        @SerializedName("anhsv")
        @Expose
        public String anhsv;

    }
}
package name.malkov.joomtest.network.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ListResult {

    @SerializedName("data")
    private List<GiphyItem> list;

    ListResult(List<GiphyItem> list) {
        this.list = list;
    }

    List<GiphyItem> getList() {
        return list;
    }
}

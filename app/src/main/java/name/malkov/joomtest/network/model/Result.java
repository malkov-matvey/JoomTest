package name.malkov.joomtest.network.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Result {

    @SerializedName("data")
    private List<GiphyItem> list;

    public Result(List<GiphyItem> list) {
        this.list = list;
    }

    public List<GiphyItem> getList() {
        return list;
    }
}

package name.malkov.joomtest.network.model;

import com.google.gson.annotations.SerializedName;

public class SingleResult {

    @SerializedName("data")
    private GiphyItem item;

    public GiphyItem getItem() {
        return item;
    }
}

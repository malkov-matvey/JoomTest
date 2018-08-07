package name.malkov.joomtest.network.model;

import com.google.gson.annotations.SerializedName;

public class GiphyImage {

    @SerializedName("url")
    private String url;

    @SerializedName("width")
    private int widthPx;

    @SerializedName("height")
    private int heightPx;

    public GiphyImage(String url, int widthPx, int heightPx) {
        this.url = url;
        this.widthPx = widthPx;
        this.heightPx = heightPx;
    }

    public String getUrl() {
        return url;
    }

    public int getWidthPx() {
        return widthPx;
    }

    public int getHeightPx() {
        return heightPx;
    }
}

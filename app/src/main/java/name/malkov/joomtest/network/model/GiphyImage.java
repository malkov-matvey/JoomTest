package name.malkov.joomtest.network.model;

import com.google.gson.annotations.SerializedName;

public class GiphyImage {

    @SerializedName("url")
    private String url;

    @SerializedName("width")
    private int widthPx;

    @SerializedName("height")
    private int heightPx;

    @SerializedName("webp")
    private String webpUrl;

    public String getWebpUrl() {
        return webpUrl;
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

package name.malkov.joomtest.network.model;

import com.google.gson.annotations.SerializedName;

class GiphyImage {

    @SerializedName("url")
    private String url;

    @SerializedName("width")
    private int widthPx;

    @SerializedName("height")
    private int heightPx;

    @SerializedName("webp")
    private String webpUrl;

    String getWebpUrl() {
        return webpUrl;
    }

    String getUrl() {
        return url;
    }

    int getWidthPx() {
        return widthPx;
    }

    int getHeightPx() {
        return heightPx;
    }
}

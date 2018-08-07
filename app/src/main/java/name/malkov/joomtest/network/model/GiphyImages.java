package name.malkov.joomtest.network.model;

import com.google.gson.annotations.SerializedName;

public class GiphyImages {

    @SerializedName("480w_still")
    private GiphyImage fixedWidthPreview;

    public GiphyImages(GiphyImage fixedWidthPreview) {
        this.fixedWidthPreview = fixedWidthPreview;
    }

    public GiphyImage getFixedWidthPreview() {
        return fixedWidthPreview;
    }
}

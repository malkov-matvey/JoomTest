package name.malkov.joomtest.network.model;

import com.google.gson.annotations.SerializedName;

public class GiphyImages {

    @SerializedName("480w_still")
    private GiphyImage fixedWidthPreview;

    @SerializedName("fixed_width")
    private GiphyImage fixedWidthAnimated;

    public GiphyImage getFixedWidthAnimated() {
        return fixedWidthAnimated;
    }

    public GiphyImage getFixedWidthPreview() {
        return fixedWidthPreview;
    }
}

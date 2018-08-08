package name.malkov.joomtest.network.model;

import com.google.gson.annotations.SerializedName;

class GiphyImages {

    @SerializedName("downsized")
    private GiphyImage downsizedOriginal;

    @SerializedName("fixed_width")
    private GiphyImage fixedWidthPreview;

    GiphyImage getFixedWidthPreview() {
        return fixedWidthPreview;
    }

    GiphyImage getDownsizedOriginal() {
        return downsizedOriginal;
    }
}

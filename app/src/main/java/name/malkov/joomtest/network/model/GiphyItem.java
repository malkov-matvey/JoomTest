package name.malkov.joomtest.network.model;

import com.google.gson.annotations.SerializedName;

class GiphyItem {

    @SerializedName("id")
    private String id;

    @SerializedName("url")
    private String url;

    @SerializedName("user")
    private GiphyUser user;

    @SerializedName("images")
    private GiphyImages images;

    String getId() {
        return id;
    }

    String getUrl() {
        return url;
    }

    GiphyUser getUser() {
        return user;
    }

    GiphyImages getImages() {
        return images;
    }
}

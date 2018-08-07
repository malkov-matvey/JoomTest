package name.malkov.joomtest.network.model;

import com.google.gson.annotations.SerializedName;

public class GiphyItem {

    @SerializedName("id")
    private String id;

    @SerializedName("url")
    private String url;

    @SerializedName("user")
    private GiphyUser user;

    @SerializedName("images")
    private GiphyImages images;

    public GiphyItem(String id, String url, GiphyUser user, GiphyImages images) {
        this.id = id;
        this.url = url;
        this.user = user;
        this.images = images;
    }

    public String getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    public GiphyUser getUser() {
        return user;
    }

    public GiphyImages getImages() {
        return images;
    }
}

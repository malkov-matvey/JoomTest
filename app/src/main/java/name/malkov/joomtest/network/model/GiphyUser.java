package name.malkov.joomtest.network.model;

import com.google.gson.annotations.SerializedName;

public class GiphyUser {

    @SerializedName("username")
    private String username;

    @SerializedName("display_name")
    private String displayName;

    @SerializedName("avatar_url")
    private String avatarUrl;

    @SerializedName("profile_url")
    private String profileUrl;

}

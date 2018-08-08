package name.malkov.joomtest.viewmodel.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

public class User implements Parcelable {

    @NonNull
    private String username;

    @NonNull
    private String profileUrl;

    private String displayName;

    private String twitterName;

    public User(@NonNull String username, @NonNull String profileUrl, String displayName, String twitterName) {
        this.username = username;
        this.profileUrl = profileUrl;
        this.displayName = displayName;
        this.twitterName = twitterName;
    }

    protected User(Parcel in) {
        username = in.readString();
        profileUrl = in.readString();
        displayName = in.readString();
        twitterName = in.readString();
    }

    @NonNull
    public String getUsername() {
        return username;
    }

    @NonNull
    public String getProfileUrl() {
        return profileUrl;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getTwitterName() {
        return twitterName;
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(username);
        parcel.writeString(profileUrl);
        parcel.writeString(displayName);
        parcel.writeString(twitterName);
    }
}

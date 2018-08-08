package name.malkov.joomtest.viewmodel.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

public class ImageItem implements Parcelable {

    public static final Creator<ImageItem> CREATOR = new Creator<ImageItem>() {
        @Override
        public ImageItem createFromParcel(Parcel in) {
            return new ImageItem(in);
        }

        @Override
        public ImageItem[] newArray(int size) {
            return new ImageItem[size];
        }
    };
    @NonNull
    private String id;
    private String url;
    private User user;
    private Image thumb;
    private Image preview;

    public ImageItem(@NonNull String id, String url, User user, Image thumb, Image preview) {
        this.id = id;
        this.url = url;
        this.user = user;
        this.thumb = thumb;
        this.preview = preview;
    }

    protected ImageItem(Parcel in) {
        id = in.readString();
        url = in.readString();
        user = in.readParcelable(User.class.getClassLoader());
        thumb = in.readParcelable(Image.class.getClassLoader());
        preview = in.readParcelable(Image.class.getClassLoader());
    }

    @NonNull
    public String getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    public User getUser() {
        return user;
    }

    public Image getThumb() {
        return thumb;
    }

    public Image getPreview() {
        return preview;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(url);
        parcel.writeParcelable(user, i);
        parcel.writeParcelable(thumb, i);
        parcel.writeParcelable(preview, i);
    }
}

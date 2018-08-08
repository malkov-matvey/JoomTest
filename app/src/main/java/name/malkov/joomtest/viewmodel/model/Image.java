package name.malkov.joomtest.viewmodel.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Image implements Parcelable {

    private int widthPx;

    private int heightPx;

    private String url;

    private String webpUrl;

    public Image(int widthPx, int heightPx, String url, String webpUrl) {
        this.widthPx = widthPx;
        this.heightPx = heightPx;
        this.url = url;
        this.webpUrl = webpUrl;
    }

    protected Image(Parcel in) {
        widthPx = in.readInt();
        heightPx = in.readInt();
        url = in.readString();
        webpUrl = in.readString();
    }

    public int getWidthPx() {
        return widthPx;
    }

    public int getHeightPx() {
        return heightPx;
    }

    public String getUrl() {
        return url;
    }

    public String getWebpUrl() {
        return webpUrl;
    }

    public static final Creator<Image> CREATOR = new Creator<Image>() {
        @Override
        public Image createFromParcel(Parcel in) {
            return new Image(in);
        }

        @Override
        public Image[] newArray(int size) {
            return new Image[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(widthPx);
        parcel.writeInt(heightPx);
        parcel.writeString(url);
        parcel.writeString(webpUrl);
    }
}

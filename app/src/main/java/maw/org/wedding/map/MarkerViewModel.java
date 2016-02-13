package maw.org.wedding.map;

import android.os.Parcel;
import android.os.Parcelable;

public class MarkerViewModel implements Parcelable{

    public String id;
    public String title;
    public String imageUrl;
    public String websiteUrl;

    public MarkerViewModel(){}

    protected MarkerViewModel(Parcel in) {
        id = in.readString();
        title = in.readString();
        imageUrl = in.readString();
        websiteUrl = in.readString();
    }

    public static final Creator<MarkerViewModel> CREATOR = new Creator<MarkerViewModel>() {
        @Override
        public MarkerViewModel createFromParcel(Parcel in) {
            return new MarkerViewModel(in);
        }

        @Override
        public MarkerViewModel[] newArray(int size) {
            return new MarkerViewModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(title);
        dest.writeString(imageUrl);
        dest.writeString(websiteUrl);
    }
}

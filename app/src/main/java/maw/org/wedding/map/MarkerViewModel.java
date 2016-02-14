package maw.org.wedding.map;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MarkerViewModel implements Parcelable{

    public String id;
    public String title;
    public List<String> imageUrls = new ArrayList<>();
    public String websiteUrl;
    public String address;
    public String rating;
    public String phoneNumber;

    public MarkerViewModel(){}

    protected MarkerViewModel(Parcel in) {
        id = in.readString();
        title = in.readString();
        String[] urls = in.createStringArray();
        imageUrls = Arrays.asList(urls);
        websiteUrl = in.readString();
        address = in.readString();
        rating = in.readString();
        phoneNumber = in.readString();
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
        dest.writeStringArray(imageUrls.toArray(new String[]{}));
        dest.writeString(websiteUrl);
        dest.writeString(address);
        dest.writeString(rating);
        dest.writeString(phoneNumber);
    }
}

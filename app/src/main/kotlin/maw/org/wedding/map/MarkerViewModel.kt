package maw.org.wedding.map

import android.os.Parcel
import android.os.Parcelable

data class MarkerViewModel(val id: String, val title: String, val imageUrls: MutableList<String> = mutableListOf(), val websiteUrl: String?, val address: String?, val rating: String?, val phoneNumber: String?) : Parcelable {

    constructor(source: Parcel) : this(source.readString(), source.readString(), source.createStringArrayList(), source.readString(), source.readString(), source.readString(), source.readString())

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(id)
        dest.writeString(title)
        dest.writeStringList(imageUrls)
        dest.writeString(websiteUrl)
        dest.writeString(address)
        dest.writeString(rating)
        dest.writeString(phoneNumber)
    }

    companion  object {
        @JvmField final val CREATOR: Parcelable.Creator<MarkerViewModel> = object : Parcelable.Creator<MarkerViewModel> {
            override fun createFromParcel(`in`: Parcel): MarkerViewModel {
                return MarkerViewModel(`in`)
            }

            override fun newArray(size: Int): Array<MarkerViewModel?> {
                return arrayOfNulls(size)
            }
        }
    }

}

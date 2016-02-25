package maw.org.wedding.map

import android.os.Parcel
import android.os.Parcelable
import java.util.*

class MarkerViewModel : Parcelable {

    var id: String? = null
    var title: String? = null
    var imageUrls: ArrayList<String> = ArrayList()
    var websiteUrl: String? = null
    var address: String? = null
    var rating: String? = null
    var phoneNumber: String? = null

    constructor() {
    }

    protected constructor(`in`: Parcel) {
        id = `in`.readString()
        title = `in`.readString()
        imageUrls = `in`.createStringArrayList()
        websiteUrl = `in`.readString()
        address = `in`.readString()
        rating = `in`.readString()
        phoneNumber = `in`.readString()
    }

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

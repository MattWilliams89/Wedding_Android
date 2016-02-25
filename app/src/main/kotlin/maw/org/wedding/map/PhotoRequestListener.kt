package maw.org.wedding.map

interface PhotoRequestListener {
    fun onSuccess(imageURL: String)
    fun onFailure()
}

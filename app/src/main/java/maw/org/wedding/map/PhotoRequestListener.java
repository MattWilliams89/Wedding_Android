package maw.org.wedding.map;

public interface PhotoRequestListener {
    void onSuccess(String imageURL);
    void onFailure();
}

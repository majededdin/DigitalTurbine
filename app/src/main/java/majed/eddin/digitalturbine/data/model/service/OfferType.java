package majed.eddin.digitalturbine.data.model.service;

import android.os.Parcel;
import android.os.Parcelable;

public class OfferType implements Parcelable {

    private int offer_type_id;
    private String readable;

    protected OfferType(Parcel in) {
        offer_type_id = in.readInt();
        readable = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(offer_type_id);
        dest.writeString(readable);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<OfferType> CREATOR = new Creator<OfferType>() {
        @Override
        public OfferType createFromParcel(Parcel in) {
            return new OfferType(in);
        }

        @Override
        public OfferType[] newArray(int size) {
            return new OfferType[size];
        }
    };

    public int getOffer_type_id() {
        return offer_type_id;
    }

    public void setOffer_type_id(int offer_type_id) {
        this.offer_type_id = offer_type_id;
    }

    public String getReadable() {
        return readable;
    }

    public void setReadable(String readable) {
        this.readable = readable;
    }
}
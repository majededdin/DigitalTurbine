package majed.eddin.digitalturbine.data.model.service;

import android.os.Parcel;
import android.os.Parcelable;

public class TimeToPayOut implements Parcelable {

    private int amount;
    private String readable;

    protected TimeToPayOut(Parcel in) {
        amount = in.readInt();
        readable = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(amount);
        dest.writeString(readable);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<TimeToPayOut> CREATOR = new Creator<TimeToPayOut>() {
        @Override
        public TimeToPayOut createFromParcel(Parcel in) {
            return new TimeToPayOut(in);
        }

        @Override
        public TimeToPayOut[] newArray(int size) {
            return new TimeToPayOut[size];
        }
    };

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getReadable() {
        return readable;
    }

    public void setReadable(String readable) {
        this.readable = readable;
    }
}
package majed.eddin.digitalturbine.data.model.service;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Offer implements Parcelable {

    private String title;
    private int offer_id;
    private String teaser;
    private String required_actions;
    private String link;
    private List<OfferType> offer_types;
    private Thumbnail thumbnail;
    private int payout;
    private TimeToPayOut time_to_payout;

    protected Offer(Parcel in) {
        title = in.readString();
        offer_id = in.readInt();
        teaser = in.readString();
        required_actions = in.readString();
        link = in.readString();
        offer_types = in.createTypedArrayList(OfferType.CREATOR);
        thumbnail = in.readParcelable(Thumbnail.class.getClassLoader());
        payout = in.readInt();
        time_to_payout = in.readParcelable(TimeToPayOut.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeInt(offer_id);
        dest.writeString(teaser);
        dest.writeString(required_actions);
        dest.writeString(link);
        dest.writeTypedList(offer_types);
        dest.writeParcelable(thumbnail, flags);
        dest.writeInt(payout);
        dest.writeParcelable(time_to_payout, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Offer> CREATOR = new Creator<Offer>() {
        @Override
        public Offer createFromParcel(Parcel in) {
            return new Offer(in);
        }

        @Override
        public Offer[] newArray(int size) {
            return new Offer[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getOffer_id() {
        return offer_id;
    }

    public void setOffer_id(int offer_id) {
        this.offer_id = offer_id;
    }

    public String getTeaser() {
        return teaser;
    }

    public void setTeaser(String teaser) {
        this.teaser = teaser;
    }

    public String getRequired_actions() {
        return required_actions;
    }

    public void setRequired_actions(String required_actions) {
        this.required_actions = required_actions;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public List<OfferType> getOffer_types() {
        return offer_types;
    }

    public void setOffer_types(List<OfferType> offer_types) {
        this.offer_types = offer_types;
    }

    public Thumbnail getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(Thumbnail thumbnail) {
        this.thumbnail = thumbnail;
    }

    public int getPayout() {
        return payout;
    }

    public void setPayout(int payout) {
        this.payout = payout;
    }

    public TimeToPayOut getTime_to_payout() {
        return time_to_payout;
    }

    public void setTime_to_payout(TimeToPayOut time_to_payout) {
        this.time_to_payout = time_to_payout;
    }
}
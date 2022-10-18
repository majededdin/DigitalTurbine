package majed.eddin.digitalturbine.data.model.service;

import android.os.Parcel;
import android.os.Parcelable;

public class Information implements Parcelable {

    private String app_name;
    private int appid;
    private String virtual_currency;
    private String country;
    private String language;
    private String support_url;

    protected Information(Parcel in) {
        app_name = in.readString();
        appid = in.readInt();
        virtual_currency = in.readString();
        country = in.readString();
        language = in.readString();
        support_url = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(app_name);
        dest.writeInt(appid);
        dest.writeString(virtual_currency);
        dest.writeString(country);
        dest.writeString(language);
        dest.writeString(support_url);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Information> CREATOR = new Creator<Information>() {
        @Override
        public Information createFromParcel(Parcel in) {
            return new Information(in);
        }

        @Override
        public Information[] newArray(int size) {
            return new Information[size];
        }
    };

    public String getApp_name() {
        return app_name;
    }

    public void setApp_name(String app_name) {
        this.app_name = app_name;
    }

    public int getAppid() {
        return appid;
    }

    public void setAppid(int appid) {
        this.appid = appid;
    }

    public String getVirtual_currency() {
        return virtual_currency;
    }

    public void setVirtual_currency(String virtual_currency) {
        this.virtual_currency = virtual_currency;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getSupport_url() {
        return support_url;
    }

    public void setSupport_url(String support_url) {
        this.support_url = support_url;
    }
}
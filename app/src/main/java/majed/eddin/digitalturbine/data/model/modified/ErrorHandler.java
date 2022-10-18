package majed.eddin.digitalturbine.data.model.modified;

import androidx.annotation.NonNull;

public class ErrorHandler {

    private String key, value;
    private final boolean isDataValid;

    public ErrorHandler(String key) {
        this.key = key;
        this.value = null;
        this.isDataValid = true;
    }

    public ErrorHandler(String key, String value) {
        this.key = key;
        this.value = value;
        this.isDataValid = false;
    }

    public ErrorHandler(boolean isDataValid) {
        this.key = null;
        this.value = null;
        this.isDataValid = isDataValid;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public boolean isDataValid() {
        return isDataValid;
    }


    @NonNull
    @Override
    public String toString() {
        return "Key " + getKey() + " value " + getValue();
    }

}
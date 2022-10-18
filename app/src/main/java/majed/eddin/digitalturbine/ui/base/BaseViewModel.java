package majed.eddin.digitalturbine.ui.base;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import majed.eddin.digitalturbine.R;
import majed.eddin.digitalturbine.data.model.modified.ErrorHandler;
import majed.eddin.digitalturbine.data.repository.BaseRepository;

public abstract class BaseViewModel extends AndroidViewModel {

    private final BaseRepository repo;
    private final Application application;
    private final MutableLiveData<ErrorHandler> formState = new MutableLiveData<>();

    public BaseViewModel(@NonNull Application application) {
        super(application);
        this.application = application;
        repo = new BaseRepository();
    }

    public abstract MutableLiveData<Boolean> showLoadingResponse();

    //------------------------------------------- Settings -----------------------------------------

    public void onDestroy() {
        repo.onDestroy();
    }

    //------------------------------------------- Setting Methods ----------------------------------

    public MutableLiveData<ErrorHandler> getFormState() {
        return formState;
    }

    public String getResString(int res) {
        return application.getApplicationContext().getString(res);
    }

    //------------------------------------------- Validation Methods -------------------------------

    public boolean isFieldRequired(String field) {
        return field == null || field.equals("");
    }

    //------------------------------------- Input Validation Methods -------------------------------

    protected boolean validateInputIsRequired(String fieldValue, String fieldKey) {
        if (isFieldRequired(fieldValue)) {
            formState.setValue(new ErrorHandler(fieldKey, getResString(R.string.required)));
            return false;
        } else {
            formState.setValue(new ErrorHandler(fieldKey));
            return true;
        }
    }
}
package majed.eddin.digitalturbine.data.repository;

import androidx.lifecycle.MutableLiveData;

import io.reactivex.disposables.CompositeDisposable;
import majed.eddin.digitalturbine.data.remote.ApiClient;
import majed.eddin.digitalturbine.data.remote.ApiService;

public class BaseRepository {

    private final ApiService apiService;
    private final CompositeDisposable disposable;
    private final MutableLiveData<Boolean> showLoading = new MutableLiveData<>();


    public BaseRepository() {
        apiService = ApiClient.getInstance();
        disposable = new CompositeDisposable();
    }

    public ApiService getApiService() {
        return apiService;
    }

    public CompositeDisposable getDisposable() {
        return disposable;
    }

    public void onDestroy() {
        disposable.dispose();
    }

    //-----------------------------------------Methods----------------------------------------------

    protected void showLoading(boolean status) {
        getShowLoading().setValue(status);
    }

    public MutableLiveData<Boolean> getShowLoading() {
        return showLoading;
    }

}
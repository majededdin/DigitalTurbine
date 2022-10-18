package majed.eddin.digitalturbine.data.repository;

import static majed.eddin.digitalturbine.data.remote.ApiStatus.OnSuccess;

import androidx.lifecycle.MutableLiveData;

import java.util.HashMap;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import majed.eddin.digitalturbine.data.model.service.Result;
import majed.eddin.digitalturbine.data.remote.ApiResponse;

public class OffersRepo extends BaseRepository {

    private final MutableLiveData<ApiResponse<Result>> offersIndexResult = new MutableLiveData<>();

    public MutableLiveData<ApiResponse<Result>> getOffersIndexResponse() {
        return offersIndexResult;
    }

    public void getOffers(String signature, HashMap<String, Object> queryMap) {
        showLoading(true);
        getDisposable().clear();
        getDisposable().add(getApiService().getOffers(signature, queryMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> {
                    showLoading(false);
                    offersIndexResult.setValue(new ApiResponse<>(OnSuccess, result));
                }, throwable -> {
                    showLoading(false);
                    offersIndexResult.setValue((ApiResponse<Result>) ApiResponse.getErrorBody(throwable));
                }));
    }
}
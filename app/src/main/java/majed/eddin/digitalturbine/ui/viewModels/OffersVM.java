package majed.eddin.digitalturbine.ui.viewModels;

import static majed.eddin.digitalturbine.data.consts.Params.APP_ID;
import static majed.eddin.digitalturbine.data.consts.Params.ERROR_INVALID_APPID;
import static majed.eddin.digitalturbine.data.consts.Params.ERROR_INVALID_UID;
import static majed.eddin.digitalturbine.data.consts.Params.HASH_KEY;
import static majed.eddin.digitalturbine.data.consts.Params.IP;
import static majed.eddin.digitalturbine.data.consts.Params.LOCALE;
import static majed.eddin.digitalturbine.data.consts.Params.OFFER_TYPES;
import static majed.eddin.digitalturbine.data.consts.Params.OS_VERSION;
import static majed.eddin.digitalturbine.data.consts.Params.PAGE;
import static majed.eddin.digitalturbine.data.consts.Params.PHONE_VERSION;
import static majed.eddin.digitalturbine.data.consts.Params.TIMESTAMP;
import static majed.eddin.digitalturbine.data.consts.Params.Token;
import static majed.eddin.digitalturbine.data.consts.Params.USER_ID;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

import majed.eddin.digitalturbine.data.model.modified.ErrorHandler;
import majed.eddin.digitalturbine.data.model.service.Result;
import majed.eddin.digitalturbine.data.remote.ApiResponse;
import majed.eddin.digitalturbine.data.repository.OffersRepo;
import majed.eddin.digitalturbine.ui.base.BaseViewModel;
import majed.eddin.digitalturbine.utils.GenerateSHA;

public class OffersVM extends BaseViewModel {

    private final OffersRepo repo;

    public OffersVM(@NonNull Application application) {
        super(application);
        repo = new OffersRepo();
    }

    @Override
    public MutableLiveData<Boolean> showLoadingResponse() {
        return repo.getShowLoading();
    }

    public MutableLiveData<ApiResponse<Result>> getOffersIndexResult() {
        return repo.getOffersIndexResponse();
    }


    public void getOffers(String applicationId, String userId, String token, int page) {
        HashMap<String, Object> map = new HashMap<>();
        map.put(APP_ID, applicationId);
        map.put(IP, "109.235.143.113");
        map.put(LOCALE, "DE");
        map.put(OFFER_TYPES, "112");
        map.put(OS_VERSION, android.os.Build.VERSION.SDK_INT);
        map.put(PAGE, page);
        map.put(PHONE_VERSION, android.os.Build.VERSION.RELEASE);
        map.put(TIMESTAMP, System.currentTimeMillis() / 1000L);
        map.put(USER_ID, userId);

        String allParams = "appid=".concat(applicationId)
                .concat("&").concat("ip=").concat("109.235.143.113")
                .concat("&").concat("locale=").concat("DE")
                .concat("&").concat("offer_types=").concat("112")
                .concat("&").concat("os_version=").concat(String.valueOf(android.os.Build.VERSION.SDK_INT))
                .concat("&").concat("page=").concat(String.valueOf(page))
                .concat("&").concat("phone_version=").concat(String.valueOf(android.os.Build.VERSION.RELEASE))
                .concat("&").concat("timestamp=").concat(String.valueOf(System.currentTimeMillis() / 1000L))
                .concat("&").concat("uid=").concat(userId)
                .concat("&").concat(token);

        try {
            map.put(HASH_KEY, GenerateSHA.SHA1(allParams));
            repo.getOffers(token, map);
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }


    public void validateInputForm(String applicationId, String userId, String token) {
        boolean isApplicationIdValid = validateInputIsRequired(applicationId, ERROR_INVALID_APPID);
        boolean isUserIdValid = validateInputIsRequired(userId, ERROR_INVALID_UID);
        boolean isTokenValid = validateInputIsRequired(token, Token);

        getFormState().setValue(new ErrorHandler(isApplicationIdValid &&
                isUserIdValid && isTokenValid));
    }

}
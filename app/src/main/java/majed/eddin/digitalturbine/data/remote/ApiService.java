package majed.eddin.digitalturbine.data.remote;

import static majed.eddin.digitalturbine.data.consts.Params.X_Signature;

import java.util.HashMap;

import io.reactivex.Observable;
import majed.eddin.digitalturbine.data.model.service.Result;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.QueryMap;

public interface ApiService {

    @GET("offers.json")
    @Headers({"Accept: application/json", "Content-Type: application/json"})
    Observable<Result> getOffers(@Header(X_Signature) String signature,
                                 @QueryMap HashMap<String, Object> map);
}
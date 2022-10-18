package majed.eddin.digitalturbine.data.remote;

import static majed.eddin.digitalturbine.data.remote.ApiStatus.OnBackEndError;
import static majed.eddin.digitalturbine.data.remote.ApiStatus.OnConnectException;
import static majed.eddin.digitalturbine.data.remote.ApiStatus.OnError;
import static majed.eddin.digitalturbine.data.remote.ApiStatus.OnFailure;
import static majed.eddin.digitalturbine.data.remote.ApiStatus.OnTimeoutException;
import static majed.eddin.digitalturbine.data.remote.ApiStatus.OnUnknownHost;

import android.util.Log;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import retrofit2.HttpException;

public class ApiResponse<T> {

    private String message;
    private T responseObject;
    private final ApiStatus apiStatus;

    public ApiResponse(ApiStatus apiStatus, String message) {
        this.apiStatus = apiStatus;
        this.message = message;
    }

    public ApiResponse(ApiStatus apiStatus) {
        this.apiStatus = apiStatus;
    }


    public ApiResponse(ApiStatus apiStatus, T responseObject) {
        this.apiStatus = apiStatus;
        this.responseObject = responseObject;
    }

    public ApiStatus getApiStatus() {
        return apiStatus;
    }

    public String getMessage() {
        return message;
    }

    public T getResponseObject() {
        return responseObject;
    }

    public static ApiResponse<?> getErrorBody(Throwable throwable) {
        ApiResponse<?> response = null;
        try {
            if (throwable instanceof HttpException) {
                HttpException exception = (HttpException) throwable;
                switch (exception.code()) {
                    case 400:
                        response = new ApiResponse<>(OnError);
                        break;

                    case 500:
                        String backEndException = exception.response().errorBody().string();
                        Log.e("backEndException ", backEndException);
                        response = new ApiResponse<>(OnBackEndError, backEndException);
                        break;

                    default:
                        String exceptionMsg = exception.response().errorBody().string();
                        Log.e("HttpExceptionMsg ", exceptionMsg);
                        response = new ApiResponse<>(OnFailure, exceptionMsg);
                        break;
                }
            } else if (throwable instanceof UnknownHostException)
                response = new ApiResponse<>(OnUnknownHost, throwable.getMessage());

            else if (throwable instanceof ConnectException)
                response = new ApiResponse<>(OnConnectException, throwable.getMessage());

            else if (throwable instanceof SocketTimeoutException)
                response = new ApiResponse<>(OnTimeoutException, throwable.getMessage());

            else {
                String throwableMsg = throwable.getMessage();
                System.out.println("throwableMsg " + throwableMsg);
                response = new ApiResponse<>(OnFailure, throwableMsg);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

}
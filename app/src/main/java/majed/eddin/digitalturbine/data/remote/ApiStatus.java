package majed.eddin.digitalturbine.data.remote;

public enum ApiStatus {
    OnSuccess,
    OnError,
    OnFailure,
    OnBackEndError,
    OnUnknownHost,
    OnConnectException,
    OnTimeoutException
}
package majed.eddin.digitalturbine.data.remote;

import java.io.File;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import majed.eddin.digitalturbine.BuildConfig;
import majed.eddin.digitalturbine.data.consts.AppConst;
import okhttp3.Cache;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    private static Retrofit ourInstance;

    private static final File cacheDir = new File(System.getProperty("java.io.tmpdir"), UUID.randomUUID().toString());
    private static final Cache cache = new Cache(cacheDir, 1024);
    private static final HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
    private static final OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .cache(cache)
            .retryOnConnectionFailure(true)
            .connectionPool(new ConnectionPool(15, 500000, TimeUnit.MILLISECONDS))
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(logging);


    private static Retrofit getClient() {
        logging.level(AppConst.getInstance().isDebug() ? HttpLoggingInterceptor.Level.BASIC : HttpLoggingInterceptor.Level.NONE);
        if (ourInstance == null)
            ourInstance = new Retrofit.Builder()
                    .baseUrl(BuildConfig.BASE_URL)
                    .client(okHttpClient.build())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        return ourInstance;
    }


    public static ApiService getInstance() {
        return getClient().create(ApiService.class);
    }
}
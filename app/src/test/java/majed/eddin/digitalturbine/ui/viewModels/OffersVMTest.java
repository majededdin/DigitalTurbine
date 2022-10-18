package majed.eddin.digitalturbine.ui.viewModels;

import static majed.eddin.digitalturbine.data.consts.Params.APP_ID;
import static majed.eddin.digitalturbine.data.consts.Params.HASH_KEY;
import static majed.eddin.digitalturbine.data.consts.Params.IP;
import static majed.eddin.digitalturbine.data.consts.Params.LOCALE;
import static majed.eddin.digitalturbine.data.consts.Params.OFFER_TYPES;
import static majed.eddin.digitalturbine.data.consts.Params.OS_VERSION;
import static majed.eddin.digitalturbine.data.consts.Params.PAGE;
import static majed.eddin.digitalturbine.data.consts.Params.TIMESTAMP;
import static majed.eddin.digitalturbine.data.consts.Params.USER_ID;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.internal.schedulers.ExecutorScheduler;
import io.reactivex.plugins.RxJavaPlugins;
import majed.eddin.digitalturbine.data.model.service.Result;
import majed.eddin.digitalturbine.data.remote.ApiResponse;
import majed.eddin.digitalturbine.data.remote.ApiStatus;
import majed.eddin.digitalturbine.utils.GenerateSHA;

public class OffersVMTest extends Application {

    //It simplifies that it's a rule that any task executaion will be instant.
    @Rule
    public InstantTaskExecutorRule rule = new InstantTaskExecutorRule();

    @InjectMocks
    OffersVM viewModel = new OffersVM(this);

    private Observable<ApiResponse<Result>> testApiResponse;

    //@Before means it runs before any unit test
    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getOffersSuccess() {
        ApiResponse<Result> resultModel = new ApiResponse<>(ApiStatus.OnSuccess, "OK");

        testApiResponse = Observable.just(resultModel);

        // Mock API response
        HashMap<String, Object> map = new HashMap<>();
        map.put(APP_ID, 1246);
        map.put(IP, "109.235.143.113");
        map.put(LOCALE, "DE");
        map.put(OFFER_TYPES, "112");
        map.put(OS_VERSION, android.os.Build.VERSION.SDK_INT);
        map.put(PAGE, 1);
        map.put(TIMESTAMP, System.currentTimeMillis() / 1000L);
        map.put(USER_ID, "superman");

        String allParams = "appid=".concat("1246")
                .concat("&").concat("ip=").concat("109.235.143.113")
                .concat("&").concat("locale=").concat("DE")
                .concat("&").concat("offer_types=").concat("112")
                .concat("&").concat("os_version=").concat(String.valueOf(android.os.Build.VERSION.SDK_INT))
                .concat("&").concat("page=").concat("1")
                .concat("&").concat("timestamp=").concat(String.valueOf(System.currentTimeMillis() / 1000L))
                .concat("&").concat("uid=").concat("superman")
                .concat("&").concat("82085b8b7b31b3e80beefdc0430e2315f67cd3e1");

        try {
            map.put(HASH_KEY, GenerateSHA.SHA1(allParams));
            viewModel.getOffersIndexResult().setValue(resultModel);
            viewModel.getOffers("1246", "superman", "82085b8b7b31b3e80beefdc0430e2315f67cd3e1", 1);

            //these are the expected value for three live data which we define in viewmodel class
            Assert.assertEquals("OK", viewModel.getOffersIndexResult().getValue().getMessage());
            Assert.assertNotSame(viewModel.getOffersIndexResult().getValue().getApiStatus(), ApiStatus.OnError);
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }


    }

    @Test
    public void getOffersError() {
        ApiResponse<Result> resultModel = new ApiResponse<>(ApiStatus.OnError, "ERROR");

        testApiResponse = Observable.just(resultModel);

        // Mock API response
        HashMap<String, Object> map = new HashMap<>();
        map.put(APP_ID, 1246);
        map.put(IP, "109.235.143.113");
        map.put(LOCALE, "DE");
        map.put(OFFER_TYPES, "112");
        map.put(OS_VERSION, android.os.Build.VERSION.SDK_INT);
        map.put(PAGE, 1);
        map.put(TIMESTAMP, System.currentTimeMillis() / 1000L);
        map.put(USER_ID, "superman");

        String allParams = "appid=".concat("1246")
                .concat("&").concat("ip=").concat("109.235.143.113")
                .concat("&").concat("locale=").concat("DE")
                .concat("&").concat("offer_types=").concat("112")
                .concat("&").concat("os_version=").concat(String.valueOf(android.os.Build.VERSION.SDK_INT))
                .concat("&").concat("page=").concat("1")
                .concat("&").concat("timestamp=").concat(String.valueOf(System.currentTimeMillis() / 1000L))
                .concat("&").concat("uid=").concat("superman")
                .concat("&").concat("82085b8b7b31b3e80beefdc0430e2315f67cd3e1");

        try {
            map.put(HASH_KEY, GenerateSHA.SHA1(allParams));
            viewModel.getOffersIndexResult().setValue(resultModel);
            viewModel.getOffers("1246", "superman", "82085b8b7b31b3e80beefdc0430e2315f67cd3e1", 1);

            //these are the expected value for three live data which we define in viewmodel class
            Assert.assertEquals("ERROR", viewModel.getOffersIndexResult().getValue().getMessage());
            Assert.assertSame(viewModel.getOffersIndexResult().getValue().getApiStatus(), ApiStatus.OnError);
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }


    }

    @Before
    public void setupRxSchedulers() {
        Scheduler immediate = new Scheduler() {
            @NonNull
            @Override
            public Worker createWorker() {
                return new ExecutorScheduler.ExecutorWorker(command -> {
                    command.run();
                }, true);
            }
        };

        //.subscribeOn(Schedulers.newThread())
        RxJavaPlugins.setInitNewThreadSchedulerHandler(schedulerCallable -> immediate);

        //.observeOn(AndroidSchedulers.mainThread())
        RxAndroidPlugins.setInitMainThreadSchedulerHandler(schedulerCallable -> immediate);

        //because of this our unit test is proceed without any interruption
    }
}
package majed.eddin.digitalturbine.data.application;

import android.app.Application;

import majed.eddin.digitalturbine.BuildConfig;
import majed.eddin.digitalturbine.data.consts.AppConst;

public class BaseApplication extends Application {

    private AppConst aConst;
    private static BaseApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        aConst = AppConst.getInstance();
        initAppConst();
    }

    public static synchronized BaseApplication getInstance() {
        return instance;
    }

    private void initAppConst() {
        aConst.setDebug(BuildConfig.DEBUG);
    }

}
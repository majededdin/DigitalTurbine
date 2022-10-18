package majed.eddin.digitalturbine.data.consts;

public class AppConst {

    private boolean debug;

    private static AppConst instance = new AppConst();

    public static AppConst getInstance() {
        return instance;
    }

    public static void setInstance(AppConst instance) {
        AppConst.instance = instance;
    }

    //----------------------------------------Getters & Setters-------------------------------------

    public boolean isDebug() {
        return debug;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

}
package ve.needforock.contactshare.data;

import android.app.Application;

/**
 * Created by Soporte on 13-Sep-17.
 */

public class ApplicationContact extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        new Offline().setPersistence();
    }
}

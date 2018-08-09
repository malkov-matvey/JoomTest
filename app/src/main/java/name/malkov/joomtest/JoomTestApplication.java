package name.malkov.joomtest;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

public class JoomTestApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
    }
}

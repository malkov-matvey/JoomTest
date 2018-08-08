package name.malkov.joomtest.ui;

import android.net.Uri;
import android.widget.ImageView;

public interface ImageDrawingProtocol<T extends ImageView> {

    void showThumbAnimation(T image, Uri thumb);

    void showAnimation(T image, Uri thumb, Uri origin);

    void cancel(T image);
}

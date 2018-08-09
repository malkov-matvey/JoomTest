package name.malkov.joomtest.ui;

import android.net.Uri;
import android.widget.ImageView;

public interface ImageDrawingProtocol<T extends ImageView> {

    void showThumbAnimation(T image, Uri thumb);

    void cancel(T image);
}

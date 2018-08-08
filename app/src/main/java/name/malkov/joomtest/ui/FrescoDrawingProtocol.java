package name.malkov.joomtest.ui;

import android.net.Uri;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.request.ImageRequest;

public class FrescoDrawingProtocol implements ImageDrawingProtocol<SimpleDraweeView> {

    public void showThumbAnimation(SimpleDraweeView image, Uri thumb) {
        image.setController(
                Fresco.newDraweeControllerBuilder()
                        .setUri(thumb)
                        .setAutoPlayAnimations(true)
                        .build()
        );
    }

    @Override
    public void showAnimation(SimpleDraweeView image, Uri thumb, Uri origin) {
        image.setController(Fresco.newDraweeControllerBuilder()
                .setLowResImageRequest(ImageRequest.fromUri(thumb))
                .setImageRequest(ImageRequest.fromUri(origin))
                .setAutoPlayAnimations(true)
                .build()
        );
    }

    @Override
    public void cancel(SimpleDraweeView image) {
        image.setController(null);
    }
}

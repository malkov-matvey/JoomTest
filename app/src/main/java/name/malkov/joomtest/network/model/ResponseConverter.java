package name.malkov.joomtest.network.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import name.malkov.joomtest.viewmodel.model.Image;
import name.malkov.joomtest.viewmodel.model.ImageItem;
import name.malkov.joomtest.viewmodel.model.User;

public class ResponseConverter {

    public static List<ImageItem> convertGiphyList(ListResult response) {
        final List<GiphyItem> list = response.getList();
        final List<ImageItem> result = new ArrayList<>(list.size());
        for (final GiphyItem gi : list) {
            result.add(convertItem(gi));
        }
        return Collections.unmodifiableList(result);
    }

    public static ImageItem convertGiphySingleItem(SingleResult response) {
        return convertItem(response.getItem());
    }

    private static ImageItem convertItem(final GiphyItem item) {
        final GiphyUser itemUser = item.getUser();
        final GiphyImage origin = item.getImages().getDownsizedOriginal();
        final GiphyImage thumb = item.getImages().getFixedWidthPreview();
        final User user = itemUser == null ? null : new User(itemUser.getUsername(),
                itemUser.getProfileUrl(),
                itemUser.getDisplayName(),
                itemUser.getTwitterHandle()
        );
        return new ImageItem(item.getId(), item.getUrl(), user, convertImage(thumb), convertImage(origin));
    }

    private static Image convertImage(final GiphyImage image) {
        final String webp = image.getWebpUrl() == null ? image.getUrl() : image.getWebpUrl();
        return new Image(image.getWidthPx(), image.getHeightPx(), image.getUrl(), webp);
    }
}

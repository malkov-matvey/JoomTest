package name.malkov.joomtest.ui.imagelist;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import name.malkov.joomtest.ui.ImageDrawingProtocol;
import name.malkov.joomtest.viewmodel.model.Image;
import name.malkov.joomtest.viewmodel.model.ImageItem;

public class ImageListAdapter extends RecyclerView.Adapter<ImageListAdapter.ImageViewHolder> {

    private List<ImageItem> items = new ArrayList<>();
    private ClickConsumer<ImageItem> click;
    private ImageDrawingProtocol<SimpleDraweeView> imageProtocol;

    ImageListAdapter(ImageDrawingProtocol<SimpleDraweeView> imageDrawingProtocol, ClickConsumer<ImageItem> click) {
        this.click = click;
        this.imageProtocol = imageDrawingProtocol;
    }

    public void addItems(List<ImageItem> is) {
        int old = this.items.size();
        this.items = is;
        //hack for replacing items after refresh.
        //Not a unified solution, but works well for current needs
        if (is.size() <= old) {
            notifyDataSetChanged();
        } else {
            notifyItemRangeInserted(old, items.size());
        }
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        SimpleDraweeView view = new SimpleDraweeView(viewGroup.getContext());
        view.getHierarchy().setActualImageScaleType(ScalingUtils.ScaleType.CENTER_CROP);
        view.setLayoutParams(new RecyclerView.LayoutParams(
                RecyclerView.LayoutParams.MATCH_PARENT,
                RecyclerView.LayoutParams.WRAP_CONTENT
        ));
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder vh, int i) {
        final ImageItem item = items.get(i);
        final Image image = item.getThumb();
        if (image.getWidthPx() != 0 && image.getHeightPx() != 0) {
            final float ratio = image.getWidthPx() / (float) image.getHeightPx();
            vh.image.setAspectRatio(ratio);
        }
        vh.itemView.setOnClickListener(view -> click.consume(item));
        imageProtocol.showThumbAnimation(vh.image, Uri.parse(image.getWebpUrl()));
    }

    @Override
    public void onViewRecycled(@NonNull ImageViewHolder vh) {
        imageProtocol.cancel(vh.image);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class ImageViewHolder extends RecyclerView.ViewHolder {

        public SimpleDraweeView image;

        ImageViewHolder(final SimpleDraweeView view) {
            super(view);
            image = view;
        }

    }
}

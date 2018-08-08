package name.malkov.joomtest.ui;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Consumer;
import name.malkov.joomtest.network.model.GiphyImage;
import name.malkov.joomtest.network.model.GiphyItem;

public class ImageListAdapter extends RecyclerView.Adapter<ImageListAdapter.ImageViewHolder> {

    private List<GiphyItem> items = new ArrayList<>();
    private Consumer<GiphyItem> click;

    ImageListAdapter(Consumer<GiphyItem> click) {
        this.click = click;
    }

    public void addItems(List<GiphyItem> is) {
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
        final GiphyItem item = items.get(i);
        final GiphyImage image = item.getImages().getFixedWidthAnimated();
        vh.image.setAspectRatio(image.getWidthPx() / (float) image.getHeightPx());
        vh.itemView.setOnClickListener(view -> {
            try {
                click.accept(item);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        vh.image.setController(
                Fresco.newDraweeControllerBuilder()
                        .setUri(Uri.parse(image.getWebpUrl())).setAutoPlayAnimations(true)
                        .build()
        );
    }

    @Override
    public void onViewRecycled(@NonNull ImageViewHolder vh) {
        vh.image.setController(null);
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

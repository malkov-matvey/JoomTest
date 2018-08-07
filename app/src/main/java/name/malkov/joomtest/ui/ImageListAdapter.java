package name.malkov.joomtest.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.ViewTarget;

import java.util.ArrayList;
import java.util.List;

import name.malkov.joomtest.R;
import name.malkov.joomtest.network.model.GiphyImage;
import name.malkov.joomtest.network.model.GiphyItem;

public class ImageListAdapter extends RecyclerView.Adapter<ImageListAdapter.ImageViewHolder> {

    private List<GiphyItem> items = new ArrayList<>();
    private int smallOffset;
    private int width;
    private int spans;
    private int elemWidth;

    public ImageListAdapter(int spans, final Context context) {
        smallOffset = context.getResources().getDimensionPixelSize(R.dimen.offset_small);
        this.spans = spans;
        this.width = context.getResources().getDisplayMetrics().widthPixels;
        this.elemWidth = width / spans - spans * (smallOffset + 1);
    }

    public void addItems(List<GiphyItem> is) {
        this.items.addAll(is);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ImageView imageView = new ImageView(viewGroup.getContext());
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        //imageView.setLayoutParams(new RecyclerView.LayoutParams(123,123));
        imageView.setPaddingRelative(smallOffset, smallOffset, smallOffset, smallOffset);
        return new ImageViewHolder(imageView);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder vh, int i) {
        final GiphyImage image = items.get(i).getImages().getFixedWidthPreview();
        final float ratio = elemWidth / image.getWidthPx();
        int elemHeight = (int)(image.getHeightPx() * ratio);
        vh.image.setLayoutParams(new RecyclerView.LayoutParams(elemWidth, elemHeight));
        vh.target = Glide.with(vh.itemView)
                .load(image.getUrl())
                .into(vh.image);
    }

    @Override
    public void onViewRecycled(@NonNull ImageViewHolder vh) {
        if (vh.target != null) {
            Glide.with(vh.itemView).clear(vh.target);
            vh.target = null;
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class ImageViewHolder extends RecyclerView.ViewHolder {

        public ImageView image;

        ViewTarget<ImageView, ?> target;

        ImageViewHolder(final ImageView view) {
            super(view);
            image = view;
        }

    }
}

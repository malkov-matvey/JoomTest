package name.malkov.joomtest.ui;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.ViewTarget;

import java.util.ArrayList;
import java.util.List;

import name.malkov.joomtest.network.model.GiphyImage;
import name.malkov.joomtest.network.model.GiphyItem;

public class ImageListAdapter extends RecyclerView.Adapter<ImageListAdapter.ImageViewHolder> {

    private List<GiphyItem> items = new ArrayList<>();

    public void addItems(List<GiphyItem> is) {
        this.items.addAll(is);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ImageView imageView = new SquaredImageView(viewGroup.getContext());
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));
        return new ImageViewHolder(imageView);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder vh, int i) {
        final GiphyImage image = items.get(i).getImages().getFixedWidthPreview();
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

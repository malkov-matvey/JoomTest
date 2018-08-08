package name.malkov.joomtest.ui.preview;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.customtabs.CustomTabsIntent;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import name.malkov.joomtest.R;
import name.malkov.joomtest.ui.FrescoDrawingProtocol;
import name.malkov.joomtest.viewmodel.ImagesViewModel;
import name.malkov.joomtest.viewmodel.model.Image;
import name.malkov.joomtest.viewmodel.model.ImageItem;
import name.malkov.joomtest.viewmodel.model.User;

public class PreviewFragment extends Fragment {

    private static String ITEM_KEY = "item_key";
    private static String ID_KEY = "id_key";
    private final CompositeDisposable disposable = new CompositeDisposable();
    private final FrescoDrawingProtocol fresco = new FrescoDrawingProtocol();
    private Button gotoProfile;
    private TextView username;
    private TextView displayName;
    private TextView twitter;
    private SimpleDraweeView image;

    public static Fragment newInstanceId(final String id) {
        final PreviewFragment f = new PreviewFragment();
        Bundle bundle = new Bundle(1);
        bundle.putString(ID_KEY, id);
        f.setArguments(bundle);
        return f;
    }

    public static Fragment newInstanceItem(final ImageItem item) {
        final PreviewFragment f = new PreviewFragment();
        Bundle bundle = new Bundle(1);
        bundle.putParcelable(ITEM_KEY, item);
        f.setArguments(bundle);
        return f;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_view_image, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        gotoProfile = view.findViewById(R.id.goto_profile);
        username = view.findViewById(R.id.username);
        displayName = view.findViewById(R.id.displayName);
        twitter = view.findViewById(R.id.twitter);
        image = view.findViewById(R.id.image_content);

        if (getArguments() != null && getArguments().containsKey(ITEM_KEY)) {
            final ImageItem item = getArguments().getParcelable(ITEM_KEY);
            showItem(view.getContext(), item);
        } else if (getArguments() != null && getArguments().containsKey(ID_KEY)) {
            final String id = getArguments().getString(ID_KEY);
            final ImagesViewModel vm = ViewModelProviders.of(this).get(ImagesViewModel.class);
            loadItem(vm, id);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (!disposable.isDisposed()) {
            disposable.dispose();
        }
    }

    private void loadItem(ImagesViewModel vm, String id) {
        disposable.add(vm.bindById(id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(item -> {
                    final Context context = getContext();
                    if (context != null) {
                        showItem(context, item);
                    }
                }));

    }

    private void setTextMaybe(final TextView tv, final String fmt, final String text) {
        if (TextUtils.isEmpty(text)) {
            tv.setVisibility(View.GONE);
        } else {
            tv.setVisibility(View.VISIBLE);
            tv.setText(String.format(fmt, text));
        }
    }

    private void showItem(final Context context, ImageItem item) {
        final User user = item.getUser();
        setTextMaybe(username, context.getString(R.string.fmt_username), user.getUsername());
        setTextMaybe(displayName, context.getString(R.string.fmt_realname), user.getDisplayName());
        setTextMaybe(twitter, context.getString(R.string.fmt_twitter), user.getTwitterName());
        gotoProfile.setOnClickListener(view -> {
            final CustomTabsIntent intent = (new CustomTabsIntent.Builder()).build();
            intent.launchUrl(context, Uri.parse(user.getProfileUrl()));
        });

        final Image preview = item.getPreview();
        if (preview.getWidthPx() != 0 && preview.getHeightPx() != 0) {
            final float ratio = preview.getWidthPx() / (float) preview.getHeightPx();
            image.setAspectRatio(ratio);
        }
        fresco.showThumbAnimation(
                image,
                Uri.parse(item.getThumb().getWebpUrl())
        );
    }
}

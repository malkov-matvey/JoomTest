package name.malkov.joomtest.ui.preview;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.customtabs.CustomTabsIntent;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import name.malkov.joomtest.R;
import name.malkov.joomtest.ui.FrescoDrawingProtocol;
import name.malkov.joomtest.ui.observable.ClickObservable;
import name.malkov.joomtest.viewmodel.ImagesViewModel;
import name.malkov.joomtest.viewmodel.model.Image;
import name.malkov.joomtest.viewmodel.model.ImageItem;
import name.malkov.joomtest.viewmodel.model.User;

public class PreviewActivity extends AppCompatActivity {

    private static String ITEM_KEY = "item_key";
    private final CompositeDisposable disposable = new CompositeDisposable();
    private final FrescoDrawingProtocol fresco = new FrescoDrawingProtocol();
    private Button gotoProfile;
    private TextView username;
    private TextView displayName;
    private TextView twitter;
    private SimpleDraweeView image;
    private TextView retry;
    private View namesRoot;
    private View loader;

    public static Intent prepareOpenIntent(final Context context, final ImageItem item) {
        final Intent intent = new Intent(context, PreviewActivity.class);
        intent.setAction(Intent.ACTION_VIEW);
        intent.putExtra(ITEM_KEY, item);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_image);

        gotoProfile = findViewById(R.id.goto_profile);
        username = findViewById(R.id.username);
        displayName = findViewById(R.id.displayName);
        twitter = findViewById(R.id.twitter);
        image = findViewById(R.id.image_content);
        namesRoot = findViewById(R.id.names_root);
        retry = findViewById(R.id.retry);
        loader = findViewById(R.id.loader);

        checkIntent(getIntent());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (!disposable.isDisposed()) {
            disposable.dispose();
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        checkIntent(intent);
    }

    private void checkIntent(final Intent intent) {
        if (intent != null && Intent.ACTION_VIEW.equals(intent.getAction())) {
            if (intent.hasExtra(ITEM_KEY)) {
                final ImageItem item = intent.getParcelableExtra(ITEM_KEY);
                showItem(this, item);
            } else {
                Uri data = intent.getData();
                if (data != null) {
                    String id = data.getLastPathSegment();
                    final ImagesViewModel vm = ViewModelProviders.of(this).get(ImagesViewModel.class);
                    loadItem(vm, id);
                }
            }
        }
    }

    private void loadItem(final ImagesViewModel vm, final String id) {
        final Observable<Boolean> loadingSignals = ClickObservable.clicks(this.retry, true)
                .doOnNext(disposable -> setUiLoading());
        disposable.add(vm.bindById(id, loadingSignals)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        item -> {
                            if (item.second == ImagesViewModel.CODE_OK) {
                                getIntent().putExtra(ITEM_KEY, item.first);
                                showItem(this, item.first);
                            } else if (item.second == ImagesViewModel.CODE_NOT_FOUND) {
                                setUiError(R.string.not_found_error, false);
                            } else {
                                setUiError(R.string.error_loading_retry, true);
                            }

                        }
                ));

    }

    private void setTextMaybe(final TextView tv, final String fmt, final String text) {
        if (TextUtils.isEmpty(text)) {
            tv.setVisibility(View.GONE);
        } else {
            tv.setVisibility(View.VISIBLE);
            tv.setText(String.format(fmt, text));
        }
    }

    private void showItem(final Context context, final ImageItem item) {
        setUiSuccess();
        final User user = item.getUser();

        if (user != null) {
            setTextMaybe(username, context.getString(R.string.fmt_username), user.getUsername());
            setTextMaybe(displayName, context.getString(R.string.fmt_realname), user.getDisplayName());
            setTextMaybe(twitter, context.getString(R.string.fmt_twitter), user.getTwitterName());

            gotoProfile.setOnClickListener(view -> {
                final CustomTabsIntent intent = (new CustomTabsIntent.Builder()).build();
                intent.launchUrl(context, Uri.parse(user.getProfileUrl()));
            });
        } else {
            gotoProfile.setVisibility(View.GONE);
            namesRoot.setVisibility(View.GONE);
        }

        final Image preview = item.getPreview();
        if (preview.getWidthPx() != 0 && preview.getHeightPx() != 0) {
            final float ratio = preview.getWidthPx() / (float) preview.getHeightPx();
            image.setAspectRatio(ratio);
        }
        fresco.showThumbAnimation(image, Uri.parse(item.getThumb().getWebpUrl()));
    }

    private void setUiSuccess() {
        retry.setVisibility(View.GONE);
        loader.setVisibility(View.GONE);
        namesRoot.setVisibility(View.VISIBLE);
        gotoProfile.setVisibility(View.VISIBLE);
        image.setVisibility(View.VISIBLE);
    }

    private void setUiLoading() {
        retry.setVisibility(View.GONE);
        namesRoot.setVisibility(View.INVISIBLE);
        gotoProfile.setVisibility(View.GONE);
        image.setVisibility(View.GONE);
        loader.setVisibility(View.VISIBLE);
    }

    private void setUiError(int textRes, boolean allowRetry) {
        retry.setText(textRes);
        retry.setClickable(allowRetry);
        retry.setVisibility(View.VISIBLE);
        loader.setVisibility(View.GONE);
        namesRoot.setVisibility(View.GONE);
        gotoProfile.setVisibility(View.GONE);
        image.setVisibility(View.GONE);
    }

}

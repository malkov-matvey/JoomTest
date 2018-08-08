package name.malkov.joomtest.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import name.malkov.joomtest.R;
import name.malkov.joomtest.ui.imagelist.GiphyListFragment;
import name.malkov.joomtest.ui.preview.PreviewFragment;

public class MainActivity extends AppCompatActivity {

    private final String listFragmentTag = GiphyListFragment.class.getSimpleName();
    ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            showListScreen();
            checkIntent(getIntent());
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        checkIntent(intent);
    }

    private void checkIntent(final Intent intent) {
        if (intent != null && Intent.ACTION_VIEW.equals(intent.getAction())) {
            Uri data = intent.getData();
            if (data != null) {
                String id = data.getLastPathSegment();
                showPreview(id);
            }
        }
    }

    private void showListScreen() {
        final FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragmentFrame, GiphyListFragment.newInstance(), listFragmentTag);
        ft.commit();
    }

    private void showPreview(String id) {
        final FragmentManager fm = getSupportFragmentManager();
        final FragmentTransaction ft = fm.beginTransaction();
        final String previewTag = PreviewFragment.class.getSimpleName();
        fm.popBackStack();
        ft.add(R.id.fragmentFrame, PreviewFragment.newInstanceId(id), previewTag);
        if (fm.findFragmentByTag(listFragmentTag) != null) {
            ft.addToBackStack(previewTag);
        }
        ft.commit();
    }

}

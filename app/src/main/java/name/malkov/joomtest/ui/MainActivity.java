package name.malkov.joomtest.ui;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import name.malkov.joomtest.R;
import name.malkov.joomtest.ui.imagelist.GiphyListFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            showListScreen();
        }
    }

    private void showListScreen() {
        final FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragmentFrame, GiphyListFragment.newInstance(), GiphyListFragment.class.getSimpleName());
        ft.commit();
    }
}

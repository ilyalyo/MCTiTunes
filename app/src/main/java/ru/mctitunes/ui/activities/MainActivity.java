package ru.mctitunes.ui.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.mctitunes.Config;
import ru.mctitunes.R;
import ru.mctitunes.entities.MusicTrack;
import ru.mctitunes.ui.adapters.TunesContentAdapter;
import ru.mctitunes.ui.presenters.MainPresenter;
import ru.mctitunes.ui.views.MainView;
import timber.log.Timber;


public class MainActivity extends AppCompatActivity implements MainView {

    MainPresenter presenter;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.material_search_view)
    MaterialSearchView mSearchView;
    @BindView(R.id.main_title_text_view)
    TextView emptyTextView;
    @BindView(R.id.search_recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);

        mSearchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Timber.d(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                Timber.d(query);
                if (query.length() >= Config.START_SEARCH_LENGTH)
                    progressBar.setVisibility(View.VISIBLE);
                else
                    emptyTextView.setText(R.string.main_title);

                presenter.loadMusicTracks(query);
                return false;
            }
        });

        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        presenter = new MainPresenter();
        presenter.bindView(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);

        MenuItem item = menu.findItem(R.id.action_search);
        mSearchView.setMenuItem(item);

        return true;
    }

    @Override
    public void onMusicTracksLoaded(List<MusicTrack> musicTracks, String query) {
        progressBar.setVisibility(View.GONE);

        if (query.length() >= Config.START_SEARCH_LENGTH)
            emptyTextView.setText(R.string.nothing_was_found);
        else
            emptyTextView.setText(R.string.main_title);

        if (musicTracks.size() > 0) {
            emptyTextView.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.VISIBLE);

            TunesContentAdapter contentAdapter = new TunesContentAdapter(MainActivity.this, musicTracks);
            mRecyclerView.setAdapter(contentAdapter);
        } else {
            emptyTextView.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onForbidden() {
        progressBar.setVisibility(View.GONE);
        Toast.makeText(this, "Seems like you access is limited (403 ERROR)", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLoadFailed(Throwable throwable) {
        progressBar.setVisibility(View.GONE);
        Toast.makeText(this, "Oops, Something went wrong..", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        if (mSearchView.isSearchOpen())
            mSearchView.closeSearch();
        else
            super.onBackPressed();
    }

    @Override
    public void onDestroy() {
        if (presenter != null)
            presenter.unbindView(this);
        super.onDestroy();
    }
}

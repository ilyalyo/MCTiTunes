package ru.mctitunes.ui.presenters;

import android.support.annotation.NonNull;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.mctitunes.Config;
import ru.mctitunes.api.ApiModule;
import ru.mctitunes.entities.MusicTrackResponse;
import ru.mctitunes.ui.views.MainView;
import timber.log.Timber;

public class MainPresenter extends BasePresenter<MainView> {

    private long lastRequestTimestamp;

    public void loadMusicTracks(String query) {
        lastRequestTimestamp = System.currentTimeMillis();
        if (query.length() < Config.START_SEARCH_LENGTH) {
            view.onMusicTracksLoaded(new ArrayList<>(), query);
            return;
        }

        final long currentRequestTimestamp = System.currentTimeMillis();
        ApiModule.getInstance().provideApiModule().getMusicTrack(query, Config.NUMBER_OF_RESULTS_IN_SEARCH).enqueue(new Callback<MusicTrackResponse>() {
            @Override
            public void onResponse(@NonNull Call<MusicTrackResponse> call, @NonNull Response<MusicTrackResponse> response) {
                // this way we can ignore late response, if have recent one
                if (lastRequestTimestamp > currentRequestTimestamp)
                    return;
                MusicTrackResponse musicTrackResponse = response.body();
                if (musicTrackResponse != null) {
                    Timber.d(musicTrackResponse.getResultCount().toString());
                    Timber.d(query);
                    view.onMusicTracksLoaded(musicTrackResponse.getResults(), query);
                } else
                    view.onLoadFailed(new Throwable("empty response"));
            }

            @Override
            public void onFailure(@NonNull Call<MusicTrackResponse> call, @NonNull Throwable t) {
                if (lastRequestTimestamp > currentRequestTimestamp)
                    return;
                Timber.d(t.getMessage());
                view.onLoadFailed(t);
            }
        });
    }
}

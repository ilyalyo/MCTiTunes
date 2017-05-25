package ru.mctitunes.ui.presenters;

import android.support.annotation.NonNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.mctitunes.Config;
import ru.mctitunes.api.ApiModule;
import ru.mctitunes.entities.MusicTrackResponse;
import ru.mctitunes.ui.views.MainView;
import timber.log.Timber;

public class MainPresenter extends BasePresenter<MainView> {
    public void loadMusicTracks(String query) {
        ApiModule.getInstance().provideApiModule().getMusicTrack(query, Config.NUMBER_OF_RESULTS_IN_SEARCH).enqueue(new Callback<MusicTrackResponse>() {
            @Override
            public void onResponse(@NonNull Call<MusicTrackResponse> call, @NonNull Response<MusicTrackResponse> response) {
                MusicTrackResponse musicTrackResponse = response.body();
                if (musicTrackResponse != null) {
                    Timber.d(musicTrackResponse.getResultCount().toString());
                    view.onMusicTracksLoaded(musicTrackResponse.getResults(), query);
                } else
                    view.onLoadFailed(new Throwable("empty response"));
            }

            @Override
            public void onFailure(@NonNull Call<MusicTrackResponse> call, @NonNull Throwable t) {
                Timber.d(t.getMessage());
                view.onLoadFailed(t);
            }
        });
    }
}

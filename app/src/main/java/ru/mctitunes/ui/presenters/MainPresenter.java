package ru.mctitunes.ui.presenters;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.mctitunes.api.ApiModule;
import ru.mctitunes.entities.MusicTrackResponse;
import ru.mctitunes.ui.views.MainView;
import timber.log.Timber;

public class MainPresenter extends BasePresenter<MainView> {
    public void loadMusicTracks(String query) {
        ApiModule.getInstance().provideApiModule().getMusicTrack(query, 2).enqueue(new Callback<MusicTrackResponse>() {
            @Override
            public void onResponse(Call<MusicTrackResponse> call, Response<MusicTrackResponse> response) {
                Timber.d(response.body().toString());
                if (response.body() != null)
                    view.onMusicTracksLoaded(response.body().getResults());
                else
                    view.onLoadFailed(new Throwable("empty response"));
            }

            @Override
            public void onFailure(Call<MusicTrackResponse> call, Throwable t) {
                Timber.d(t.getMessage());
                view.onLoadFailed(t);
            }
        });
    }
}

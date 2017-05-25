package ru.mctitunes.ui.views;

import java.util.List;

import ru.mctitunes.entities.MusicTrack;

public interface MainView {
    void onMusicTracksLoaded(List<MusicTrack> musicTracks);

    void onLoadFailed(Throwable throwable);
}

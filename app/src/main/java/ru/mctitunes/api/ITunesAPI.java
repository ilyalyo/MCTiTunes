package ru.mctitunes.api;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import ru.mctitunes.entities.MusicTrackResponse;

public interface ITunesAPI {

    @GET("search?entity=musicTrack")
    Call<MusicTrackResponse> getMusicTrack(@Query("term") String term, @Query("limit") Integer limit);
}

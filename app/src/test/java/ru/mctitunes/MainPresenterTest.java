package ru.mctitunes;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.IOException;
import java.io.InputStream;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;
import ru.mctitunes.api.ApiModule;
import ru.mctitunes.api.ITunesAPI;
import ru.mctitunes.entities.MusicTrack;
import ru.mctitunes.entities.MusicTrackResponse;
import ru.mctitunes.utils.StreamUtils;
import timber.log.Timber;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

/**
 * Created by Ilya on 25.05.2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class MainPresenterTest {

    MockWebServer mockWebServer;
    Call<MusicTrackResponse> call;
    @Before
    public void setUp() {
        mockWebServer = new MockWebServer();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(mockWebServer.url("").toString())
                .addConverterFactory(JacksonConverterFactory.create())
                .build();

        ITunesAPI service = retrofit.create(ITunesAPI.class);

        call = service.getMusicTrack("query", 1);
    }

    @Test
    public void testEmptyResponseParseJson() throws IOException {
        InputStream inputStream = getClass().getResourceAsStream("empty_result.json");
        String mockResponseBody = StreamUtils.convertStreamToString(inputStream);

        mockWebServer.enqueue(new MockResponse().setBody(mockResponseBody));

        MusicTrackResponse response = call.execute().body();
        assertEquals(response.getResultCount(), (Integer) 0);
        assertEquals(response.getResults().size(), 0);

        mockWebServer.shutdown();
    }

    @Test
    public void testResponseParseJson() throws IOException {
        InputStream inputStream = getClass().getResourceAsStream("full_result.json");
        String mockResponseBody = StreamUtils.convertStreamToString(inputStream);

        mockWebServer.enqueue(new MockResponse().setBody(mockResponseBody));

        MusicTrackResponse response = call.execute().body();
        assertEquals(response.getResultCount(), (Integer) 3);
        assertEquals(response.getResults().size(), 3);
        assertEquals(response.getResults().get(0).getArtistName(), "Jack Johnson");
        assertEquals(response.getResults().get(0).getCountry(), "USA");

        mockWebServer.shutdown();
    }
}

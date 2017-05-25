package ru.mctitunes.api;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;
import ru.mctitunes.Config;

public class ApiModule {

    private static volatile ApiModule instance;

    private OkHttpClient client;
    private Retrofit retrofit;

    private ApiModule() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        client = new OkHttpClient.Builder().addInterceptor(logging).build();
    }

    public static synchronized ApiModule getInstance() {
        if (instance == null)
            instance = new ApiModule();
        return instance;
    }

    public ITunesAPI provideApiModule() {
        if (retrofit == null)
            retrofit = new Retrofit.Builder()
                    .baseUrl(Config.ITUNES_API_URL)
                    .addConverterFactory(JacksonConverterFactory.create())
                    .client(client)
                    .build();

        return retrofit.create(ITunesAPI.class);
    }
}

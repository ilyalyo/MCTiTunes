package ru.mctitunes.utils;

import android.content.Context;

import ru.mctitunes.R;
import ru.mctitunes.ui.interfaces.SearchResult;

public class StringUtils {

    public static String getDescriptionForSearchResult(SearchResult searchResult, Context context) {
        return context.getString(R.string.music_track_description, searchResult.getCountry(), searchResult.getPrice());
    }
}

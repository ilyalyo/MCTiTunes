package ru.mctitunes.entities;

import java.util.List;

public class MusicTrackResponse {
    private Integer resultCount;
    private List<MusicTrack> results;

    public List<MusicTrack> getResults() {
        return results;
    }

    public void setResults(List<MusicTrack> results) {
        this.results = results;
    }

    public Integer getResultCount() {
        return resultCount;
    }

    public void setResultCount(Integer resultCount) {
        this.resultCount = resultCount;
    }
}

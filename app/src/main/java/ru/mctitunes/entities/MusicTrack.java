package ru.mctitunes.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import ru.mctitunes.ui.interfaces.SearchResult;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MusicTrack implements SearchResult {
    private String artistName;
    private String trackName;
    private String artworkUrl100;
    private String trackPrice;
    private String country;

    public String getTitle() {
        return artistName + " - " + trackName;
    }

    public String getDescription() {
        return String.format("Country: %s\nPrice is: %s", country, trackPrice);
    }

    public String getAvatar() {
        return artworkUrl100;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getTrackName() {
        return trackName;
    }

    public void setTrackName(String trackName) {
        this.trackName = trackName;
    }

    public String getArtworkUrl100() {
        return artworkUrl100;
    }

    public void setArtworkUrl100(String artworkUrl100) {
        this.artworkUrl100 = artworkUrl100;
    }

    public String getTrackPrice() {
        return trackPrice;
    }

    public void setTrackPrice(String trackPrice) {
        this.trackPrice = trackPrice;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}

package com.riperoni.riperoni;

import android.net.Uri;

import java.io.File;

/**
 * Created by J on 07-Feb-15.
 */
public class Song {
    //Fields
    private String title;
    private String artist;
    private int duration;
    private Uri songUri;

    //Constructor
    public Song(String title, String artist, int duration, Uri songUri){
        this.title = title;
        this.artist = artist;
        this.duration = duration;
        this.songUri = songUri;
    }

    //Getter Methods
    public String getTitle() {
        return title;
    }

    public String getArtist(){
        return artist;
    }

    public int getLength(){
        return duration;
    }

    public Uri getSongUri(){
        return songUri;
    }
}

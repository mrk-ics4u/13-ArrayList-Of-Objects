/*
 * Name:        Lesson 13: ArrayList of Objects (Song.java)
 * Description: The Song class from Lesson 11, complete. Don't change this
 *              file. MusicLibrary.java stores Song objects in an ArrayList.
 * Created by:  Mr Kowalczewski
 * Last edited: 2026-10-13
 */
public class Song {

    private String title;
    private String artist;
    private int lengthSeconds;
    private int plays;

    public Song(String title, String artist, int lengthSeconds) {
        this.title = title;
        this.artist = artist;
        this.lengthSeconds = lengthSeconds;
        this.plays = 0;
    }

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    public int getLengthSeconds() {
        return lengthSeconds;
    }

    public int getPlays() {
        return plays;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void play() {
        plays++;
    }

    public String getLengthText() {
        int minutes = lengthSeconds / 60;
        int seconds = lengthSeconds % 60;
        if (seconds < 10) {
            return minutes + ":0" + seconds;
        }
        return minutes + ":" + seconds;
    }
}

package project.model;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.net.URL;
import java.util.ArrayList;

public enum Music {
//    A_REMARKABLE_MAN(Music.class.getResource("/project/music/A_Remarkable_Man.mp3")),
//    A_REMARKABLE_MAN(Music.class.getResource("/project/music/A_Remarkable_Man.mp3")),
//    A_REMARKABLE_MAN(Music.class.getResource("/project/music/A_Remarkable_Man.mp3")),
//    A_REMARKABLE_MAN(Music.class.getResource("/project/music/A_Remarkable_Man.mp3")),
    A_REMARKABLE_MAN(Music.class.getResource("/project/music/A_Remarkable_Man.mp3"));

    public URL url;
    public Media media;
    public static MediaPlayer mediaPlayer;
    public static ArrayList<Media> playlist = new ArrayList<>();
    public static int counter;

    static {
        playlist.add(A_REMARKABLE_MAN.media);
        counter = 0;
        mediaPlayer = new MediaPlayer(playlist.get(counter));
        mediaPlayer.setOnEndOfMedia(new Runnable() {
            public void run() {
//                if (counter != playlist.size() - 1) {
//                    counter++;
//                    mediaPlayer.medi
//                } else {
//                    counter = 0
//                }
                mediaPlayer.seek(Duration.ZERO);
            }
        });
    }

    Music(URL url) {
        setUrl(url);
        media = new Media(String.valueOf(url));
    }

    public void setUrl(URL url) {
        this.url = url;
    }
}

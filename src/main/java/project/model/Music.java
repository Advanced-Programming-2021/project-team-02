package project.model;

import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import project.model.gui.Icon;

import java.net.URL;
import java.util.ArrayList;

public enum Music {
    RUMBLING(Music.class.getResource("/project/music/Rumbling.mp3")),
    ACCESS_POINT(Music.class.getResource("/project/music/Access_Point.mp3")),
    NEW_HORIZONS(Music.class.getResource("/project/music/New_Horizons.mp3")),
    CHAOS(Music.class.getResource("/project/music/Chaos.mp3")),
    A_REMARKABLE_MAN(Music.class.getResource("/project/music/A_Remarkable_Man.mp3"));

    public URL url;
    public Media media;
    public static MediaPlayer mediaPlayer;
    public static ArrayList<Media> playlist = new ArrayList<>();
    public static int counter;
    public static boolean isMediaPlayerPaused = false;

    static {
        playlist.add(A_REMARKABLE_MAN.media);
        playlist.add(CHAOS.media);
        playlist.add(NEW_HORIZONS.media);
        playlist.add(ACCESS_POINT.media);
        playlist.add(RUMBLING.media);
        counter = 0;
        newMediaPlayer();
    }

    Music(URL url) {
        setUrl(url);
        media = new Media(String.valueOf(url));
    }

    public void setUrl(URL url) {
        this.url = url;
    }

    public static void newMediaPlayer() {
        mediaPlayer = new MediaPlayer(playlist.get(counter));
        mediaPlayer.setAutoPlay(true);
        mediaPlayer.setOnEndOfMedia(() -> {
            if (counter != playlist.size() - 1) counter++;
            else counter = 0;
            newMediaPlayer();
        });
    }

    public static void nextTrack() {
        mediaPlayer.stop();
        if (counter != playlist.size() - 1) counter++;
        else counter = 0;
        newMediaPlayer();
    }

    public static void playPauseMusic(ImageView playPauseMusicButton) {
        if (playPauseMusicButton.getImage().equals(Icon.PAUSE.getImage())) {
            playPauseMusicButton.setImage(Icon.PLAY.getImage());
            Music.mediaPlayer.pause();
            isMediaPlayerPaused = true;
        } else {
            playPauseMusicButton.setImage(Icon.PAUSE.getImage());
            Music.mediaPlayer.play();
            isMediaPlayerPaused = false;
        }
    }

    public static void muteUnmuteMusic(ImageView muteUnmuteButton) {
        if (Music.mediaPlayer.isMute()) {
            muteUnmuteButton.setImage(Icon.UNMUTE.getImage());
            Music.mediaPlayer.setMute(false);
        } else {
            muteUnmuteButton.setImage(Icon.MUTE.getImage());
            Music.mediaPlayer.setMute(true);
        }
    }
}

package project.model;

import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import project.model.gui.Icon;

import java.net.URL;
import java.util.ArrayList;

public enum Music {
    RUMBLING(Music.class.getResource("/project/music/Rumbling.mp3"), "Rok Nardin - The Rumbling"),
    ACCESS_POINT(Music.class.getResource("/project/music/Access_Point.mp3"), "Audiomachine - Access Point"),
    NEW_HORIZONS(Music.class.getResource("/project/music/New_Horizons.mp3"), "The Last Sighs of the Wind - New Horizons"),
    CHAOS(Music.class.getResource("/project/music/Chaos.mp3"), "Rok Nardin - Chaos"),
    A_REMARKABLE_MAN(Music.class.getResource("/project/music/A_Remarkable_Man.mp3"), "Audiomachine - A Remarkable Man");

    public URL url;
    public String name;
    public Media media;
    public static MediaPlayer mediaPlayer;
    public static ArrayList<Media> playlist = new ArrayList<>();
    public static ArrayList<ImageView> muteUnmuteButtons = new ArrayList<>();
    public static int counter;
    public static boolean isMediaPlayerPaused = false;

    static {
        playlist.add(A_REMARKABLE_MAN.media);
        playlist.add(CHAOS.media);
        playlist.add(NEW_HORIZONS.media);
        playlist.add(ACCESS_POINT.media);
        playlist.add(RUMBLING.media);
        counter = 0;
        mediaPlayer = new MediaPlayer(playlist.get(counter));
        mediaPlayer.setAutoPlay(true);
        mediaPlayer.setOnEndOfMedia(() -> {
            if (counter != playlist.size() - 1) counter++;
            else counter = 0;
            newMediaPlayer();
        });
    }

    Music(URL url, String name ) {
        setUrl(url);
        setName(name);
        media = new Media(String.valueOf(url));
    }

    public void setUrl(URL url) {
        this.url = url;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static void newMediaPlayer() {
        if (mediaPlayer.isMute())
            for (ImageView muteUnmuteButton : muteUnmuteButtons) muteUnmuteButton.setImage(Icon.MUTE.getImage());
        mediaPlayer = new MediaPlayer(playlist.get(counter));
        for (ImageView muteUnmuteButton : muteUnmuteButtons)
            if (muteUnmuteButton.getImage().equals(Icon.MUTE.getImage())) mediaPlayer.setMute(true);
        mediaPlayer.setAutoPlay(true);
        mediaPlayer.setOnEndOfMedia(() -> {
            if (counter != playlist.size() - 1) counter++;
            else counter = 0;
            newMediaPlayer();
        });
    }

    public static void nextTrack(ImageView playPauseMusicButton, ImageView muteUnmuteButton) {
        mediaPlayer.stop();
        if (counter != playlist.size() - 1) counter++;
        else counter = 0;
        newMediaPlayer();
        isMediaPlayerPaused = false;
        playPauseMusicButton.setImage(Icon.PAUSE.getImage());
        if (Music.mediaPlayer.isMute()) muteUnmuteButton.setImage(Icon.MUTE.getImage());
        else muteUnmuteButton.setImage(Icon.UNMUTE.getImage());
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

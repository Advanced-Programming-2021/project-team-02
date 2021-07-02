package project.model.gui;

import javafx.scene.image.Image;

import java.net.URL;

public enum Icon {
    PREVIOUS(Icon.class.getResource("/project/image/icon/previous.png")),
    MUTE(Icon.class.getResource("/project/image/icon/mute.png")),
    UNMUTE(Icon.class.getResource("/project/image/icon/unmute.png")),
    CAMERA(Icon.class.getResource("/project/image/icon/camera.png")),
    EDIT(Icon.class.getResource("/project/image/icon/edit.png")),
    MAIN_MENU(Icon.class.getResource("/project/image/icon/main_menu.png")),
    PAUSE(Icon.class.getResource("/project/image/icon/pause.png")),
    PLAY(Icon.class.getResource("/project/image/icon/play.png")),
    YU_GI_OH(Icon.class.getResource("/project/image/icon/Yu-Gi-Oh.jpg")),
    YU_GI_OH_TEXT(Icon.class.getResource("/project/image/icon/YuGiOhText.png"));
    public URL url;
    public Image image;

    Icon(URL url) {
        setUrl(url);
        image = new Image(String.valueOf(url));
    }

    public void setUrl(URL url) {
        this.url = url;
    }

    public URL getUrl() {
        return url;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public Image getImage() {
        return image;
    }
}

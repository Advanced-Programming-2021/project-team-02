package project.model;

import java.net.URL;

public enum Avatar {
    AVATAR_1(Avatar.class.getResource("/project/image/ProfileMenuPictures/1.jpg")),
    AVATAR_2(Avatar.class.getResource("/project/image/ProfileMenuPictures/2.jpg")),
    AVATAR_3(Avatar.class.getResource("/project/image/ProfileMenuPictures/3.jpg")),
    AVATAR_4(Avatar.class.getResource("/project/image/ProfileMenuPictures/4.jpg"));

    private URL url;

    Avatar(URL url) {
        setUrl(url);
    }

    public void setUrl(URL url) {
        this.url = url;
    }

    public URL getUrl() {
        return url;
    }
}

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

    public URL getUrl() {
        return url;
    }

    public void setUrl(URL url) {
        this.url = url;
    }

    public static URL getAvatarByNumber(int number) {
        switch (number) {
            case 2:
                return AVATAR_2.getUrl();
            case 3:
                return AVATAR_3.getUrl();
            case 4:
                return AVATAR_4.getUrl();
            default:
                return AVATAR_1.getUrl();
        }
    }
}

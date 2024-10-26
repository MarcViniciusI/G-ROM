package Modelos;

public class CharacterInfo {
    private final String name;
    private final String photoPath;

    public CharacterInfo(String name, String photoPath) {
        this.name = name;
        this.photoPath = photoPath;
    }

    public String getName() {
        return name;
    }

    public String getPhotoPath() {
        return photoPath;
    }
}

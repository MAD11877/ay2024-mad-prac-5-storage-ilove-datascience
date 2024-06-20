package sg.edu.np.mad.madpractical5;

public class User {
    private int id;
    private String name;
    private String description;
    private boolean followed;

    public User(int id, String name, String description, boolean followed) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.followed = followed;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isFollowed() {
        return followed;
    }

    public void setFollowed(boolean followed) {
        this.followed = followed;
    }
}

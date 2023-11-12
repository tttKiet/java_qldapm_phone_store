package common;

public class Category {
    private int idCategory;
    private String name;
    private String description;

    public Category() {
        this.name = new String();
        this.description = new String();
    }

    public Category(String name, String description) {
        this.name = new String(name);
        this.description = new String(description);
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String toString() {
        return "[name: " + name + ", description: " + description +
                " ]";
    }

}

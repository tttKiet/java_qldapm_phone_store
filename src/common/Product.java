package common;

public class Product {
    private int idProduct;
    private String name;
    private String description;
    private String price;
    private int quantity;
    private String hardWare;
    private int idCategory;

    public Product(String name, String description, String price, int quantity, String hardWare, int idCategory) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.hardWare = hardWare;
        this.idCategory = idCategory;
    }

    public Product() {
        this.name = new String();
        this.description = new String();
        this.price = new String();
        this.quantity = 0;
        this.hardWare = new String();
    }

    public String getName() {
        return name;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public String getDescription() {
        return description;
    }

    public String getPrice() {
        return price;
    }

    public String gethHardWare() {
        return hardWare;
    }

    public int getiIdCategory() {
        return idCategory;
    }

    public String toString() {
        return "[name: " + name + ", description: " + description +
                ", hardWare: " + hardWare + ", price: " + price + ", quantity: " + quantity + ", idCategory: "
                + idCategory + " ]\n";
    }

}

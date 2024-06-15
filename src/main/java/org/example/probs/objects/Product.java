package org.example.probs.objects;
public class Product {

    private int id_product;
    private String name;
    private Float price;

    Product() {
        id_product = 0;
        name = "unknown";
        price = Float.valueOf(0);
    }

   public Product(String name,Float price) {
        this.price = price;
        this.name = name;
    }

    public Product(int id_product, String name, Float price) {
        this.id_product = id_product;
        this.name = name;
        this.price = price;
    }

    public void setId_product(int id_product) {
        this.id_product = id_product;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public int getId_product() {
        return id_product;
    }

    public String getName() {
        return name;
    }

    public Float getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "Id_product: " + id_product + "\n Name product: " + name;
    }
}

public class Product {

    private final String name;
    private final String category;
    private final long price;

    public Product(String name, String category, long price) {
        this.name = name;
        this.category = category;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public long getPrice() {
        return price;
    }
}

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Cart {

    private final ArrayList<CartItem> items;

    public Cart() {
        items = new ArrayList<>();
    }

    // Menambahkan produk ke keranjang
    public void addProduct(Product product) {

        for (CartItem item : items) {

            if (item.getProduct() == product) {
                item.increaseQuantity();
                return;
            }
        }

        items.add(new CartItem(product));
    }

    // Menghapus produk berdasarkan indeks
    public void removeProduct(int index) {

        if (index >= 0 && index < items.size()) {
            items.remove(index);
        }
    }

    // Mengambil seluruh item keranjang
    public List<CartItem> getItems() {
        return Collections.unmodifiableList(items);
    }

    // Menghitung total pembayaran
    public long getTotal() {

        long total = 0;

        for (CartItem item : items) {
            total += item.getSubtotal();
        }

        return total;
    }

    // Menghitung jumlah seluruh produk
    public int getTotalItems() {

        int total = 0;

        for (CartItem item : items) {
            total += item.getQuantity();
        }

        return total;
    }

    // Memeriksa keranjang kosong
    public boolean isEmpty() {
        return items.isEmpty();
    }

    // Mengosongkan keranjang
    public void clear() {
        items.clear();
    }
}

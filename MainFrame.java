import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class MainFrame extends JFrame {

    private static final long serialVersionUID = 1L;
    private final Color DARK = new Color(55, 40, 32);
    private final Color CREAM = new Color(250, 246, 239);

    private final Cart cart;

    private MenuPanel menuPanel;
    private CartPanel cartPanel;

    public MainFrame() {

        setTitle("CafeQueue - Self Order Cafe");

        setSize(1150, 750);

        setMinimumSize(new Dimension(950, 650));

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLocationRelativeTo(null);

        cart = new Cart();

        initializeGUI();
    }

    // Mengatur tampilan utama
    private void initializeGUI() {

        JPanel root = new JPanel(
                new BorderLayout(18, 18)
        );

        root.setBackground(CREAM);

        root.setBorder(BorderFactory.createEmptyBorder(
                20, 22, 20, 22
        ));

        root.add(createHeader(), BorderLayout.NORTH);

        JPanel content = new JPanel(
                new GridLayout(1, 2, 18, 0)
        );

        content.setOpaque(false);

        menuPanel = new MenuPanel(
                createProducts(),
                this::addProductToCart
        );

        cartPanel = new CartPanel(cart);

        content.add(menuPanel);
        content.add(cartPanel);

        root.add(content, BorderLayout.CENTER);

        setContentPane(root);
    }

    // Membuat header aplikasi
    private JPanel createHeader() {

        JPanel header = new JPanel(
                new BorderLayout()
        );

        header.setBackground(DARK);

        header.setBorder(BorderFactory.createEmptyBorder(
                18, 22, 18, 22
        ));

        JLabel title = new JLabel("CAFEQUEUE");

        title.setForeground(Color.WHITE);

        title.setFont(new Font(
                "SansSerif", Font.BOLD, 27
        ));

        JLabel subtitle = new JLabel(
                "Your daily coffee, made better."
        );

        subtitle.setForeground(
                new Color(225, 207, 187)
        );

        subtitle.setFont(new Font(
                "SansSerif", Font.PLAIN, 13
        ));

        JPanel titlePanel = new JPanel(
                new GridLayout(2, 1, 0, 5)
        );

        titlePanel.setOpaque(false);

        titlePanel.add(title);
        titlePanel.add(subtitle);

        JLabel status = new JLabel("  OPEN  ");

        status.setForeground(
                new Color(220, 245, 210)
        );

        status.setFont(new Font(
                "SansSerif", Font.BOLD, 12
        ));

        header.add(titlePanel, BorderLayout.WEST);

        header.add(status, BorderLayout.EAST);

        return header;
    }

    // Menyiapkan data produk
    private List<Product> createProducts() {

        List<Product> products = new ArrayList<>();

        // Coffee
        products.add(new Product(
                "Espresso", "Coffee", 15000
        ));

        products.add(new Product(
                "Americano", "Coffee", 18000
        ));

        products.add(new Product(
                "Cafe Latte", "Coffee", 23000
        ));

        products.add(new Product(
                "Caramel Latte", "Coffee", 27000
        ));

        products.add(new Product(
                "Cappuccino", "Coffee", 25000
        ));

        // Non-Coffee
        products.add(new Product(
                "Matcha Latte", "Non-Coffee", 25000
        ));

        products.add(new Product(
                "Chocolate", "Non-Coffee", 22000
        ));

        products.add(new Product(
                "Red Velvet", "Non-Coffee", 24000
        ));

        // Food
        products.add(new Product(
                "Croissant", "Food", 18000
        ));

        products.add(new Product(
                "Cheesecake", "Food", 25000
        ));

        products.add(new Product(
                "Chocolate Muffin", "Food", 17000
        ));

        return products;
    }

    // Menambahkan produk ke keranjang
    private void addProductToCart(Product product) {

        cart.addProduct(product);

        cartPanel.refreshCart();
    }
}

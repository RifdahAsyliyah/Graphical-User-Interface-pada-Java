import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.function.Consumer;

public class MenuPanel extends JPanel {

    private static final long serialVersionUID = 1L;
    private final Color DARK = new Color(55, 40, 32);
    private final Color BROWN = new Color(121, 82, 56);
    private final Color CREAM = new Color(250, 246, 239);
    private final Color BEIGE = new Color(237, 225, 209);

    private final List<Product> products;
    private final Consumer<Product> addToCartAction;

    private final JPanel menuGrid;
    private final JPanel categoryPanel;

    private String selectedCategory = "All";

    public MenuPanel(
            List<Product> products,
            Consumer<Product> addToCartAction) {

        this.products = products;
        this.addToCartAction = addToCartAction;

        setLayout(new BorderLayout(0, 15));
        setBackground(Color.WHITE);

        setBorder(BorderFactory.createEmptyBorder(
                18, 18, 18, 18
        ));

        JLabel title = new JLabel("Explore Our Menu");

        title.setFont(new Font(
                "SansSerif", Font.BOLD, 21
        ));

        title.setForeground(DARK);

        categoryPanel = new JPanel(
                new FlowLayout(FlowLayout.LEFT, 7, 5)
        );

        categoryPanel.setOpaque(false);

        String[] categories = {
                "All", "Coffee", "Non-Coffee", "Food"
        };

        for (String category : categories) {

            JButton button = new JButton(category);

            button.setFocusPainted(false);

            button.setFont(new Font(
                    "SansSerif", Font.BOLD, 11
            ));

            button.setBorder(BorderFactory.createEmptyBorder(
                    9, 12, 9, 12
            ));

            button.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {

                    selectedCategory = category;

                    updateCategoryButtons();
                    refreshMenu();
                }
            });

            categoryPanel.add(button);
        }

        JPanel topPanel = new JPanel(
                new BorderLayout(0, 12)
        );

        topPanel.setOpaque(false);

        topPanel.add(title, BorderLayout.NORTH);
        topPanel.add(categoryPanel, BorderLayout.CENTER);

        add(topPanel, BorderLayout.NORTH);

        menuGrid = new JPanel(
                new GridLayout(0, 2, 12, 12)
        );

        menuGrid.setBackground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(menuGrid);

        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        scrollPane.getVerticalScrollBar().setUnitIncrement(15);

        add(scrollPane, BorderLayout.CENTER);

        updateCategoryButtons();
        refreshMenu();
    }

    private void updateCategoryButtons() {

        for (Component component :
                categoryPanel.getComponents()) {

            if (component instanceof JButton) {

                JButton button = (JButton) component;

                boolean active = button.getText().equals(
                        selectedCategory
                );

                button.setBackground(active ? BROWN : BEIGE);

                button.setForeground(
                        active ? Color.WHITE : DARK
                );
            }
        }
    }

    private void refreshMenu() {

        menuGrid.removeAll();

        for (Product product : products) {

            if (!selectedCategory.equals("All")
                    && !product.getCategory().equals(
                            selectedCategory)) {

                continue;
            }

            menuGrid.add(createProductCard(product));
        }

        menuGrid.revalidate();
        menuGrid.repaint();
    }

    private JPanel createProductCard(Product product) {

        JPanel card = new JPanel(
                new BorderLayout(0, 10)
        );

        card.setBackground(CREAM);

        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BEIGE),
                BorderFactory.createEmptyBorder(
                        13, 13, 13, 13
                )
        ));

        JLabel category = new JLabel(
                product.getCategory()
        );

        category.setForeground(BROWN);

        category.setFont(new Font(
                "SansSerif", Font.PLAIN, 11
        ));

        JLabel name = new JLabel(
                product.getName()
        );

        name.setForeground(DARK);

        name.setFont(new Font(
                "SansSerif", Font.BOLD, 15
        ));

        JLabel price = new JLabel(
                formatRupiah(product.getPrice())
        );

        price.setForeground(BROWN);

        price.setFont(new Font(
                "SansSerif", Font.BOLD, 14
        ));

        JButton addButton = new JButton("+ Add to Cart");

        addButton.setBackground(BROWN);
        addButton.setForeground(Color.WHITE);
        addButton.setFocusPainted(false);

        addButton.addActionListener(e -> {
            addToCartAction.accept(product);
        });

        JPanel info = new JPanel(
                new GridLayout(3, 1, 0, 5)
        );

        info.setOpaque(false);

        info.add(category);
        info.add(name);
        info.add(price);

        card.add(info, BorderLayout.CENTER);
        card.add(addButton, BorderLayout.SOUTH);

        return card;
    }

    private String formatRupiah(long amount) {

        return "Rp " + String.format(
                "%,d", amount
        ).replace(',', '.');
    }
}

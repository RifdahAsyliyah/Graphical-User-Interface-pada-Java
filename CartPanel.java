
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

public class CartPanel extends JPanel {

    private static final long serialVersionUID = 1L;

    private final Color DARK = new Color(55, 40, 32);
    private final Color BROWN = new Color(121, 82, 56);
    private final Color CREAM = new Color(250, 246, 239);
    private final Color BEIGE = new Color(237, 225, 209);

    private final Cart cart;

    private DefaultTableModel tableModel;
    private JTable cartTable;

    private JLabel totalLabel;
    private JLabel itemCountLabel;
    private JLabel changeLabel;

    private JTextField paymentField;

    public CartPanel(Cart cart) {

        this.cart = cart;

        setLayout(new BorderLayout(0, 13));
        setBackground(Color.WHITE);

        setBorder(BorderFactory.createEmptyBorder(
                18, 18, 18, 18
        ));

        initializeCartTable();
        initializePaymentPanel();

        refreshCart();
    }

    // Membuat tabel keranjang
    private void initializeCartTable() {

        JPanel heading = new JPanel(new BorderLayout());

        heading.setOpaque(false);

        JLabel title = new JLabel("Your Order");

        title.setFont(new Font(
                "SansSerif", Font.BOLD, 21
        ));

        title.setForeground(DARK);

        itemCountLabel = new JLabel("0 items");
        itemCountLabel.setForeground(BROWN);

        heading.add(title, BorderLayout.WEST);
        heading.add(itemCountLabel, BorderLayout.EAST);

        add(heading, BorderLayout.NORTH);

        String[] columns = {
                "Product", "Price", "Qty", "Subtotal"
        };

        tableModel = new DefaultTableModel(columns, 0) {

            private static final long serialVersionUID = 1L;

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        cartTable = new JTable(tableModel);

        cartTable.setRowHeight(32);

        cartTable.setFont(new Font(
                "SansSerif", Font.PLAIN, 11
        ));

        cartTable.getTableHeader().setFont(new Font(
                "SansSerif", Font.BOLD, 11
        ));

        cartTable.getTableHeader().setBackground(BEIGE);

        cartTable.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );

        JScrollPane scrollPane = new JScrollPane(cartTable);

        scrollPane.setBorder(
                BorderFactory.createLineBorder(BEIGE)
        );

        JButton removeButton = new JButton(
                "Remove Selected Item"
        );

        removeButton.setForeground(
                new Color(160, 55, 45)
        );

        removeButton.addActionListener(new RemoveListener());

        JPanel centerPanel = new JPanel(new BorderLayout(0, 8));

        centerPanel.setOpaque(false);

        centerPanel.add(removeButton, BorderLayout.NORTH);
        centerPanel.add(scrollPane, BorderLayout.CENTER);

        add(centerPanel, BorderLayout.CENTER);
    }

    // Membuat panel pembayaran
    private void initializePaymentPanel() {

        JPanel panel = new JPanel(
                new GridLayout(0, 1, 0, 9)
        );

        panel.setBackground(CREAM);

        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BEIGE),
                BorderFactory.createEmptyBorder(
                        14, 14, 14, 14
                )
        ));

        JLabel totalTitle = new JLabel("Total Payment");

        totalLabel = new JLabel("Rp 0");

        totalLabel.setForeground(BROWN);

        totalLabel.setFont(new Font(
                "SansSerif", Font.BOLD, 23
        ));

        JLabel paymentTitle = new JLabel(
                "Cash Received (Rp)"
        );

        paymentField = new JTextField();

        paymentField.setFont(new Font(
                "SansSerif", Font.PLAIN, 14
        ));

        paymentField.setToolTipText(
                "Enter payment amount"
        );

        JLabel changeTitle = new JLabel("Change");

        changeLabel = new JLabel("Rp 0");

        changeLabel.setForeground(BROWN);

        changeLabel.setFont(new Font(
                "SansSerif", Font.BOLD, 16
        ));

        // Listener untuk menghitung kembalian secara otomatis
        paymentField.getDocument().addDocumentListener(
                new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent e) {
                updateChange();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateChange();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateChange();
            }
        });

        JButton checkoutButton = new JButton("CHECKOUT");

        checkoutButton.setBackground(BROWN);
        checkoutButton.setForeground(Color.WHITE);

        checkoutButton.setFont(new Font(
                "SansSerif", Font.BOLD, 14
        ));

        checkoutButton.setFocusPainted(false);

        checkoutButton.addActionListener(
                new CheckoutListener()
        );

        panel.add(totalTitle);
        panel.add(totalLabel);
        panel.add(paymentTitle);
        panel.add(paymentField);
        panel.add(changeTitle);
        panel.add(changeLabel);
        panel.add(checkoutButton);

        add(panel, BorderLayout.SOUTH);
    }

    // Menghitung kembalian secara otomatis
    private void updateChange() {

        if (cart.isEmpty()) {
            changeLabel.setText("Rp 0");
            return;
        }

        String input = paymentField.getText().trim();

        if (!input.matches("[0-9]+")) {
            changeLabel.setText("Rp 0");
            return;
        }

        try {

            long payment = Long.parseLong(input);
            long total = cart.getTotal();

            if (payment >= total) {

                long change = payment - total;

                changeLabel.setText(
                        formatRupiah(change)
                );

            } else {

                changeLabel.setText("Rp 0");
            }

        } catch (NumberFormatException e) {

            changeLabel.setText("Rp 0");
        }
    }

    // Memperbarui tampilan keranjang
    public void refreshCart() {

        tableModel.setRowCount(0);

        for (CartItem item : cart.getItems()) {

            tableModel.addRow(new Object[] {
                    item.getProduct().getName(),

                    formatRupiah(
                            item.getProduct().getPrice()
                    ),

                    item.getQuantity(),

                    formatRupiah(item.getSubtotal())
            });
        }

        totalLabel.setText(
                formatRupiah(cart.getTotal())
        );

        itemCountLabel.setText(
                cart.getTotalItems() + " items"
        );

        updateChange();

        cartTable.clearSelection();
    }

    // Menghapus item yang dipilih
    private void removeSelectedItem() {

        int selectedRow = cartTable.getSelectedRow();

        if (selectedRow == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select an item to remove.",
                    "No Item Selected",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        cart.removeProduct(selectedRow);

        refreshCart();
    }

    // Memproses pembayaran
    private void processCheckout() {

        if (cart.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Your cart is still empty!",
                    "Empty Cart",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        String input = paymentField.getText().trim();

        if (!input.matches("[0-9]+")) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter a valid payment amount.",
                    "Invalid Payment",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }

        long payment;

        try {

            payment = Long.parseLong(input);

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Payment amount is too large.",
                    "Invalid Payment",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }

        long total = cart.getTotal();

        if (payment < total) {

            JOptionPane.showMessageDialog(
                    this,
                    "Insufficient payment!\n"
                    + "Please enter at least "
                    + formatRupiah(total),
                    "Payment Failed",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        long change = payment - total;

        changeLabel.setText(
                formatRupiah(change)
        );

        // Menampilkan struk
        ReceiptDialog.showReceipt(
                this, cart, payment
        );

        // Mengosongkan transaksi setelah checkout
        cart.clear();

        paymentField.setText("");

        refreshCart();
    }

    // Format mata uang Rupiah
    private String formatRupiah(long amount) {

        return "Rp " + String.format(
                "%,d", amount
        ).replace(',', '.');
    }

    // Event Listener untuk tombol hapus
    private class RemoveListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            removeSelectedItem();
        }
    }

    // Event Listener untuk tombol checkout
    private class CheckoutListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            processCheckout();
        }
    }

    // Mengakses field pembayaran
    public JTextField getPaymentField() {
        return paymentField;
    }
}

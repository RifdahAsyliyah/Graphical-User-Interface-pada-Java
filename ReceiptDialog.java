import javax.swing.*;
import java.awt.*;
import java.util.Date;

public class ReceiptDialog {

    public static void showReceipt(
            Component parent,
            Cart cart,
            long payment) {

        long total = cart.getTotal();
        long change = payment - total;

        StringBuilder receipt = new StringBuilder();

        receipt.append("================================\n");
        receipt.append("          CAFEQUEUE             \n");
        receipt.append("     Your daily coffee          \n");
        receipt.append("================================\n");

        receipt.append("Date: ")
                .append(new Date())
                .append("\n");

        receipt.append("--------------------------------\n");

        for (CartItem item : cart.getItems()) {

            receipt.append(item.getProduct().getName())
                    .append("\n");

            receipt.append(item.getQuantity())
                    .append(" x ")
                    .append(formatRupiah(
                            item.getProduct().getPrice()
                    ))
                    .append(" = ")
                    .append(formatRupiah(
                            item.getSubtotal()
                    ))
                    .append("\n");
        }

        receipt.append("--------------------------------\n");

        receipt.append("TOTAL  : ")
                .append(formatRupiah(total))
                .append("\n");

        receipt.append("PAID   : ")
                .append(formatRupiah(payment))
                .append("\n");

        receipt.append("CHANGE : ")
                .append(formatRupiah(change))
                .append("\n");

        receipt.append("================================\n");
        receipt.append("     THANK YOU FOR ORDERING!    \n");
        receipt.append("================================\n");

        JTextArea receiptArea = new JTextArea(
                receipt.toString()
        );

        receiptArea.setEditable(false);
        receiptArea.setFont(new Font(
                "Monospaced", Font.PLAIN, 12
        ));

        JOptionPane.showMessageDialog(
                parent,
                new JScrollPane(receiptArea),
                "Payment Successful - Receipt",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    private static String formatRupiah(long amount) {

        return "Rp " + String.format(
                "%,d", amount
        ).replace(',', '.');
    }
}

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PizzaGUIFrame extends JFrame {
    private JRadioButton thinCrust, regularCrust, deepDishCrust;
    private JComboBox<String> sizeComboBox;
    private JCheckBox[] toppingsCheckBoxes;
    private JTextArea orderTextArea;
    private JButton orderButton, clearButton, quitButton;

    public PizzaGUIFrame() {
        setTitle("Pizza Order Form");
        setSize(400, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel crustPanel = new JPanel();
        crustPanel.setBorder(BorderFactory.createTitledBorder("Crust Type"));
        thinCrust = new JRadioButton("Thin");
        regularCrust = new JRadioButton("Regular");
        deepDishCrust = new JRadioButton("Deep-dish");
        ButtonGroup crustGroup = new ButtonGroup();
        crustGroup.add(thinCrust);
        crustGroup.add(regularCrust);
        crustGroup.add(deepDishCrust);
        crustPanel.add(thinCrust);
        crustPanel.add(regularCrust);
        crustPanel.add(deepDishCrust);

        JPanel sizePanel = new JPanel();
        sizePanel.setBorder(BorderFactory.createTitledBorder("Size"));
        String[] sizes = {"Small", "Medium", "Large", "Super"};
        sizeComboBox = new JComboBox<>(sizes);
        sizePanel.add(sizeComboBox);

        JPanel toppingsPanel = new JPanel();
        toppingsPanel.setBorder(BorderFactory.createTitledBorder("Toppings"));
        String[] toppings = {"Pepperoni", "Mushrooms", "Onions", "Sausage", "Bacon", "Extra Cheese"};
        toppingsCheckBoxes = new JCheckBox[toppings.length];
        for (int i = 0; i < toppings.length; i++) {
            toppingsCheckBoxes[i] = new JCheckBox(toppings[i]);
            toppingsPanel.add(toppingsCheckBoxes[i]);
        }

        JPanel orderPanel = new JPanel();
        orderPanel.setBorder(BorderFactory.createTitledBorder("Order Summary"));
        orderTextArea = new JTextArea(10, 30);
        orderTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(orderTextArea);
        orderPanel.add(scrollPane);

        JPanel buttonPanel = new JPanel();
        orderButton = new JButton("Order");
        clearButton = new JButton("Clear");
        quitButton = new JButton("Quit");
        buttonPanel.add(orderButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(quitButton);

        add(crustPanel, BorderLayout.NORTH);
        add(sizePanel, BorderLayout.CENTER);
        add(toppingsPanel, BorderLayout.EAST);
        add(orderPanel, BorderLayout.WEST);
        add(buttonPanel, BorderLayout.SOUTH);

        orderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                compileOrder();
            }
        });

        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearForm();
            }
        });

        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int response = JOptionPane.showConfirmDialog(null, "Are you sure you want to quit?", "Confirm", JOptionPane.YES_NO_OPTION);
                if (response == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });
    }

    private void compileOrder() {
        String crust = "";
        if (thinCrust.isSelected()) crust = "Thin";
        else if (regularCrust.isSelected()) crust = "Regular";
        else if (deepDishCrust.isSelected()) crust = "Deep-dish";

        String size = (String) sizeComboBox.getSelectedItem();
        double baseCost = 0;
        switch (size) {
            case "Small":
                baseCost = 8.00;
                break;
            case "Medium":
                baseCost = 12.00;
                break;
            case "Large":
                baseCost = 16.00;
                break;
            case "Super":
                baseCost = 20.00;
                break;
        }

        StringBuilder toppings = new StringBuilder();
        double toppingsCost = 0;
        for (JCheckBox checkBox : toppingsCheckBoxes) {
            if (checkBox.isSelected()) {
                toppings.append(checkBox.getText()).append("\n");
                toppingsCost += 1.00;
            }
        }

        double subTotal = baseCost + toppingsCost;
        double tax = subTotal * 0.07;
        double total = subTotal + tax;

        orderTextArea.setText(String.format(
                "=========================================\n" +
                        "Type of Crust & Size\t\tPrice\n" +
                        "%s %s\t\t$%.2f\n" +
                        "Ingredients\t\tPrice\n" +
                        "%s\t\t$%.2f\n" +
                        "Sub-total:\t\t$%.2f\n" +
                        "Tax:\t\t$%.2f\n" +
                        "-----------------------------------------\n" +
                        "Total:\t\t$%.2f\n" +
                        "=========================================",
                crust, size, baseCost, toppings.toString(), toppingsCost, subTotal, tax, total
        ));
    }

    private void clearForm() {
        thinCrust.setSelected(false);
        regularCrust.setSelected(false);
        deepDishCrust.setSelected(false);
        sizeComboBox.setSelectedIndex(0);
        for (JCheckBox checkBox : toppingsCheckBoxes) {
            checkBox.setSelected(false);
        }
        orderTextArea.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new PizzaGUIFrame().setVisible(true);
            }
        });
    }
}
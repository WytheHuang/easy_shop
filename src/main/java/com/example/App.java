package com.example;

import java.awt.event.KeyListener;
import java.awt.Font;
import java.awt.BorderLayout;
import java.awt.Component;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import org.json.JSONObject;

public class App {
    private static JSONObject data;
    private static List<Item> items = new ArrayList<Item>();

    private static JFrame window;

    private static JPanel user_info_panel;
    private static JLabel user_info_label;
    private static JLabel user_money_label;
    private static JTextField user_money;
    private static JButton user_add_money_button;
    private static JPanel user_add_money_panel;
    private static JLabel user_add_money_panel_label;
    private static JLabel user_add_money_input_label;
    private static JTextField user_add_money_input;
    private static JButton user_add_money_submit_button;

    private static JPanel panel_selecter;
    private static JLabel panel_label;
    private static JButton append_button;
    private static JButton shop_button;
    // private static JButton black_list_button;

    private static JPanel main_panel;

    private static JPanel append_panel;
    private static JLabel append_label;
    private static JLabel append_item_label;
    private static JTextField append_item;
    private static JLabel append_money_label;
    private static JTextField append_money;
    private static JLabel append_item_category_label;
    private static JComboBox<String> append_item_category_comboBox;
    private static JButton append_submit_button;

    private static JPanel shop_panel;
    private static JLabel shop_label;
    private static JList<Item> shop_item_list;
    private static JButton shop_buy_button;
    // private static JButton shop_add_black_list_button;

    private static long user_money_amount = Config.user_money_init;

    private static void read_data() throws Exception {
        data = Utils.read_json(new URI(String.format("file:%s", Config.data_path)));
        for (String key : data.keySet()) {
            items.add(new Item(key, data.getJSONObject(key)));
        }
        items.sort(new Comparator<Item>() {
            @Override
            public int compare(Item a, Item b) {
                return a.category.compareTo(b.category);
            }
        });
    }

    public static void main(String[] args) throws Exception {
        read_data();

        window = new JFrame();
        window.setSize(500, 250);
        window.setLayout(new BorderLayout());
        window.setTitle("Shopping");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        main_panel = new JPanel();

        user_info_panel = new JPanel();
        user_info_panel.setLayout(new BoxLayout(user_info_panel, BoxLayout.X_AXIS));
        user_info_label = new JLabel("[User Info]");
        user_money_label = new JLabel("Money: $");
        user_money = new JTextField(10);
        user_money.setEditable(false);
        user_money.setText(Long.toString(user_money_amount));
        user_add_money_button = new JButton("Add Money");
        user_info_panel.add(user_info_label);
        user_info_panel.add(user_money_label);
        user_info_panel.add(user_money);
        user_info_panel.add(user_add_money_button);

        user_add_money_panel = new JPanel();
        user_add_money_panel.setLayout(new BoxLayout(user_add_money_panel, BoxLayout.Y_AXIS));
        user_add_money_panel_label = new JLabel("Add Money");
        user_add_money_input_label = new JLabel("Amount");
        user_add_money_input = new JTextField(25);
        user_add_money_input.setEditable(true);
        user_add_money_input.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(java.awt.event.KeyEvent e) {
                if (e.getKeyChar() == '\n') {
                    user_add_money_submit_button.doClick();
                }
            }

            @Override
            public void keyPressed(java.awt.event.KeyEvent e) {}

            @Override
            public void keyReleased(java.awt.event.KeyEvent e) {}
        });
        user_add_money_submit_button = new JButton("Submit");
        user_add_money_submit_button.addActionListener(l -> {
            try {
                String input = user_add_money_input.getText();
                if (input.length() == 0) {
                    JOptionPane.showMessageDialog(null, "Please input amount", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                long amount = Long.parseLong(user_add_money_input.getText());
                if (amount < 0) {
                    JOptionPane.showMessageDialog(null, "Amount must be positive", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                user_money_amount += amount;
                user_money.setText(Long.toString(user_money_amount));
                user_add_money_input.setText("");
                shop_button.doClick();
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Amount must be integer", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        user_add_money_panel.add(user_add_money_panel_label);
        user_add_money_panel.add(user_add_money_input_label);
        user_add_money_panel.add(user_add_money_input);
        user_add_money_panel.add(user_add_money_submit_button);

        panel_selecter = new JPanel();
        panel_selecter.setLayout(new BoxLayout(panel_selecter, BoxLayout.Y_AXIS));
        panel_label = new JLabel("Select Page");

        shop_button = new JButton("Shop");
        append_button = new JButton("new item");
        // black_list_button = new JButton("black list");

        panel_selecter.add(panel_label);
        panel_selecter.add(shop_button);
        panel_selecter.add(append_button);
        // panel_selecter.add(black_list_button);

        append_panel = new JPanel();
        append_panel.setAlignmentX(Component.LEFT_ALIGNMENT);
        append_panel.setLayout(new BoxLayout(append_panel, BoxLayout.Y_AXIS));
        append_label = new JLabel("Append Item");
        append_item_label = new JLabel("Item name");
        append_item = new JTextField(10);
        append_item.setEditable(true);
        append_money_label = new JLabel("Item money");
        append_money = new JTextField(10);
        append_money.setEditable(true);
        append_item_category_label = new JLabel("Item category");
        append_item_category_comboBox = new JComboBox<String>(Config.item_category_options);
        append_submit_button = new JButton("Submit");
        append_submit_button.addActionListener(l -> {
            try {
                String item = append_item.getText();
                String money = append_money.getText();
                String category = (String)append_item_category_comboBox.getSelectedItem();
                if (item.length() == 0) {
                    JOptionPane.showMessageDialog(null, "Please input item name", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (money.length() == 0) {
                    JOptionPane.showMessageDialog(null, "Please input item money", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (category.equals(Config.item_category_options[0])) {
                    JOptionPane.showMessageDialog(null, "Please select item category", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                int money_int = Integer.parseInt(money);
                if (money_int < 0) {
                    JOptionPane.showMessageDialog(null, "Money must be positive", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                Item new_item = new Item(item, money_int, category);
                items.add(new_item);
                data.put(new_item.id, new_item.get_json());
                Utils.save_data(data, Config.data_path);
                shop_item_list.setListData(items.toArray(new Item[items.size()]));
                append_item.setText("");
                append_money.setText("");
                append_item_category_comboBox.setSelectedIndex(0);
                shop_button.doClick();
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Money must be integer", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Error while writing data", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        append_panel.add(append_label);
        append_panel.add(append_item_label);
        append_panel.add(append_item);
        append_panel.add(append_money_label);
        append_panel.add(append_money);
        append_panel.add(append_item_category_label);
        append_panel.add(append_item_category_comboBox);
        append_panel.add(append_submit_button);

        shop_panel = new JPanel();
        shop_panel.setLayout(new BorderLayout());
        shop_label = new JLabel("Shop");

        shop_item_list = new JList<>();
        shop_item_list.setFont(new Font("monospaced", Font.PLAIN, 12));
        shop_item_list.setListData(items.toArray(new Item[items.size()]));
        shop_item_list.addListSelectionListener(l -> {
            shop_buy_button.setEnabled(true);
            // shop_add_black_list_button.setEnabled(true);
        });

        shop_buy_button = new JButton("Buy");
        shop_buy_button.setEnabled(false);
        shop_buy_button.addActionListener(l -> {
            Item item = shop_item_list.getSelectedValue();
            if (item.money > user_money_amount) {
                JOptionPane.showMessageDialog(null, "Not enough money", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            user_money_amount -= item.money;
            user_money.setText(Long.toString(user_money_amount));
            items.clear();
            data.remove(item.id);
            try {
                Utils.save_data(data, Config.data_path);
                read_data();
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Error while writing data", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception e) {
                e.printStackTrace();
            }
            shop_item_list.setListData(items.toArray(new Item[items.size()]));
            shop_buy_button.setEnabled(false);
            // shop_add_black_list_button.setEnabled(false);
        });
        // shop_add_black_list_button = new JButton("Add to black list");
        // shop_add_black_list_button.setEnabled(false);
        // shop_add_black_list_button.addActionListener(null);

        shop_panel.add(shop_label, BorderLayout.NORTH);
        shop_panel.add(new JScrollPane(shop_item_list), BorderLayout.CENTER);
        shop_panel.add(
            new JPanel() {{
                setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
                add(shop_buy_button);
                // add(shop_add_black_list_button);
            }}
            , BorderLayout.SOUTH
        );


        main_panel.add(shop_panel);
        user_add_money_button.addActionListener(e -> {
            main_panel.removeAll();
            main_panel.add(user_add_money_panel);
            main_panel.revalidate();
            main_panel.repaint();
        });
        append_button.addActionListener(e -> {
            main_panel.removeAll();
            main_panel.add(append_panel);
            main_panel.revalidate();
            main_panel.repaint();
        });
        shop_button.addActionListener(e -> {
            main_panel.removeAll();
            main_panel.add(shop_panel);
            main_panel.revalidate();
            main_panel.repaint();
        });

        window.add(user_info_panel, BorderLayout.NORTH);
        window.add(panel_selecter, BorderLayout.WEST);
        window.add(main_panel, BorderLayout.CENTER);
        window.setVisible(true);
    }
}

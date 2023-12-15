package com.example;

import java.awt.event.KeyListener;
import java.awt.Font;
import java.awt.BorderLayout;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

import javax.swing.BoxLayout;
import javax.swing.JButton;
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
    private static JButton black_list_button;

    private static JPanel main_panel;

    private static JPanel append_panel;
    private static JLabel append_label;
    private static JLabel append_item_label;
    private static JTextField append_item;
    private static JLabel append_money_label;
    private static JTextField append_money;
    private static JButton append_submit_button;

    private static JPanel shop_panel;
    private static JLabel shop_label;
    private static JList<Item> shop_item_list;
    private static JButton shop_buy_button;
    private static JButton shop_add_black_list_button;

    public static void main(String[] args) throws Exception {
        // jframe init
        window = new JFrame();
        window.setSize(400, 250);
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
        user_money.setText(Integer.toString(Config.user_money_init));
        user_add_money_button = new JButton("Add Money");
        user_info_panel.add(user_info_label);
        user_info_panel.add(user_money_label);
        user_info_panel.add(user_money);
        user_info_panel.add(user_add_money_button);

        user_add_money_panel = new JPanel();
        user_add_money_panel.setLayout(new BoxLayout(user_add_money_panel, BoxLayout.Y_AXIS));
        user_add_money_panel_label = new JLabel("Add Money");
        user_add_money_input_label = new JLabel("Amount");
        user_add_money_input = new JTextField(10);
        user_add_money_input.setEditable(true);
        user_add_money_submit_button = new JButton("Submit");
        user_add_money_submit_button.addActionListener(null);
        user_add_money_panel.add(user_add_money_panel_label);
        user_add_money_panel.add(user_add_money_input_label);
        user_add_money_panel.add(user_add_money_input);
        user_add_money_panel.add(user_add_money_submit_button);

        panel_selecter = new JPanel();
        panel_selecter.setLayout(new BoxLayout(panel_selecter, BoxLayout.Y_AXIS));
        panel_label = new JLabel("Select Page");

        shop_button = new JButton("Shop");
        append_button = new JButton("new item");
        black_list_button = new JButton("black list");

        panel_selecter.add(panel_label);
        panel_selecter.add(shop_button);
        panel_selecter.add(append_button);
        panel_selecter.add(black_list_button);

        append_panel = new JPanel();
        append_panel.setLayout(new BoxLayout(append_panel, BoxLayout.Y_AXIS));
        append_label = new JLabel("Append Item");
        append_item_label = new JLabel("Item name");
        append_item = new JTextField(10);
        append_item.setEditable(true);
        append_money_label = new JLabel("Item money");
        append_money = new JTextField(10);
        append_money.setEditable(true);
        append_submit_button = new JButton("Submit");
        append_submit_button.addActionListener(null);

        append_panel.add(append_label);
        append_panel.add(append_item_label);
        append_panel.add(append_item);
        append_panel.add(append_money_label);
        append_panel.add(append_money);
        append_panel.add(append_submit_button);

        shop_panel = new JPanel();
        shop_panel.setLayout(new BorderLayout());
        shop_label = new JLabel("Shop");

        shop_item_list = new JList<>();
        items.add(new Item("apple", 100));
        items.add(new Item("banana", 200));
        items.add(new Item("orange", 300));
        shop_item_list.setListData(items.toArray(new Item[items.size()]));

        shop_buy_button = new JButton("Buy");
        shop_buy_button.setEnabled(false);
        shop_buy_button.addActionListener(null);
        shop_add_black_list_button = new JButton("Add to black list");
        shop_add_black_list_button.setEnabled(false);
        shop_add_black_list_button.addActionListener(null);

        shop_panel.add(shop_label, BorderLayout.NORTH);
        shop_panel.add(new JScrollPane(shop_item_list), BorderLayout.CENTER);
        shop_panel.add(
            new JPanel() {{
                setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
                add(shop_buy_button);
                add(shop_add_black_list_button);
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

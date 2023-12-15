package com.example;

import java.util.UUID;

import org.json.JSONObject;

public class Item {
    String id; // UUID for item
    String item;
    int money;
    String category;

    public Item(String item, int money, String category) {
        this.id = UUID.randomUUID().toString();
        this.item = item;
        this.money = money;
        this.category = category;
    }

    public Item(String id, JSONObject json) {
        this.id = id;
        this.item = (String)json.get("item");
        this.money = json.getInt("money");
        this.category = json.getString("category");
    }

    @Override
    public String toString() {
        return String.format("item: %-10s $: % 5d, category: %s", item, money, category);
    }

    public JSONObject get_json() {
        return new JSONObject(
            String.format(
                "{\"item\":%s,\"money\":%d,\"category\":%s}",
                item,
                money,
                category
            )
        );
    }
}

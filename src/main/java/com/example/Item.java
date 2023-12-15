package com.example;

import java.util.UUID;

import org.json.JSONObject;

public class Item {
    String id; // UUID for item
    String item;
    int money;

    public Item(String item, int money) {
        this.id = UUID.randomUUID().toString();
        this.item = item;
        this.money = money;
    }

    public Item(String id, JSONObject json) {
        this.id = id;
        this.item = (String)json.get("item");
        this.money = json.getInt("money");
    }

    @Override
    public String toString() {
        return String.format("item: %-10s $: % 5d", item, money);
    }

    public JSONObject get_json() {
        return new JSONObject(
            String.format(
                "{\"item\":%s,\"money\":%d}",
                item,
                money
            )
        );
    }
}

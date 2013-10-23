package com.wipro.auction;

/**
 * Item to auction
 */
public class Item {

    private final String name;
    private final String id;

    public Item(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String toString() {
        return "Item #" + this.id + " ( " + this.name + " )";
    }
}
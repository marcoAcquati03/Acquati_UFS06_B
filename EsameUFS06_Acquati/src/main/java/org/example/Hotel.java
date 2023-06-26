package org.example;


class Hotel {
    private int id;
    private String description;
    private String name;
    private boolean suite;
    private double price;

    public Hotel(int id, String description, String name, boolean suite, double price) {
        this.id = id;
        this.description = description;
        this.name = name;
        this.suite = suite;
        this.price = price;
    }

    public int getId() {
        return id;
    }


    public String getDescription() {
        return description;
    }


    public String getName() {
        return name;
    }


    public boolean isSuite() {
        return suite;
    }


    public double getPrice() {
        return price;
    }


    @Override
    public String toString() {
        return "Hotel [id=" + id + ", description=" + description + ", name=" + name + ", suite=" + suite + ", price="
                + price + "]";
    }
}

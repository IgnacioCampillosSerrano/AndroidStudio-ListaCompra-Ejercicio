package ignacio.campillos.androidstudio_listacompra_ejercicio.modelos;

import java.io.Serializable;

public class Product implements Serializable {
    private String name;
    private int quantity;
    
    private float price;
    private float total;

    public Product(String name, int quantity, float price) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        updateTotal();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
        updateTotal();
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
        updateTotal();
    }

    public float getTotal() {
        return total;
    }

    private void updateTotal() {
        this.total = this.price * quantity;
    }

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", quantity=" + quantity +
                ", price=" + price +
                ", total=" + total +
                '}';
    }
}

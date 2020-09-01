public class Item {
    private String itemCode;
    private String itemDescription;
    private double price;

    public Item(String itemCode, String itemDescription, double price) {
        this.itemCode = itemCode;
        this.itemDescription = itemDescription;
        this.price = price;
    }

    public String getItemCode() {
        return itemCode;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public double getPrice() {
        return price;
    }
    
    public String toString(){
        return String.format("(%s, %s, %s)", itemCode, itemDescription, price);
    }
}
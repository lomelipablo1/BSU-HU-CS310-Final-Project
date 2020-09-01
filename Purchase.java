public class Purchase {
    private String itemCode;
    private int purchaseQuantity;

    public Purchase(String itemCode, int purchaseQuantity) {
        this.itemCode = itemCode;
        this.purchaseQuantity = purchaseQuantity;
    }

    public String getItemCode() {
        return itemCode;
    }

    public int getPurchaseQuantity() {
        return purchaseQuantity;
    }

    public String toString(){
        return String.format("(%s, %s)", itemCode, purchaseQuantity);
    }
}
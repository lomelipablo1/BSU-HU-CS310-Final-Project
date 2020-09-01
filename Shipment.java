public class Shipment {
	private String itemCode;
    private int purchaseQuantity;
    private String shipmentDate;

    public Shipment(String itemCode, int purchaseQuantity, String shipmentDate) {
        this.itemCode = itemCode;
        this.purchaseQuantity = purchaseQuantity;
        this.shipmentDate = shipmentDate;
    }

    public String getItemCode() {
        return itemCode;
    }

    public int getPurchaseQuantity() {
        return purchaseQuantity;
    }

    public String getShipmentDate() {
        return shipmentDate;
    }

    public String toString(){
        return String.format("(%s, %s, %s)", itemCode, purchaseQuantity, shipmentDate);
    }
}
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.ArrayList;

public class Project {

    public static Item createItem(String itemCode, String itemDescription, double price) throws SQLException {
        Connection connection = null;
        Item newItem = new Item(itemCode, itemDescription, price);

        connection = MySqlDatabase.getDatabaseConnection();
        Statement sqlStatement = connection.createStatement();

        String sql = String.format("INSERT INTO Item (ItemCode, ItemDescription, Price) VALUES ('%s', '%s', %s);",
        		newItem.getItemCode(),
        		newItem.getItemDescription(),
        		newItem.getPrice());
        sqlStatement.executeUpdate(sql);
        connection.close();

        return newItem;
    }
    
    public static Purchase createPurchase(String itemCode, int purchaseQuantity) throws SQLException {
        Connection connection = null;
        Purchase newPurchase = new Purchase(itemCode, purchaseQuantity);

        connection = MySqlDatabase.getDatabaseConnection();
        Statement sqlStatement = connection.createStatement();

        String sql = String.format("INSERT INTO Purchase (ItemID, Quantity) VALUES ((SELECT ID FROM Item WHERE ItemCode='%s'), %s);",
        		newPurchase.getItemCode(),
        		newPurchase.getPurchaseQuantity());
        sqlStatement.executeUpdate(sql);
        connection.close();

        return newPurchase;
    }
    
    public static Shipment createShipment(String itemCode, int shipmentQuantity, String shipmentDate) throws SQLException {
        Connection connection = null;
        Shipment newShip = new Shipment(itemCode, shipmentQuantity, shipmentDate);
        connection = MySqlDatabase.getDatabaseConnection();
        Statement sqlStatement = connection.createStatement();

        String sql = String.format("INSERT INTO Shipment (ItemID, Quantity, ShipmentDate) VALUES ((SELECT ID FROM Item WHERE ItemCode='%s'), %s, %s);",
        		newShip.getItemCode(),
        		newShip.getPurchaseQuantity(),
        		newShip.getShipmentDate());
        
        sqlStatement.executeUpdate(sql);
        connection.close();

        return newShip;
    }
    
    public static void attemptToCreateNewItem(String itemCode, String itemDescription, double price) {
        try {
            Item newItem = createItem(itemCode, itemDescription, price);
            System.out.println(newItem.toString());
        } catch (SQLException sqlException) {
            System.out.println("Failed to create item");
            System.out.println(sqlException.getMessage());
        }
    }
    
    public static void attemptToCreateNewPurchase(String itemCode, int purchaseQuantity) {
        try {
            Purchase newPurchase = createPurchase(itemCode, purchaseQuantity);
            System.out.println(newPurchase.toString());
        } catch (SQLException sqlException) {
            System.out.println("Failed to create purchase");
            System.out.println(sqlException.getMessage());
        }
    }
    
    public static void attemptToCreateNewShipment(String itemCode, int shipmentQuantity, String shipmentDate) {
        try {
            Shipment newShip = createShipment(itemCode, shipmentQuantity, shipmentDate);
            System.out.println(newShip.toString());
        } catch (SQLException sqlException) {
            System.out.println("Failed to create shipment");
            System.out.println(sqlException.getMessage());
        }
    }
    
    public static void getAllItems(String itemCode) throws SQLException {
        Connection connection = null;


        connection = MySqlDatabase.getDatabaseConnection();
        Statement sqlStatement = connection.createStatement();

        if(itemCode.equals("%"))
        {
        	String sql = "SELECT * FROM Item;";
            ResultSet resultSet = sqlStatement.executeQuery(sql);

            while (resultSet.next()) {
                String itemCodeFromRS = resultSet.getString(2);
                String itemDescription = resultSet.getString(3);
                double price = resultSet.getDouble(4);
                
                System.out.println(String.format("(%s, %s, %s)", itemCodeFromRS, itemDescription, price));
            }
            resultSet.close();
            connection.close();
        }
        else
        {
        	String sql = String.format("SELECT * FROM Item WHERE ItemCode=%s;",
            		"'"+itemCode+"'");
            ResultSet resultSet = sqlStatement.executeQuery(sql);

            while (resultSet.next()) {
                String itemCodeFromRS = resultSet.getString(2);
                String itemDescription = resultSet.getString(3);
                double price = resultSet.getDouble(4);

                System.out.println(String.format("(%s, %s, %s)", itemCodeFromRS, itemDescription, price));
            }
            resultSet.close();
            connection.close();
        }
        


    }

    public static void attemptToListItems(String itemCode) {
        try {
        	getAllItems(itemCode);
            
        } catch (SQLException sqlException) {
            System.out.println("Failed to get items");
            System.out.println(sqlException.getMessage());
        }
    }
    
    public static void getAllPurchases(String itemCode) throws SQLException {
        Connection connection = null;


        connection = MySqlDatabase.getDatabaseConnection();
        Statement sqlStatement = connection.createStatement();

        if(itemCode.equals("%"))
        {
        	String sql = "SELECT * FROM Purchase;";
            ResultSet resultSet = sqlStatement.executeQuery(sql);

            while (resultSet.next()) {
                int itemID = resultSet.getInt(2);
                int Quantity = resultSet.getInt(3);
                String purchaseDate = resultSet.getString(4);
                
                System.out.println(String.format("(%s, %s, %s)", itemID, Quantity, purchaseDate));
            }
            resultSet.close();
            connection.close();
        }
        else
        {
        	String sql = String.format("SELECT * FROM Purchase WHERE ItemID=((SELECT ID FROM Item WHERE ItemCode='%s'));",
            		itemCode);
            ResultSet resultSet = sqlStatement.executeQuery(sql);

            while (resultSet.next()) {
            	int itemID = resultSet.getInt(2);
                int Quantity = resultSet.getInt(3);
                String purchaseDate = resultSet.getString(4);
                
                System.out.println(String.format("(%s, %s, %s)", itemID, Quantity, purchaseDate));
            }
            resultSet.close();
            connection.close();
        }
        


    }

    public static void attemptToListPurchases(String itemCode) {
        try {
        	getAllPurchases(itemCode);
            
        } catch (SQLException sqlException) {
            System.out.println("Failed to get purchases");
            System.out.println(sqlException.getMessage());
        }
    }
    
    public static void getAllShipments(String itemCode) throws SQLException {
        Connection connection = null;


        connection = MySqlDatabase.getDatabaseConnection();
        Statement sqlStatement = connection.createStatement();

        if(itemCode.equals("%"))
        {
        	String sql = "SELECT * FROM Shipment;";
            ResultSet resultSet = sqlStatement.executeQuery(sql);

            while (resultSet.next()) {
                int itemID = resultSet.getInt(2);
                int Quantity = resultSet.getInt(3);
                String ShipmentDate = resultSet.getString(4);
                
                System.out.println(String.format("(%s, %s, %s)", itemID, Quantity, ShipmentDate));
            }
            resultSet.close();
            connection.close();
        }
        else
        {
        	String sql = String.format("SELECT * FROM Shipment WHERE ItemID=(SELECT ID FROM Item WHERE ItemCode='%s');",
            		itemCode);
            ResultSet resultSet = sqlStatement.executeQuery(sql);

            while (resultSet.next()) {
            	int itemID = resultSet.getInt(2);
                int Quantity = resultSet.getInt(3);
                String ShipmentDate = resultSet.getString(4);
                
                System.out.println(String.format("(%s, %s, %s)", itemID, Quantity, ShipmentDate));
            }
            resultSet.close();
            connection.close();
        }
        


    }
    
    public static void allAvailable(String itemCode) throws SQLException {
        Connection connection = null;


        connection = MySqlDatabase.getDatabaseConnection();
        Statement sqlStatement = connection.createStatement();

        if(itemCode.equals("%"))
        {
        	String sql = "SELECT DISTINCT ItemCode as currentItem, ItemDescription, \r\n" + 
        			"CASE\r\n" + 
        			"WHEN (SELECT SUM(Quantity) FROM Shipment WHERE ItemID=(SELECT ID FROM Item WHERE ItemCode=currentItem)) IS NULL THEN 0 - (SELECT SUM(Quantity) FROM Purchase WHERE ItemID=(SELECT ID FROM Item WHERE ItemCode=currentItem))\r\n" + 
        			"\r\n" + 
        			"WHEN (SELECT SUM(Quantity) FROM Purchase WHERE ItemID=(SELECT ID FROM Item WHERE ItemCode=currentItem)) IS NULL THEN (SELECT SUM(Quantity) FROM Shipment WHERE ItemID=(SELECT ID FROM Item WHERE ItemCode=currentItem)) - 0\r\n" + 
        			"\r\n" + 
        			"ELSE ((SELECT SUM(Quantity) FROM Shipment WHERE ItemID=(SELECT ID FROM Item WHERE ItemCode=currentItem) ) - \r\n" + 
        			"(SELECT SUM(Quantity) FROM Purchase WHERE ItemID=(SELECT ID FROM Item WHERE ItemCode=currentItem) ))\r\n" + 
        			"\r\n" + 
        			"END AS QuantityAvailable\r\n" + 
        			"FROM Item;";
            ResultSet resultSet = sqlStatement.executeQuery(sql);

            while (resultSet.next()) {
                String itemCodeFromRS = resultSet.getString(1);
                String itemDescription = resultSet.getString(2);
                int quantityAvailable = resultSet.getInt(3);
                
                if(quantityAvailable < 0)
                {
                	quantityAvailable = 0;
                }
                
                System.out.println(String.format("(%s, %s, %s)", itemCodeFromRS, itemDescription, quantityAvailable));
            }
            resultSet.close();
            connection.close();
        }
        else
        {
        	String sql = "SELECT DISTINCT ItemCode as currentItem, ItemDescription, \r\n" + 
        			"CASE\r\n" + 
        			"WHEN (SELECT SUM(Quantity) FROM Shipment WHERE ItemID=(SELECT ID FROM Item WHERE ItemCode=currentItem)) IS NULL THEN 0 - (SELECT SUM(Quantity) FROM Purchase WHERE ItemID=(SELECT ID FROM Item WHERE ItemCode=currentItem))\r\n" + 
        			"\r\n" + 
        			"WHEN (SELECT SUM(Quantity) FROM Purchase WHERE ItemID=(SELECT ID FROM Item WHERE ItemCode=currentItem)) IS NULL THEN (SELECT SUM(Quantity) FROM Shipment WHERE ItemID=(SELECT ID FROM Item WHERE ItemCode=currentItem)) - 0\r\n" + 
        			"\r\n" + 
        			"ELSE ((SELECT SUM(Quantity) FROM Shipment WHERE ItemID=(SELECT ID FROM Item WHERE ItemCode=currentItem) ) - \r\n" + 
        			"(SELECT SUM(Quantity) FROM Purchase WHERE ItemID=(SELECT ID FROM Item WHERE ItemCode=currentItem) ))\r\n" + 
        			"\r\n" + 
        			"END AS QuantityAvailable\r\n" + 
        			"FROM Item WHERE ItemCode='"+itemCode+"';";
            ResultSet resultSet = sqlStatement.executeQuery(sql);

            while (resultSet.next()) {
            	String itemCodeFromRS = resultSet.getString(1);
                String itemDescription = resultSet.getString(2);
                int quantityAvailable = resultSet.getInt(3);
                
                if(quantityAvailable < 0)
                {
                	quantityAvailable = 0;
                }
                
                System.out.println(String.format("(%s, %s, %s)", itemCodeFromRS, itemDescription, quantityAvailable));
            }
            resultSet.close();
            connection.close();
        }
        


    }

    public static void attemptToListShipments(String itemCode) throws SQLException {
        try {
        	getAllShipments(itemCode);
            
        } catch (SQLException sqlException) {
            System.out.println("Failed to get shipments");
            System.out.println(sqlException.getMessage());
        }
    }
    
    public static void attemptToListItemsAvailable(String itemCode) throws SQLException {
        try {
        	allAvailable(itemCode);
            
        } catch (SQLException sqlException) {
            System.out.println("Failed to get shipments");
            System.out.println(sqlException.getMessage());
        }
    }
    
    public static void attemptToUpdateItem(String itemCode, double price) throws SQLException {
        Connection connection = null;

        connection = MySqlDatabase.getDatabaseConnection();
        Statement sqlStatement = connection.createStatement();

        String sql = String.format("UPDATE Item SET price = '%s' WHERE ItemCode='%s'",
                price, itemCode);

        sqlStatement.executeUpdate(sql);

        connection.close();
    }
    
    public static void attemptToDeleteItem(String itemCode) throws SQLException {
        Connection connection = null;

        connection = MySqlDatabase.getDatabaseConnection();
        Statement sqlStatement = connection.createStatement();

        String sql = String.format("DELETE FROM Item WHERE ItemCode = '%s';", itemCode);
        sqlStatement.executeUpdate(sql);
        connection.close();
    }
    
    public static void attemptToListDeleteShipment(String itemCode) throws SQLException {
        Connection connection = null;

        connection = MySqlDatabase.getDatabaseConnection();
        Statement sqlStatement = connection.createStatement();

        String sql = String.format("DELETE FROM Shipment WHERE ItemID=(SELECT ID FROM Item WHERE ItemCode='%s') "
        		+ "ORDER BY ID DESC LIMIT 1", itemCode);
        sqlStatement.executeUpdate(sql);
        connection.close();
    }
    
    public static void attemptToListDeletePurchase(String itemCode) throws SQLException {
        Connection connection = null;

        connection = MySqlDatabase.getDatabaseConnection();
        Statement sqlStatement = connection.createStatement();

        String sql = String.format("DELETE FROM Purchase WHERE ItemID=(SELECT ID FROM Item WHERE ItemCode='%s')"
        		+ "ORDER BY ID DESC LIMIT 1", itemCode);
        sqlStatement.executeUpdate(sql);
        connection.close();
    }
    
    public static void main(String[] args) throws SQLException {

    	if (args[0].equals("CreateItem")) {
            String itemCode = args[1];
            String itemDescription = args[2];
            double price = Double.parseDouble(args[3]);
            attemptToCreateNewItem(itemCode, itemDescription, price);
    	}
    	else if (args[0].equals("CreatePurchase")) {
            String itemCode = args[1];
            int purchaseQuantity = Integer.parseInt(args[2]);
            attemptToCreateNewPurchase(itemCode, purchaseQuantity);
    	}
    	else if (args[0].equals("CreateShipment")) {
            String itemCode = args[1];
            int shipmentQuantity = Integer.parseInt(args[2]);
            String shipmentDate = "'" + args[3] +"'";
            attemptToCreateNewShipment(itemCode, shipmentQuantity, shipmentDate);
    	}
    	else if (args[0].equals("GetItems")) {
            String itemCode = args[1];
            attemptToListItems(itemCode);
    	}
    	else if (args[0].equals("GetPurchases")) {
            String itemCode = args[1];
            attemptToListPurchases(itemCode);
    	}
    	else if (args[0].equals("GetShipments")) {
            String itemCode = args[1];
            attemptToListShipments(itemCode);
    	}
    	else if (args[0].equals("ItemsAvailable")) {
            String itemCode = args[1];
            attemptToListItemsAvailable(itemCode);
    	}
    	else if (args[0].equals("UpdateItem")) {
            String itemCode = args[1];
            double price = Double.parseDouble(args[2]);
            attemptToUpdateItem(itemCode, price);
    	}
    	else if (args[0].equals("DeleteItem")) {
            String itemCode = args[1];
            attemptToDeleteItem(itemCode);
    	}
    	else if (args[0].equals("DeleteShipment")) {
            String itemCode = args[1];
            attemptToListDeleteShipment(itemCode);
    	}
    	else if (args[0].equals("DeletePurchase")) {
            String itemCode = args[1];
            attemptToListDeletePurchase(itemCode);
    	}
    }
}
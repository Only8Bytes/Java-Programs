package pizzaBean;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sql.DataSource;
import javax.annotation.Resource;
import javax.annotation.sql.DataSourceDefinition;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetProvider;
import java.util.Date;
import java.sql.Timestamp;

@DataSourceDefinition(
   name = "java:global/jdbc/pizzadb",
   className = "org.apache.derby.jdbc.ClientDataSource",
   url = "jdbc:derby://localhost:1527/pizzadb",
   databaseName = "pizzadb",
   user = "app",
   password = "app")

@ManagedBean(name="pizzaBean")
@SessionScoped
public class pizzaBean implements Serializable {
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String pizzaType;
    private String pizzaQuantity;
    private String sideType;
    private String sideQuantity;
    private String drinkType;
    private String drinkQuantity;
    
    @Resource(lookup="java:global/jdbc/pizzadb")
    DataSource dataSource;
    
    //get functions for all properties access by jsf
    public String getFirstName(){
        return firstName;
    }
    
    public String getLastName(){
        return lastName;
    }
    
    public String getEmail(){
        return email;
    }
    
    public String getPhoneNumber(){
        return phoneNumber;
    }
    
    public String getPizzaType(){
        return pizzaType;
    }
    
    public String getPizzaQuantity(){
        return pizzaQuantity;
    }
    
    public String getSideType(){
        return sideType;
    }
    
    public String getSideQuantity(){
        return sideQuantity;
    }
    
    public String getDrinkType(){
        return drinkType;
    }
    
    public String getDrinkQuantity(){
        return drinkQuantity;
    }
    
    //set functions for all properties accessed by jsf
    public void setFirstName(String input){
        this.firstName = input;
    }
    
    public void setLastName(String input){
        this.lastName = input;
    }
    
    public void setEmail(String input){
        this.email = input;
    }
    
    public void setPhoneNumber(String input){
        this.phoneNumber = input;
    }
    
    public void setPizzaType(String input){
        this.pizzaType = input;
    }
    
    public void setPizzaQuantity(String input){
        this.pizzaQuantity = input;
    }
    
    public void setSideType(String input){
        this.sideType = input;
    }
    
    public void setSideQuantity(String input){
        this.sideQuantity = input;
    }
    
    public void setDrinkType(String input){
        this.drinkType = input;
    }
    
    public void setDrinkQuantity(String input){
        this.drinkQuantity = input;
    }
    
    //return all past orders to the datatable on the past orders page, cloned from the addressbook example
    public ResultSet getOrders() throws SQLException{
        if (dataSource == null){
            throw new SQLException("Unable to obtain DataSource");
        }
        Connection connection = dataSource.getConnection();
        if (connection == null){
            throw new SQLException("Unable to obtain DataSource");
        }
        try {
            //query modified to display orders by ordertime in descending order
            PreparedStatement getOrders = connection.prepareStatement("SELECT ORDERID, PHONENUMBER, ORDERTIME, TOTALPRICE " + "FROM ORDERS ORDER BY ORDERTIME DESC");
            CachedRowSet rowSet = RowSetProvider.newFactory().createCachedRowSet();
            rowSet.populate(getOrders.executeQuery());
            return rowSet;
        } finally {
            connection.close();
        }
    }
    
    //if a customer hits the "return home" button send them back to the homepage and wipe their previous input
    public String home(){
        firstName = null;
        lastName = null;
        email = null;
        phoneNumber = null;
        pizzaType = null;
        pizzaQuantity = null;
        sideType = null;
        sideQuantity = null;
        drinkType = null;
        drinkQuantity = null;
        return "index";
    }
    
    //save function for when a customer submits and order
    public String save() throws SQLException{
        if (dataSource == null){
            throw new SQLException("Unable to obtain DataSource");
        }
        Connection connection = dataSource.getConnection();
        if (connection == null){
            throw new SQLException("Unable to obtain DataSource");
        }
        try {
            //add customer if one with the phonenumber does not exist
            PreparedStatement addCustomer = connection.prepareStatement("INSERT INTO CUSTOMERS (SELECT ? AS PHONENUMBER, ? AS FIRSTNAME, ? AS LASTNAME, ? AS EMAIL FROM CUSTOMERS WHERE PHONENUMBER = ? HAVING count(*)=0)");
            addCustomer.setString(1, getPhoneNumber());
            addCustomer.setString(2, getFirstName());
            addCustomer.setString(3, getLastName());
            addCustomer.setString(4, getEmail());
            addCustomer.setString(5, getPhoneNumber());
            addCustomer.executeUpdate();
            
            //add items ordered by the customer and their quantities to the ordereditems database
            Date orderDate = new Date();
            String orderId = Long.toString(orderDate.getTime() & 0x0000000000ffffffL);
            PreparedStatement addOrderedItems = connection.prepareStatement("INSERT INTO ORDEREDITEMS " + "(ORDERID, PIZZAID, PIZZAQN, SIDESID, SIDESQN, DRINKID, DRINKQN)" + " VALUES (?, ?, ?, ?, ?, ?, ?)");
            addOrderedItems.setString(1, orderId);
            addOrderedItems.setString(2, getPizzaType());
            addOrderedItems.setString(3, getPizzaQuantity());
            addOrderedItems.setString(4, getSideType());
            addOrderedItems.setString(5, getSideQuantity());
            addOrderedItems.setString(6, getDrinkType());
            addOrderedItems.setString(7, getDrinkQuantity());
            addOrderedItems.executeUpdate();
            
            //calculate the price by getting the price of each pizza, side and drink of the id they ordered and multiply by the quantity
            float price = 0;
            
            PreparedStatement stmnt = connection.prepareStatement("SELECT PRICE FROM PIZZA WHERE PIZZAID = ?");
            stmnt.setString(1, getPizzaType());
            ResultSet pizzasResult = stmnt.executeQuery();
            while (pizzasResult.next()){
                price += pizzasResult.getFloat("PRICE") * Integer.parseInt(getPizzaQuantity());
            }
            
            stmnt = connection.prepareStatement("SELECT PRICE FROM SIDES WHERE SIDESID = ?");
            stmnt.setString(1, getSideType());
            ResultSet sideResult = stmnt.executeQuery();
            while (sideResult.next()){
                price += sideResult.getFloat("PRICE") * Integer.parseInt(getSideQuantity());
            }
            
            stmnt = connection.prepareStatement("SELECT PRICE FROM DRINKS WHERE DRINKID = ?");
            stmnt.setString(1, getDrinkType());
            ResultSet drinkResult = stmnt.executeQuery();
            while (drinkResult.next()){
                price += drinkResult.getFloat("PRICE") * Integer.parseInt(getDrinkQuantity());
            }
            
            //add customer's order to the orders database
            PreparedStatement addOrder = connection.prepareStatement("INSERT INTO ORDERS " + "(ORDERID, PHONENUMBER, ORDERTIME, TOTALPRICE)" + " VALUES (?, ?, ?, ?)");
            addOrder.setString(1, orderId);
            addOrder.setString(2, getPhoneNumber());
            addOrder.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
            addOrder.setFloat(4, price);//temporary
            addOrder.executeUpdate();
            
            //wipe all of their previous input and return them to the homepage
            firstName = null;
            lastName = null;
            email = null;
            phoneNumber = null;
            pizzaType = null;
            pizzaQuantity = null;
            sideType = null;
            sideQuantity = null;
            drinkType = null;
            drinkQuantity = null;
            
            return "index";
        } finally {
            connection.close();
        }
    }
}
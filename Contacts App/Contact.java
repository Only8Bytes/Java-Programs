package contactsapp;

import javafx.scene.image.Image;

public class Contact {
    private String FirstName;
    private String LastName;
    private String Email;
    private String Address;
    private String Phone;
    private Image Image;
    
    public Contact(String lname, String fname, String email, String phone, String address, Image image){
        this.FirstName = fname;
        this.LastName = lname;
        this.Email = email;
        this.Phone = phone;
        this.Address = address;
        this.Image = image;
    }
    
    public String getName(){
        return this.FirstName + " " + this.LastName;
    }
    
    public String getLastName(){
        return this.LastName;
    }
    
    public String getFirstName(){
        return this.FirstName;
    }
    
    public String getEmail(){
        return this.Email;
    }
    
    public String getAddress(){
        return this.Address;
    }
    
    public String getPhone(){
        return this.Phone;
    }
    
    public Image getImage(){
        return this.Image;
    }
    
    public void setFirstName(String name){
        this.FirstName = name;
    }
    
    public void setLastName(String name){
        this.LastName = name;
    }
    
    public void setPhone(String phone){
        this.Phone = phone;
    }
    
    public void setEmail(String email){
        this.Email = email;
    }
    
    public void setAddress(String address){
        this.Address = address;
    }
    
    public void setImage(Image image){
        if (image == null){
            this.Image = null;
        } else {
            this.Image = image;
        }
    }
}
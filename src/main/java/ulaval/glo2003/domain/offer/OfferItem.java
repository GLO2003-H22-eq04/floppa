package ulaval.glo2003.domain.offer;

import ulaval.glo2003.domain.product.Amount;

public class OfferItem {
    private String name;
    private String email;
    private int phoneNumber;
    private Amount amount;
    private String message;

    public void setName(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setPhoneNumber(int phoneNumber){
        this.phoneNumber = phoneNumber;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setAmount(Amount amount){
        this.amount = amount;
    }

    public Amount getAmount() {
        return amount;
    }

    public void setMessage(String message){
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}

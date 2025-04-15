package domain;

public class Payment {
    private int paymentId;
    private int customerId;
    private double amount;
    private String paymentMethod;
    private String paymentDate;
    private String name;         
    private String shippingAddress; 

    public Payment() {}

    public Payment(int paymentId, int customerId, double amount, String paymentMethod, String paymentDate, String name, String shippingAddress) {
        this.paymentId = paymentId;
        this.customerId = customerId;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.paymentDate = paymentDate;
        this.name = name;
        this.shippingAddress = shippingAddress;
    }

    // Getters and Setters
    public int getPaymentId() { 
        return paymentId; 
    }
    
    public void setPaymentId(int paymentId) { 
        this.paymentId = paymentId; 
    }
    
    public int getCustomerId() { 
        return customerId; 
    }
    
    public void setCustomerId(int customerId) { 
        this.customerId = customerId; 
    }
    
    public double getAmount() { 
        return amount; 
    }
    
    public void setAmount(double amount) { 
        this.amount = amount; 
    }
    
    public String getPaymentMethod() { 
        return paymentMethod; 
    }
    
    public void setPaymentMethod(String paymentMethod) { 
        this.paymentMethod = paymentMethod; 
    }
    
    public String getPaymentDate() { 
        return paymentDate; 
    }
    
    public void setPaymentDate(String paymentDate) { 
        this.paymentDate = paymentDate; 
    }
    
    public String getName() { 
        return name; 
    }
    
    public void setName(String name) { 
        this.name = name; 
    }
    
    public String getShippingAddress() { 
        return shippingAddress; 
    }
    
    public void setShippingAddress(String shippingAddress) { 
        this.shippingAddress = shippingAddress; 
    }
}
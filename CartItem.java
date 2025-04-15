package domain;

public class CartItem {
    private int cartItemId;
    private int customerId;
    private int productId;
    private int quantity;

    public CartItem() {}

    public CartItem(int cartItemId, int customerId, int productId, int quantity) {
        this.cartItemId = cartItemId;
        this.customerId = customerId;
        this.productId = productId;
        this.quantity = quantity;
    }

    // Getters and Setters
    public int getCartItemId() { 
        return cartItemId; 
    }
    
    public void setCartItemId(int cartItemId) { 
        this.cartItemId = cartItemId; 
    }
    
    public int getCustomerId() { 
        return customerId; 
    }
    
    public void setCustomerId(int customerId) { 
        this.customerId = customerId; 
    }
    
    public int getProductId() { 
        return productId; 
    }
    
    public void setProductId(int productId) { 
        this.productId = productId; 
    }
    
    public int getQuantity() { 
        return quantity; 
    }
    
    public void setQuantity(int quantity) { 
        this.quantity = quantity; 
    }
}
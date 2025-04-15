package domain;

public class Product {
    private int productId;
    private String name;
    private String description;
    private double price;
    private int stock;
    private byte[] image;

    // Constructor
    public Product(int productId, String name, String description, double price, int stock, byte[] image) {
        this.productId = productId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
        this.image = image;
    }

    // Default constructor
    public Product() {}

    // Getters and Setters
    public int getProductId() { 
        return productId; 
    }
    
    public void setProductId(int productId) { 
        this.productId = productId; 
    }
    
    public String getName() { 
        return name; 
    }
    
    public void setName(String name) { 
        this.name = name; 
    }
    
    public String getDescription() { 
        return description; 
    }
    
    public void setDescription(String description) { 
        this.description = description; 
    }
    
    public double getPrice() { 
        return price; 
    }
    
    public void setPrice(double price) { 
        this.price = price; 
    }
    
    public int getStock() { 
        return stock; 
    }
    
    public void setStock(int stock) { 
        this.stock = stock; 
    }
    
    public byte[] getImage() { 
        return image; 
    }
    
    public void setImage(byte[] image) { 
        this.image = image; 
    }
}
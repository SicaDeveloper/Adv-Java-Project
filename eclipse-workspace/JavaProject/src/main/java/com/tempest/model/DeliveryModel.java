public class DeliveryModel {
    private int delivery_id;
    private String delivery_date;
    private String delivery_status;
    private String delivery_address;
    private String delivery_mode;

    // Default constructor
    public DeliveryModel() {
    }

    // Constructor with all fields
    public DeliveryModel(int delivery_id, String delivery_date, String delivery_status, String delivery_address, String delivery_mode) {
        this.delivery_id = delivery_id;
        this.delivery_date = delivery_date;
        this.delivery_status = delivery_status;
        this.delivery_address = delivery_address;
        this.delivery_mode = delivery_mode;
    }

    // Getter for delivery_id
    public int getDelivery_id() {
        return delivery_id;
    }

    // Setter for delivery_id
    public void setDelivery_id(int delivery_id) {
        this.delivery_id = delivery_id;
    }

    // Getter for delivery_date
    public String getDelivery_date() {
        return delivery_date;
    }

    // Setter for delivery_date
    public void setDelivery_date(String delivery_date) {
        this.delivery_date = delivery_date;
    }

    // Getter for delivery_status
    public String getDelivery_status() {
        return delivery_status;
    }

    // Setter for delivery_status
    public void setDelivery_status(String delivery_status) {
        this.delivery_status = delivery_status;
    }

    // Getter for delivery_address
    public String getDelivery_address() {
        return delivery_address;
    }

    // Setter for delivery_address
    public void setDelivery_address(String delivery_address) {
        this.delivery_address = delivery_address;
    }

    // Getter for delivery_mode
    public String getDelivery_mode() {
        return delivery_mode;
    }

    // Setter for delivery_mode
    public void setDelivery_mode(String delivery_mode) {
        this.delivery_mode = delivery_mode;
    }
32	
}


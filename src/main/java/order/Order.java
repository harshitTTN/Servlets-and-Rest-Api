package order;

public class Order {
    private String name;
    private int quantity;
    private int id;

    public Order(String name , int quantity,int id){
         this.quantity = quantity;
         this.name = name;
         this.id = id;

    }
    public void setName(String name) {
        this.name = name;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public String getName(){
        return name;
    }
    public int getQuantity(){
        return quantity;
    }
    public int getId()
    {
        return id;
    }
    public void setId(int id)
    {
        this.id = id;
    }
    @Override
    public String toString() {
        return "{" +
                "name='" + name + '\'' +
                ", quantity=" + quantity +
                '}';
    }
}

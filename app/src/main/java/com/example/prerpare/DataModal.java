package com.example.prerpare;

public class DataModal {

    private String title;
    private int cost;
    private int stockAvailability;
    private int availabilityInTheStore;
    private String description;
    private String rewiews;
    private String image;

    public DataModal(String Title, int Cost, int StockAvailability, int AvailabilityInTheStore, String Description, String Rewiews, String Image){
    this.title = Title;
    this.cost = Cost;
    this.stockAvailability = StockAvailability;
    this.availabilityInTheStore = AvailabilityInTheStore;
    this.description = Description;
    this.rewiews = Rewiews;
    this.image = Image;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public int getStockAvailability() {
        return stockAvailability;
    }

    public void setStockAvailability(int stockAvailability) {
        this.stockAvailability = stockAvailability;
    }

    public int getAvailabilityInTheStore() {
        return availabilityInTheStore;
    }

    public void setAvailabilityInTheStore(int availabilityInTheStore) {
        this.availabilityInTheStore = availabilityInTheStore;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRewiews() {
        return rewiews;
    }

    public void setRewiews(String rewiews) {
        this.rewiews = rewiews;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}

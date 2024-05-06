package venue;

import javafx.beans.property.*;

public class Venue {
    private final IntegerProperty venueId = new SimpleIntegerProperty();
    private final StringProperty name = new SimpleStringProperty();
    private final IntegerProperty capacity = new SimpleIntegerProperty();
    private final StringProperty suitableFor = new SimpleStringProperty();
    private final StringProperty category = new SimpleStringProperty();
    private final IntegerProperty rate = new SimpleIntegerProperty();

    
    
    // Constructor
    public Venue(int venueId, String name, int capacity, String suitableFor, String category, int rate) {
        setVenueId(venueId);
        setName(name);
        setCapacity(capacity);
        setSuitableFor(suitableFor);
        setCategory(category);
        setRate(rate);
    }

    // Getter and setter for venueId
    public int getVenueId() {
        return venueId.get();
    }

    public void setVenueId(int venueId) {
        this.venueId.set(venueId);
    }

    public IntegerProperty venueIdProperty() {
        return venueId;
    }

    // Getter and setter for name
    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public StringProperty nameProperty() {
        return name;
    }

    // Getter and setter for capacity
    public int getCapacity() {
        return capacity.get();
    }

    public void setCapacity(int capacity) {
        this.capacity.set(capacity);
    }


    // Getter and setter for suitableFor
    public String getSuitableFor() {
        return suitableFor.get();
    }

    public void setSuitableFor(String suitableFor) {
        this.suitableFor.set(suitableFor);
    }

    public StringProperty suitableForProperty() {
        return suitableFor;
    }

    // Getter and setter for category
    public String getCategory() {
        return category.get();
    }

    public void setCategory(String category) {
        this.category.set(category);
    }

    public StringProperty categoryProperty() {
        return category;
    }

    // Getter and setter for rate
    public int getRate() {
        return rate.get();
    }

    public void setRate(int rate) {
        this.rate.set(rate);
    }

    public IntegerProperty rateProperty() {
        return rate;
    }

    @Override
    public String toString() {
        return String.format("%d, %s, %d, %s, %s, %d", venueId.get(), name.get(), capacity.get(), suitableFor.get(), category.get(), rate.get());
    }



	public Object compatabilityScoreProperty() {
		// TODO Auto-generated method stub
		return null;
	}

	
	
//	 public ObjectProperty<Integer> venueNoProperty() {
	public ObjectProperty<Integer> venueNoProperty() {
	        return venueId.asObject();
	    }

	    public StringProperty venueNameProperty() {
	        return name;
	    }

	    public ObjectProperty<Integer> capacityProperty() {
	        return capacity.asObject();
	    }

}

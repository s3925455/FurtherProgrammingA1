package venue;
/**
 * 
 */


/**
 * @author amitmunjal
 *
 */
//Venue class representing venue information
class Venue {
	private int venueId;
	private String name;
	private int capacity;
	private String suitableFor;
	private String category;
	private int rate; 

	// Constructor
	public Venue(int venueId, String name, int capacity, String suitableFor, String category, int rate) {
		this.venueId = venueId;
		this.name = name;
		this.capacity = capacity;
		this.suitableFor = suitableFor;
		this.category = category;
		this.rate = rate;
	}


	// Override toString method
	@Override
	public String toString() {
//		return String.format("Venue ID: %d\nName: %s\nCapacity: %d\nSuitable for: %s\nCategory: %s\nRate: %d\n",
//	            venueId, name, capacity, suitableFor, category,  rate);
		
		return String.format("%d, %s, %d, %s, %s, %d", venueId, name, capacity, suitableFor, category, rate);
	}

	
	//Getter and setter for ID for use in SelectByCategory ----------
    public int getVenueId() {
        return venueId;
    }

    public void setVenueId(int venueId) {
        this.venueId = venueId;
    }
    
	// Getters n setter for name-----------
	public String getName() {
		return name;
	}
	
    public void setName(String name) {
        this.name = name;
    }

    ///Getter n setter for Capacity--------
	public int getCapacity() {
		return capacity;
	}
	
	public void setCapacity(int capacity) {
	     this.capacity = capacity;
	}
	
	//Getter Setter for suitableFor ----------
	public String getSuitableFor() {
		return suitableFor;
	}
	
	public void setSuitableFor(String suitableFor) {
	     this.suitableFor = suitableFor;
	}

	
	// Getter and setter for Category---------
	public String getCategory() {
	    return category;
	}

	public void setCategory(String category) {
	     this.category = category;
	}

    
    // Getter and setter for rate
    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
	}
	
}

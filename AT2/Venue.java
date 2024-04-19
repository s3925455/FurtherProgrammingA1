/**
 * 
 */
package AT2;

/**
 * @author amitmunjal
 *
 */
//Venue class representing venue information
class Venue {
	private String id;
	private String name;
	private String capacity;
	private String suitableFor;
	private String category;
	private String rate; 

	// Constructor
	public Venue(String name, String capacity, String suitableFor, String category, String rate) {
		this.name = name;
		this.capacity = capacity;
		this.suitableFor = suitableFor;
		this.category = category;
		this.id = id;
		this.rate = rate;
	}

	public Venue(String string, String string2, int i) {
		// TODO Auto-generated constructor stub
	}

	public Venue(String name2, int capacity, String category, String suitableFor, String rate) {
		// TODO Auto-generated constructor stub
	}

//	public Venue(String name2, String capacity2, String suitableFor2, String category2, String string) {
//		// TODO Auto-generated constructor stub
//	}

	// Override toString method
	@Override
	public String toString() {
		return String.format("%s\nCapacity: %d\nCategory: %s\nSuitable for: %s\n", name, capacity, category,
				suitableFor);
	}

	// Getters
	public String getName() {
		return name;
	}

	public String getCapacity() {
		return capacity;
	}

	public String getCategory() {
		return category;
	}

	public String getSuitableFor() {
		return suitableFor;
	}

	//Getter and setter for ID for use in SelectByCategory
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    // Getter and setter for rate
    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
	}
	
}

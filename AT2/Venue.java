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
	private String name;
	private int capacity;
	private String suitableFor;
	private String category;
	private int id;

	// Constructor
	public Venue(String name, int capacity, String suitableFor, String category) {
		this.name = name;
		this.capacity = capacity;
		this.suitableFor = suitableFor;
		this.category = category;
		this.id = id;
	}

	public Venue(String string, String string2, int i) {
		// TODO Auto-generated constructor stub
	}

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

	public int getCapacity() {
		return capacity;
	}

	public String getCategory() {
		return category;
	}

	public String getSuitableFor() {
		return suitableFor;
	}

	//Getter and setter for ID for use in SelectByCategory
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
	
}

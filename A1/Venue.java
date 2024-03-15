/**
 * 
 */
package A1;

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

	// Constructor
	public Venue(String name, int capacity, String suitableFor, String category) {
		this.name = name;
		this.capacity = capacity;
		this.suitableFor = suitableFor;
		this.category = category;
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
}

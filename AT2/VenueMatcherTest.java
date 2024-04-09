package AT2;
//package AT1;
//
////import static org.junit.Assert.assertNotNull;
//
////import static org.junit.Assert.assertEquals;
////import static org.junit.Assert.assertNotNull;
//
//import java.io.ByteArrayInputStream;
//import java.io.FileNotFoundException;
//import java.io.InputStream;
//import java.io.OutputStream;
//import java.util.ArrayList;
//import java.util.List;
//
//import org.junit.Before;
//import org.junit.Test;
//
//public class VenueMatcherTest {
//
//	private VenueMatcher venueMatcher;
//	private List<Venue> venues;
//	private List<String> oldJobRequests;
//
//	@Before
//	public void setUp() {
//		venueMatcher = new VenueMatcher();
//		venues = new ArrayList<>();
//		venues.add(new Venue("Venue1", "Outdoor", 100));
//		venues.add(new Venue("Venue2", "Indoor", 200));
//		oldJobRequests = new ArrayList<>();
//		oldJobRequests.add("JobRequest1");
//		oldJobRequests.add("JobRequest2");
//	}
//
//	@Test
//	public void testGetName() {
//		assertEquals("Test Restaurant", venueMatcher.getName());
//	}
//
//	private void assertEquals(String string, Object name) {
//
//		
//	}
//
//	@Test
//	public void testGetVenues() {
//		assertNotNull(venueMatcher.getVenues());
//		assertEquals(venues.size(), ((List<Venue>) venueMatcher.getVenues()).size());
//	}
//
//	private void assertEquals(int size, int size2) {
//		
//		
//	}
//
//	@Test
//	public void testGetOldJobRequests() {
//		assertNotNull(venueMatcher.getOldJobRequests());
//		assertEquals(oldJobRequests.size(), ((ArrayList<Venue>) venueMatcher.getOldJobRequests()).size());
//	}
//
//	private void assertNotNull(Object oldJobRequests2) {
//	
//		
//	}
//
//	@Test
//	public void testDisplayOldJobRequests() throws FileNotFoundException {
//		// Redirect System.out to capture printed output
//		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
//		System.setOut(new PrintStream(outContent));
//
//		venueMatcher.displayOldJobRequests();
//		String expectedOutput = "JobRequest1\nJobRequest2\n";
//		assertEquals(expectedOutput, outContent.toString());
//	}
//
//	@Test
//	public void testSelectByCategory() {
//		// Redirect System.in to simulate user input
//		String input = "1\n"; // Select Outdoor category
//		InputStream in = new ByteArrayInputStream(input.getBytes());
//		System.setIn(in);
//
//		VenueMatcher.selectByCategory();
//		
//	}
//
//	@Test
//	public void testSelectFromVenueList() {
//		// Redirect System.in to simulate user input
//		String input = "1\n"; // Select the first venue
//		InputStream in = new ByteArrayInputStream(input.getBytes());
//		System.setIn(in);
//
//		VenueMatcher.selectFromVenueList();
//	
//	}
//
//	@Test
//	public void testSelectFromMatchingList() {
//		// Redirect System.in to simulate user input
//		String input = "1\n"; // Select the first option from the matching list
//		InputStream in = new ByteArrayInputStream(input.getBytes());
//		System.setIn(in);
//
//		VenueMatcher.selectFromMatchingList();
//	
//	}
//
//	
//
//}

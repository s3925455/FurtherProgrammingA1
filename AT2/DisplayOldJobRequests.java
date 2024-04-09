package AT2;

import java.util.ArrayList;
import java.util.List;

public class DisplayOldJobRequests {
    private List<String> oldJobRequests = new ArrayList<>();

    public DisplayOldJobRequests(List<String> oldJobRequests) {
        this.oldJobRequests.addAll(oldJobRequests);
    }

    public void displayOldJobRequests() {
        if (oldJobRequests.isEmpty()) {
            System.out.println("\nNo Jobs to Display\n");
        } else {
            for (String oldJobRequest : oldJobRequests) {
                System.out.println(oldJobRequest);
            }
        }
    }

    // Getters and setters if needed

    public List<String> getOldJobRequests() {
        return oldJobRequests;
    }

    public void setOldJobRequests(List<String> oldJobRequests) {
        this.oldJobRequests = oldJobRequests;
    }
}

package sensors;

public class Sensor {
	public Sensor(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	public Snapshot getLastSnapshot() {
		return lastSnapshot;
	}
	public void updateSnapshot(Snapshot snapshot) {
		lastSnapshot = snapshot;
	}

	private String name;
	private Snapshot lastSnapshot;
}

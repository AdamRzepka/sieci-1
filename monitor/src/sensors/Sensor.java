package sensors;

public class Sensor {
	public Sensor(String resource, String metric) {
		this.resource = resource;
		this.metric = metric;
	}
	
	public String getResource() {
		return resource;
	}
	public String getMetric() {
		return metric;
	}

	public float getLastMeasurement() {
		return lastValue;
	}
	public void updateMeasurement(float value) {
		lastValue = value;
	}

	private String resource;
	private String metric;
	private float lastValue;
}

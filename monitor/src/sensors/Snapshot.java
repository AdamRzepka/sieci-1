package sensors;

import java.util.Date;
import java.util.HashMap;

public class Snapshot {
	public Snapshot(Sensor sensor, Date timestamp,
			HashMap<String, Float> measurements) {
		this.sensor = sensor;
		this.timestamp = timestamp;
		this.measurements = measurements;
	}
	public Sensor getSensor() {
		return sensor;
	}
	public Date getTimestamp() {
		return timestamp;
	}
	public float getMeasurement(String metric) throws NoSuchFieldException {
		Float value = measurements.get(metric);
		if (value == null)
			throw new NoSuchFieldException();
		return value.floatValue();
	}
	Sensor sensor;
	Date timestamp;
	HashMap<String, Float> measurements;
}

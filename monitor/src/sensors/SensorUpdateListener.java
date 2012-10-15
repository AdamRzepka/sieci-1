package sensors;

public interface SensorUpdateListener {
	void onUpdate(Sensor sensor);
	void onDisconnected(Sensor sensor);
}

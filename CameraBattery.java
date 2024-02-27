package hw1;

/**
 * Constructs a simulation of a camera battery 
 * @author Ethan Griesman 02/10/2023
 */
public class CameraBattery {
	/*
	 * The total number of settings on the external charger as an integer.
	 */
	public static final int NUM_CHARGER_SETTINGS = 4;
	/*
	 * The rate at which both camera and battery are charged.
	 */
	public static final double CHARGE_RATE = 2.0;
	/*
	 * The default power consumed by the camera battery.
	 */
	public static final double DEFAULT_CAMERA_POWER_CONSUMPTION = 1.0;
	/*
	 * Stores the charge of the battery.
	 */
	private double batteryCharge;
	/*
	 * Stores the charge on the camera battery.
	 */
	private double cameraCharge;
	/*
	 * The charger setting on the external charger which is zero by default.
	 */
	private int chargerSetting;
	/*
	 * Stores the total amount charged by the external charger.
	 */
	private double totalExternalCharge;
	/*
	 * Stores the total amount drained from both battery and camera
	 */
	private double totalDrain;
	/*
	 * Stores the total capacity of the battery
	 */
	private double batteryCap;
	/*
	 * Stores whether the battery is or is not connected to the external charger.
	 */
	private double isConnectedExternal;
	/*
	 * Stores whether the battery is or is not connected to the camera.
	 */
	private double isConnectedCamera;
	/**
	 * Constructs a CameraBattery with a given starting charge and capacity
	 * @param batteryStartingCharge
	 * @param batteryCapacity
	 */
	public CameraBattery(double batteryStartingCharge, double batteryCapacity) {
		
		//Initializes starting battery charge to always be less than the battery capacity
		batteryCharge = Math.min(batteryStartingCharge, batteryCapacity);
		//Initializes battery capacity to be greater than or equal to zero.
		batteryCap = Math.max(batteryCapacity, 0.0);
	}
	/* 
	 * Changes the external charger setting once the button has been pressed
	 * Also loops around once setting 3 has been selected after starting at setting 0.
	 */
	public void buttonPress() {
		chargerSetting = (chargerSetting + 1) % 4;
	}
	/**
	 * Resets the battery monitor once the charger settings have cycled through.
	 */
	public void resetBatteryMonitor() {
		//drain reset
		totalDrain = 0.0;
	}
	/**
	 * Represents the battery being connected to the camera, and not being connected to the external charger.
	 */
	public void moveBatteryCamera() {
		//battery is now connected, charges of camera and battery are now equal.
		cameraCharge = batteryCharge;
		//current state of connection of camera to battery
		isConnectedCamera = 1.0;
		//current state of connection of battery to external charger
		isConnectedExternal = 0.0;
	}
	/** Initializes the power consumption of battery.
	 * @param cameraPowerConsumption
	 */
	public void setCameraPowerConsumption(double cameraPowerConsumption) {
		cameraPowerConsumption = DEFAULT_CAMERA_POWER_CONSUMPTION;
	}
	/*
	 * Represents the battery being removed from the camera, and not being connected to the external charger.
	 */
	public void removeBattery() {
		//camera no longer has charge since it is disconnected
		cameraCharge = 0.0;
		//current state of connection of battery to camera
		isConnectedCamera = 0.0;
		//current state of connection of battery to external charger 
		isConnectedExternal = 0.0;
	}	
	/* 
	 * Represents the battery being connected to the external charger, and not being connected to the camera.
	 * Resets camera charge to default of zero
	 */
	public void moveBatteryExternal(){
		//default charger setting
		chargerSetting = 0;
		//resets camera charge
		cameraCharge = 0.0;
		//current state of connection to camera
		isConnectedCamera = 0.0;
		//current state of connection to external charger
		isConnectedExternal = 1.0;
	}
	/** Returns battery capacity
	 * @return batteryCap
	 */
	public double getBatteryCapacity() {
		return batteryCap;
	}
	/** Returns the current charger setting
	 * @return chargerSetting
	 */
	public int getChargerSetting() {
		return chargerSetting;
	}
	/** Returns current battery charge
	 * @return batteryCharge
	 */
	public double getBatteryCharge() {
		return batteryCharge;
	}
	/** Returns current camera charge
	 * @return cameraCharge
	 */
	public double getCameraCharge() {
		return cameraCharge;
	}
	/** Returns current total amount drained
	 * @return totalDrain
	 */
	public double getTotalDrain() {
		return totalDrain;
	}
	/** Returns the camera power consumption
	 * @return DEFAULT_CAMERA_POWER_CONSUMPTION
	 */
	public double getCameraPowerConsumption() {
		return DEFAULT_CAMERA_POWER_CONSUMPTION; //return private instance variable
	}
	/** The amount drained from the camera and battery only when they are connected.
	 * @param minutes: the minutes the drain function has been operating for 
	 * @return drained 
	 */
	public double drain(double minutes) {
		//amount drained as a function of time, camera power consumption, and whether battery is connected to camera
		double drained = Math.min((minutes*DEFAULT_CAMERA_POWER_CONSUMPTION)*isConnectedCamera, batteryCharge - totalDrain);
		//amount drained from battery and camera cannot be less than zero
		batteryCharge = Math.max(batteryCharge - drained, 0); //batterycharge after drain
		cameraCharge = Math.max(cameraCharge - drained, 0);
		//updates total drain each time
		totalDrain = totalDrain + drained;
		return drained;
	}
	/** The amount charged to both the camera and the battery only when they are connected.
	 * @param minutes:
	 * @return charged:
	 */
	public double cameraCharge(double minutes) {
		//amount charged as a function of time, charge rate, and whether battery is connected to camera
		double charged = Math.min((minutes*CHARGE_RATE)*isConnectedCamera, batteryCap - batteryCharge);
		//amount charged cannot exceed capacity of battery
		batteryCharge = Math.min(batteryCharge + charged, batteryCap);
		cameraCharge = Math.min(cameraCharge + charged, batteryCap);
		return charged;
	}
	/** The amount the battery is charged when it is connected to the external charger with variable setting.
	 * @param minutes:
	 * @return externalCharge: updates the total external charge that cannot exceed the battery capacity.
	 */
	public double externalCharge(double minutes) {
		//amount charged by external charger as a function of time, charge rate, charger setting and whether battery is connected to charger
		double externalCharge = Math.min((minutes*chargerSetting*CHARGE_RATE)*isConnectedExternal, batteryCap - batteryCharge);
		//amount charged cannot exceed capacity of battery
		batteryCharge = Math.min(batteryCharge + externalCharge, batteryCap);
		//updates each time
		totalExternalCharge = Math.min(totalExternalCharge + externalCharge, batteryCap);
		return externalCharge;
	}			

}

	
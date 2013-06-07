package com.sfs.android;

public class PhoneStatus {
	public enum BatteryLevel {
		Powered, Normal, Low
	}

	public enum Ratio {
		ThreeG, WiFi
	}

	public PhoneStatus(BatteryLevel batteryLevel, Ratio ratio) {
		this.batteryLevel = batteryLevel;
		this.ratio = ratio;
	}

	private BatteryLevel batteryLevel;

	private Ratio ratio;

	public BatteryLevel getBatteryLevel() {
		return batteryLevel;
	}

	public Ratio getRatio() {
		return ratio;
	}
}

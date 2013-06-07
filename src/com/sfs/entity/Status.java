package com.sfs.entity;

public class Status {
	
	public enum BatteryLevel {
		Powered, Normal, Low
	}
	
	public enum Ratio {
		ThreeG, WiFi
	}
	
	public Status(BatteryLevel batteryLevel, Ratio ratio) {
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

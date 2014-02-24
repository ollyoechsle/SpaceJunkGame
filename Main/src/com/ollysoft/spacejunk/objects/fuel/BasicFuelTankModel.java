package com.ollysoft.spacejunk.objects.fuel;

public class BasicFuelTankModel implements FuelTankModel {

  public static final int THRUSTER_FUEL_USAGE = 10;
  public static final int MAX_CAPACITY = 1000;

  private float fuel = 0;
  private final FuelTankListener listener;

  public BasicFuelTankModel(float initialFuel, FuelTankListener listener) {
    this.listener = listener;
    this.fuel = Math.min(initialFuel, MAX_CAPACITY);
  }

  @Override
  public void onFuelSpent() {
    fuel = Math.max(0, fuel - THRUSTER_FUEL_USAGE);
    if (fuel <= 0) {
      listener.onFuelTankEmpty();
    }
  }

  @Override
  public float getCapacity() {
    return MAX_CAPACITY;
  }

  @Override
  public float getLevel() {
    return fuel;
  }

}

package com.ollysoft.spacejunk.objects.fuel;

public interface FuelTankModel {

  float getCapacity();

  float getLevel();

  void onFuelSpent();

}

package com.ollysoft.spacejunk.objects.fuel;

import com.ollysoft.spacejunk.objects.util.LabelView;
import com.ollysoft.spacejunk.util.Assets;

public class FuelTankView extends LabelView {

  private final FuelTankModel model;
  private float displayLevel;

  public FuelTankView(Assets assets, FuelTankModel model) {
    super(assets);
    this.model = model;
    this.displayLevel = model.getLevel();
  }

  @Override
  public void act(float delta) {
    super.act(delta);
    displayLevel = model.getLevel();
  }

  @Override
  public String getText() {
    return "" + (int) displayLevel;
  }

}

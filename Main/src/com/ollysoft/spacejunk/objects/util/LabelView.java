package com.ollysoft.spacejunk.objects.util;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.ollysoft.spacejunk.util.Assets;

public abstract class LabelView extends Label {

  public LabelView(Assets assets) {
    super("", new Label.LabelStyle(assets.bigFont, new Color(1, 1, 1, 1)));
  }

  @Override
  public void act(float delta) {
    this.setText(getText());
  }

  public abstract String getText();

}

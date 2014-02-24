package com.ollysoft.spacejunk.objects.props;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.ollysoft.spacejunk.util.Assets;

public class Boulder extends Actor {

  private TextureRegion texture;
  private float speed;

  public Boulder(Assets assets, int x, float speed) {
    this.speed = speed;
    this.texture = new TextureRegion(assets.boulder);
    this.setWidth(speed * 2);
    this.setHeight(speed * 2);
    this.setX(x);
    this.setY(MathUtils.random(-getHeight(), Gdx.graphics.getHeight()));
  }

  @Override
  public void act(float delta) {
    this.setY(getY() - (speed * delta));
    this.setRotation(getRotation() + ((105 - speed) * delta));
    if (getY() < -getHeight()) {
      setY(Gdx.graphics.getHeight());
    }
  }

  @Override
  public void draw(SpriteBatch batch, float parentAlpha) {
    float lightness = speed / 100f;
    batch.setColor(new Color(lightness, lightness, lightness, 1));
    batch.draw(texture, getX(), getY(), getWidth() / 2, getHeight() / 2, getWidth(), getHeight(), 1, 1, getRotation());
  }
}

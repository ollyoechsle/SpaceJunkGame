package com.ollysoft.spacejunk.objects.score;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.ollysoft.spacejunk.util.Assets;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.alpha;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveBy;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.parallel;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.removeActor;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

/**
 * com.ollysoft.spacejunk.objects.util
 */
public class PointsLabel extends Actor {

  private final BitmapFont bigFont;
  private final String message;

  public PointsLabel(Assets assets, float x, float y, String message) {
    this.message = message;
    bigFont = assets.bigFont;
    x -= (bigFont.getBounds(message).width / 2);
    this.setX(x);
    this.setY(y);
    addAction(sequence(
        parallel(
            moveBy(0, 150, 2f),
            alpha(0f, 2)
        ),
        removeActor()
    ));
  }

  @Override
  public void draw(SpriteBatch batch, float parentAlpha) {
    bigFont.setColor(getColor());
    bigFont.draw(batch, message, getX(), getY());
    bigFont.setColor(1, 1, 1, 1);
  }

}

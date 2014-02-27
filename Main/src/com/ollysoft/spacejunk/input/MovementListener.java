package com.ollysoft.spacejunk.input;

import com.ollysoft.spacejunk.util.Movement;

public interface MovementListener {

  void move(Movement movement);

  void togglePaused();

}

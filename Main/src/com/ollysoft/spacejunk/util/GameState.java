package com.ollysoft.spacejunk.util;

public enum GameState {

  LOADING {
    @Override
    public boolean canMove() {
      return false;
    }
  },
  PLAYING {
    @Override
    public boolean canMove() {
      return true;
    }
  },
  PAUSED {
    @Override
    public boolean canMove() {
      return false;
    }
  },
  OVER {
    @Override
    public boolean canMove() {
      return false;
    }
  };

  public abstract boolean canMove();

}

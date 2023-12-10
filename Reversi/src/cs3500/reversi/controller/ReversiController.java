package cs3500.reversi.controller;

import cs3500.reversi.model.ModelFeatures;
import cs3500.reversi.view.MoveFeatures;

/**
 * Interface to consolidate interfaces. MoveFeatures and ModelFeatures are
 * entirely separate interfaces that shouldn't be forced together, but any
 * HexReversiController needs both.
 */
public interface ReversiController extends MoveFeatures, ModelFeatures {
  //no additional features
}

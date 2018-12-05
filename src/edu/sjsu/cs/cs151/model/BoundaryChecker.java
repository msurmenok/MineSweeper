package edu.sjsu.cs.cs151.model;

/** 
 * Boundary Check Proxy
 * To make sure selected cell index is inside mineField boundary
 */
public interface BoundaryChecker {
  boolean boundaryProxy(int h, int w);
}

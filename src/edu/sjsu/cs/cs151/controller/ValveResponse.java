package edu.sjsu.cs.cs151.controller;

/**
 * Used to distinguish if subclass of Message was processed by the right Valve
 */
public enum ValveResponse {
	MISS, EXECUTED, FINISH
};

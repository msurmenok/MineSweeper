package edu.sjsu.cs.cs151;

public class RightClickMessage extends Message {
	int height;
	int width;

	public RightClickMessage(int height, int width) {
		this.height = height;
		this.width = width;
	}
}

package edu.sjsu.cs.cs151;

public class LeftClickMessage extends Message {
	int height;
	int width;

	public LeftClickMessage(int height, int width) {
		this.height = height;
		this.width = width;
	}
}

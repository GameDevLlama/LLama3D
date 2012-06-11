package com.llama3d.elements.touch;

public interface TouchListener {

	public void pointerDown(int pointerID, double pointerX, double pointerY);

	public void pointerMove(int pointerID, double speedX, double speedY);

	public void pointerRelease(int pointerID);

}

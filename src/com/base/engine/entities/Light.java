package com.base.engine.entities;

import org.lwjgl.util.vector.Vector3f;
/*A class that represents a light*/
public class Light {
	private Vector3f position; // Position of the light
	private Vector3f colour; // Color of the light
	
	
		//A constructor
	public Light(Vector3f position, Vector3f colour) {
		this.position = position;
		this.colour = colour;
	}


    // Getters and setters
	public Vector3f getPosition() {
		return position;
	}



	public void setPosition(Vector3f position) {
		this.position = position;
	}



	public Vector3f getColour() {
		return colour;
	}



	public void setColour(Vector3f colour) {
		this.colour = colour;
	}
	
	
	
	
}

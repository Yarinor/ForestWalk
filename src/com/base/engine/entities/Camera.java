package com.base.engine.entities;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;
/*The class creates an instance of a camera through which the user viewes the 3D world*/
public class Camera {
	
	private float distanceFromPlayer =50;//distance of the camera from the player, the zoom
	private float angleAroundPlayer = 0;////angle around the player
    private Vector3f position = new Vector3f(0,5,0);// position of the camera
    private float pitch;// Rotation around the x,y and z axis.
    private float yaw ; // how much left or right the camera is aiming
    private float roll;// how much the camera is tilted to one side
    
    private Player player;
    // Contractor
    public Camera(Player player){
    	this.player = player;
    }
     /*Moves the camera around. Calls the methods below,
      * and calculates the position of the camera accordingly*/
    public void move(){
       calculateZoom();
       calculatePitch();
       calculateAngleAroundPlayer();
       float horizontalDistance = calculateHorizontalDistance();
       float verticalDistance = calculateVerticalDistance();
       calculateCameraPosition(horizontalDistance,verticalDistance);
       this.yaw = 180 - (player.getRotY() + angleAroundPlayer);
    }
 //getters and setters
    public Vector3f getPosition() {
        return position;
    }
 
    public float getPitch() {
        return pitch;
    }
 
    public float getYaw() {
        return yaw;
    }
 
    public float getRoll() {
        return roll;
    }
    //Calculates the actual position of the camera(the actual x,y and z positon of the camera)
    private void calculateCameraPosition(float horizontalDistance, float verticalDistance){
    	float theta = player.getRotY() + angleAroundPlayer;
    	float offsetX = (float) (horizontalDistance * Math.sin(Math.toRadians(theta)));
    	float offsetZ = (float) (horizontalDistance * Math.cos(Math.toRadians(theta)));
    	position.x = player.getPosition().x - offsetX;
    	position.z = player.getPosition().z - offsetZ;
    	position.y = player.getPosition().y + verticalDistance;
    }
    /*Calculates the horizontal distance of the camera from the player*/
    private float calculateHorizontalDistance(){
    	float hD = (float) (distanceFromPlayer * Math.cos(Math.toRadians(pitch)));
		if(hD < 0)
			hD = 0;
		return hD;
    }
    /*Calculates the vertical distance of the camera from the player*/
    private float calculateVerticalDistance(){
    	float vD = (float) (distanceFromPlayer * Math.sin(Math.toRadians(pitch)));
		if(vD < 0)
			vD = 0;
		return vD;
    }
    
    /* Checks the input for the zoom variable. checks how much the user
    moves the mouse wheel*/
    private void calculateZoom(){
    	float zoomLevel = Mouse.getDWheel() * 0.1f;
    	distanceFromPlayer -= zoomLevel;
    }
    
    /*Calculates the pitch. limited to 90 degrees*/
    
    private void calculatePitch(){
    	float pitchChange = Mouse.getDY() * 0.1f;
		pitch -= pitchChange;
		if(pitch < 0)
			pitch = 0;
		else if(pitch > 90)
			pitch = 90;
	
    }
     /*Calculates how much the camera should move around the player.
      happens if the left mouse button is clicked*/
    private void calculateAngleAroundPlayer(){
    	if(Mouse.isButtonDown(0)){
    		float angleChange = Mouse.getDX() * 0.3f;
    		angleAroundPlayer -= angleChange;
    	}
    }
     
 
}
package com.base.engine.entities;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

import com.base.engine.models.TexturedModel;
import com.base.engine.render.DisplayManager;

/*This class creates an object which represents the player*/
public class Player extends Entity{
	
	private static final float RUN_SPEED = 20;//The speed of the player movement in units per second
	private static final float TURN_SPEED = 160;// How fast the player turns around in degrees per second
	private static final float GRAVITY = -50;//Controls the gravity of the player to the terrain 
	private static final float JUMP_POWER = 30;//Determines how high the player jumps
	private static final float TERRAIN_HEIGHT = 0;// Used to check terrain height on which the player moves
	
	private float currentSpeed = 0;//Used to calculate the current movement speed of the player
	private float currentTurnSpeed = 0;//Used to calculate the current turn speed of the player
	private float upwardsSpeed = 0;// Used to calculate how much the y position is going to increase per second 
	
	private boolean isInAir =  false;//A boolean flag which shows if the player is in the air when jumping
   /*A constructor */
	public Player(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale) {
		super(model, position, rotX, rotY, rotZ, scale);
	}
	/*Moves the player around. Calculates user inputs and moves the player accordingly*/
	public void move(){
		checkInputs();
		super.increaseRotation(0, currentTurnSpeed * DisplayManager.getFrameTimeSeconds(), 0);
		float distance = currentSpeed * DisplayManager.getFrameTimeSeconds(); 
		float dx = (float) (distance * Math.sin(Math.toRadians(super.getRotY())));
		float dz = (float) (distance * Math.cos(Math.toRadians(super.getRotY())));
		super.increasePosition(dx,0 , dz);
		upwardsSpeed += GRAVITY * DisplayManager.getFrameTimeSeconds();
		super.increasePosition(0, upwardsSpeed * DisplayManager.getFrameTimeSeconds(), 0);
		if(super.getPosition().y<TERRAIN_HEIGHT){
			upwardsSpeed = 0;
			isInAir = false;
			super.getPosition().y = TERRAIN_HEIGHT;
		}
			
	}
	/*Makes the player jump above the terrain. If the player is in the air he is not allowed to jump again*/
	private void jump(){
		if(!isInAir){
			this.upwardsSpeed = JUMP_POWER;
			isInAir=true;
		}
		
	}
	
	/*Checks the keyboard inputs to control how the player moves*/
	private void checkInputs(){
		if(Keyboard.isKeyDown(Keyboard.KEY_W)){
			this.currentSpeed = -RUN_SPEED;
		}else if(Keyboard.isKeyDown(Keyboard.KEY_S)){
			this.currentSpeed = RUN_SPEED;
		}else{
			currentSpeed=0;
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_D)){
			this.currentTurnSpeed = -TURN_SPEED;
		}else if(Keyboard.isKeyDown(Keyboard.KEY_A)){
			this.currentTurnSpeed = TURN_SPEED;
		}else{
			currentTurnSpeed=0;
		}
		
		if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)){
			jump();
		}
	}

}

	package com.base.engine.render;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.PixelFormat;
//A class for managing the display
public class DisplayManager {
	
	public static final int WIDTH = 2000;//Display's width
	public static final int HEIGHT = 1000;//Display's height
	public static final int FPS_CAP =120;//Fps cap of the game
	
	private static long lastFrameTime;//the time of the prior frame
	private static float delta;// the delta time(time between two frames per second)
	//Creates the display and sets the last frame time
	public static void createDisplay(){
		ContextAttribs attribs = new ContextAttribs(3,2).withForwardCompatible(true).withProfileCore(true);
		try {
			Display.setDisplayMode(new DisplayMode(WIDTH,HEIGHT));
			Display.create(new PixelFormat(), attribs);
			Display.setTitle("ForestWalk");
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
		GL11.glViewport(0, 0, WIDTH, HEIGHT);
		lastFrameTime = getCurrentTime();
	}
	//Synchronizes with the fps cap, updates the display and calculates the delta time(time between two frames per second)
	public static void updateDisplay(){
		
		Display.sync(FPS_CAP);
		Display.update();
		long currentFrameTime = getCurrentTime();
		delta = (currentFrameTime - lastFrameTime)/1000f;
		lastFrameTime = currentFrameTime;
	}
	//getter
	public static float getFrameTimeSeconds(){
		return delta;
	}
	
	//closes the display
	public static void closeDisplay(){
		
		Display.destroy();
	}
	//calculates current time
	private static long getCurrentTime(){
		return Sys.getTime()*1000/Sys.getTimerResolution();
	}
	

}

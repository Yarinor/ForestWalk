package com.base.engine.audio;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.LWJGLException;
import org.lwjgl.openal.AL;
import org.lwjgl.openal.AL10;
import org.lwjgl.util.WaveData;
//This class handles all the audio
public class AudioMaster {
	
	public static List<Integer> buffers = new ArrayList<Integer>();//creates a list of buffers
	
	//Initializes AL
	public static void init(){
		
		try {
			AL.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
	}
	//Loads the sound file unto a sound buffer and returns it
	public static int loadSound(String file){
		int buffer = AL10.alGenBuffers();
		buffers.add(buffer);
		WaveData waveFile = WaveData.create(file);
		AL10.alBufferData(buffer, waveFile.format, waveFile.data, waveFile.samplerate);
		waveFile.dispose();
		return buffer;
	}
	//Sets listener data- the position and the velocity of the listener in the 3d world
	public static void setListenerData(){
		AL10.alListener3f(AL10.AL_POSITION, 0, 0, 0);
		AL10.alListener3f(AL10.AL_VELOCITY, 0, 0, 0);
	}
	//Cleans up the buffers after use and deletes AL
	public static void cleanUp(){
		for(int buffer : buffers){
			AL10.alDeleteBuffers(buffer);
		}
		AL.destroy();
	}

}

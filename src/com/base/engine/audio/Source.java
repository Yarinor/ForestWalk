
package com.base.engine.audio;

import org.lwjgl.openal.AL10;
//The class represents a sound source
public class Source {

	private int sourceId;//ID of the source
	
	
	//Getter
	public int getSourceId() {
		return sourceId;
	}
   //Constructor
	public Source(){
		sourceId = AL10.alGenSources();
		AL10.alSourcef(sourceId,AL10.AL_GAIN,1);
		AL10.alSourcef(sourceId,AL10.AL_PITCH,1);
		AL10.alSource3f(sourceId, AL10.AL_POSITION, 0, 0, 0);
		
	}
	//Plays the sound source
	public void play(int buffer){
		AL10.alSourcei(sourceId,AL10.AL_BUFFER,buffer);
		AL10.alSourcePlay(sourceId);
		
	}
	//Deletes the sound source
	public void delete(){
		AL10.alDeleteSources(sourceId);
	}
}

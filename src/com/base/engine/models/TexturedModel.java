package com.base.engine.models;

import com.base.engine.textures.ModelTexture;
//A class which contains both the model and the texture
public class TexturedModel {
	
	private RawModel rawModel;// the model
	private ModelTexture texture; // the model texture
	//constructor
	public TexturedModel(RawModel model, ModelTexture texture){
		this.rawModel = model;
		this.texture = texture;
	}
   //getters
	public RawModel getRawModel() {
		return rawModel;
	}


	public ModelTexture getTexture() {
		return texture;
	}

	

}

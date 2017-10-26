package com.base.engine.models;

//The class represents a 3D model stored in memory
public class RawModel {
	private int vaoID;//The ID of the VAO once the model is stored in memory
	private int vertexCount;//How many vertices in the 3D model
	//A constructor
	public RawModel(int vaoID, int vertexCount){
		this.vaoID = vaoID;
		this.vertexCount = vertexCount;
		
	}
   //Getters and setters
	public int getVaoID() {
		return vaoID;
	}

	public int getVertexCount() {
		return vertexCount;
	}
	
	

}

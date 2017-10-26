package com.base.engine.shaders;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import com.base.engine.entities.Camera;
import com.base.engine.entities.Light;
import com.base.engine.toolbox.Maths;
// a class that implements the shaders, used to create the static models
public class StaticShader extends ShaderProgram{
	
	private static final String VERTEX_FILE = "src/com/base/engine/shaders/vertexShader.txt"; //vertex shader file
	private static final String FRAGMENT_FILE = "src/com/base/engine/shaders/fragmentShader.txt";// fragment shader file
	
	private int location_transformationMatrix;// the location of the uniform in the shader code
	private int location_projectionMatrix;
	private int location_viewMatrix;
	private int location_lightPosition;
	private int location_lightColour;
	private int location_shineDamper;
	private int location_reflectivity;
	private int location_useFakeLighting;
	private int location_skyColour;


    //A constructor
	public StaticShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
		
	}
    // binds the attributes of the VAOs to the attributes of the vertex shder
	@Override
	protected void bindAttrributes() {
		super.bindAttribute(0, "position");
		super.bindAttribute(1, "textureCoords");
		super.bindAttribute(2, "normal");
		
	}
   // get the location of the uniform location in the shader code
	@Override
	protected void getAllUniformLocations() {
		 location_transformationMatrix = super.getUniformLocation("transformationMatrix");
		 location_projectionMatrix = super.getUniformLocation("projectionMatrix");
		 location_viewMatrix = super.getUniformLocation("viewMatrix");
		 location_lightPosition = super.getUniformLocation("lightPosition");
		 location_lightColour = super.getUniformLocation("lightColour");
		 location_shineDamper = super.getUniformLocation("shineDamper");
		 location_reflectivity = super.getUniformLocation("reflectivity");
		 location_useFakeLighting = super.getUniformLocation("useFakeLighting");
		 location_skyColour = super.getUniformLocation("skyColour");
	}
	//loads a color to the skyColour uniform variable
	public void loadSkyColour(float r, float g, float b){
		super.loadVector(location_skyColour, new Vector3f(r,g,b));
	}
	//loads a boolean to the fakeLighting uniform variable
	public void loadFakeLightingVariable(boolean useFake){
		super.loadBoolean(location_useFakeLighting, useFake);
	}
	//loads floats to the shineDamper and reflectivity uniform variables
	public void loadShineVariables(float damper,float reflectivity){
		super.loadFloat(location_shineDamper, damper);
		super.loadFloat(location_reflectivity, reflectivity);
	}
	//loads a transformation matrix to the uniform variable
	public void loadTransformationMatrix(Matrix4f matrix){
		super.loadMatrix(location_transformationMatrix, matrix);
	}
	//loads a light color and position to the respective uniform variables
	public void loadLight(Light light){
		super.loadVector(location_lightPosition, light.getPosition());
		super.loadVector(location_lightColour, light.getColour());
	}
	//loads the camera, creates a view matrix from the camera and loads the matrix to the view matrix uniform variable
	 public void loadViewMatrix(Camera camera){
	        Matrix4f viewMatrix = Maths.createViewMatrix(camera);
	        super.loadMatrix(location_viewMatrix, viewMatrix);
	    }
	//loads the projection matrix to the projection matrix uniform variable
	public void loadProjectionMatrix(Matrix4f projection){
		super.loadMatrix(location_projectionMatrix, projection);
	}

}

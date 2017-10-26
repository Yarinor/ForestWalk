package com.base.engine.shaders;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.util.Vector;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;


// A class the enables to access the shader program written in GLSL from the java code
public abstract class ShaderProgram {

	private int programID;//Program ID
	private int vertexShaderID;//ID of the vertex shader
	private int fragmentShaderID;//ID of the fragment shader
	private static FloatBuffer matrixBuffer = BufferUtils.createFloatBuffer(16);
	
	//A constructor
	public ShaderProgram(String vertexFile,String fragmentFile){
		vertexShaderID = loadShader(vertexFile,GL20.GL_VERTEX_SHADER);
		fragmentShaderID = loadShader(fragmentFile,GL20.GL_FRAGMENT_SHADER);
		programID = GL20.glCreateProgram();
		GL20.glAttachShader(programID, vertexShaderID);
		GL20.glAttachShader(programID, fragmentShaderID);
		bindAttrributes();
		GL20.glLinkProgram(programID);
		GL20.glValidateProgram(programID);
		getAllUniformLocations();
	}
	//starts the program
	public void start(){
		GL20.glUseProgram(programID);
	}
	//stops the program
	public void stop(){
		GL20.glUseProgram(0);
	}
	//cleans up all the shaders and deletes the shader program
	public void cleanUp(){
		stop();
		GL20.glDetachShader(programID, vertexShaderID);
		GL20.glDetachShader(programID, fragmentShaderID );
		GL20.glDeleteShader(vertexShaderID);
		GL20.glDeleteShader(fragmentShaderID);
		GL20.glDeleteProgram(programID);
	}
	
	protected abstract void getAllUniformLocations();
	//Gets the uniform variable name in the shader code and returns the location of that variable
	protected int getUniformLocation(String uniformName){
		return GL20.glGetUniformLocation(programID,uniformName);
	}
	
	protected abstract void bindAttrributes();//links up the inputs to the shader programs to one of the attributes of the VAO
	//takes the number of the attribute list in the VAO that we want to bind and also the variable name in the shader code that we want to bind that attribute to.
	protected void bindAttribute(int attribute , String variableName ){
		GL20.glBindAttribLocation(programID, attribute, variableName);
	}
	//Load a uniform variable of type float
	protected void loadFloat(int location, float value){
		GL20.glUniform1f(location, value);
	}
	//Load a uniform variable of type vector
	protected void loadVector(int location, Vector3f vector){
		GL20.glUniform3f(location, vector.x, vector.y, vector.z);
	}
	//Load a uniform variable of type boolean
	protected void loadBoolean(int location, boolean value){
		float toLoad = 0;
		if(value){
			toLoad = 1;
			
		}
		GL20.glUniform1f(location, toLoad);
	}
	//Load a uniform variable of type matrix4
	protected void loadMatrix(int location, Matrix4f matrix){
		matrix.store(matrixBuffer);
		matrixBuffer.flip();
		GL20.glUniformMatrix4(location, false, matrixBuffer);
		
	}
	
	//Loads the shader code source files with a type that indicates if it is a vertex or a fragment shader
	private static int loadShader(String file, int type){
		StringBuilder shaderSource = new StringBuilder();
		try
		{
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line;
			while((line = reader.readLine()) !=null){
				shaderSource.append(line).append("\n");
			}
			reader.close();
		}catch(IOException 	e){
			System.err.println("Could not read file!");
			e.printStackTrace();
			System.exit(-1);
		}
		int shaderID = GL20.glCreateShader(type);
		GL20.glShaderSource(shaderID, shaderSource);
		GL20.glCompileShader(shaderID);
		if (GL20.glGetShaderi(shaderID, GL20.GL_COMPILE_STATUS)== GL11.GL_FALSE){
			System.out.println(GL20.glGetShaderInfoLog(shaderID, 500));
			System.err.println("Could not compile shader.");
			System.exit(-1);
		}
		return shaderID;
	}
}

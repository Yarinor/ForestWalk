package com.base.engine.render;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Matrix4f;

import com.base.engine.entities.Camera;
import com.base.engine.entities.Entity;
import com.base.engine.entities.Light;
import com.base.engine.models.TexturedModel;
import com.base.engine.shaders.StaticShader;
import com.base.engine.shaders.TerrainShader;
import com.base.engine.terrains.Terrain;
//A class that handles all the rendering code
public class MasterRenderer {
	
	private static final float FOV = 70;
    private static final float NEAR_PLANE = 0.1f;
    private static final float FAR_PLANE = 1000;
    
    private static final float RED = 0.3f;
    private static final float GREEN = 1.0f;
    private static final float BLUE = 1.0f;
    
    
    private Matrix4f projectionMatrix;

	private StaticShader shader = new StaticShader();//uses his own static shader
	private EntityRenderer renderer;//uses an entity renderer to render entities
	
	private TerrainRenderer terrainRenderer;//used to render the terrain
	private TerrainShader terrainShader = new TerrainShader();//uses a terrain shader
	
	
	private Map<TexturedModel, List<Entity>> entities = new HashMap<TexturedModel, List<Entity>>();//uses a list of entities
	private List<Terrain> terrains = new ArrayList<Terrain>();// uses a list of terrains
	
	//A constructor
	public MasterRenderer(){
		 enableCulling();
		createProjectionMatrix();
		renderer = new EntityRenderer(shader,projectionMatrix);
		terrainRenderer = new TerrainRenderer(terrainShader,projectionMatrix);
	}
	//Enables model face culling
	public static void enableCulling(){
		GL11.glDisable(GL11.GL_CULL_FACE);
 
	}
	//Disables model face culling
	public static void disableCulling(){
		GL11.glEnable(GL11.GL_CULL_FACE);
    	GL11.glCullFace(GL11.GL_BACK);
	}
	//Main render method, responsible for all the rendering
	public void render(Light sun, Camera camera){
		prepare();
		shader.start();
		shader.loadSkyColour(RED, GREEN, BLUE);
		shader.loadLight(sun);
		shader.loadViewMatrix(camera);
		renderer.render(entities);	
		shader.stop();
		terrainShader.start();
		terrainShader.loadSkyColour(RED,GREEN, BLUE);
		terrainShader.loadLight(sun);
		terrainShader.loadViewMatrix(camera);
		terrainRenderer.render(terrains);
		terrainShader.stop();
		terrains.clear();
		entities.clear();
	}
	//Add terrain to the list
	public void processTerrain(Terrain terrain){
		terrains.add(terrain);
	}
	//Adds a fully textured entity model to a batch list and adds this list with the model to the entities list
	public void processEntity(Entity entity){
		TexturedModel entityModel = entity.getModel();
		List<Entity> batch = entities.get(entityModel);
		if(batch != null){
			batch.add(entity);
			
		}else{
			List<Entity> newBatch = new ArrayList<Entity>();
			newBatch.add(entity);
			entities.put(entityModel, newBatch);
		}
	}
	//cleans up the shaders after using them
	public void cleanUp(){
		shader.cleanUp();
		terrainShader.cleanUp();
	}
	
	//Prepares the screen for rendering
	 public void prepare() {
	        GL11.glEnable(GL11.GL_DEPTH_TEST);
	        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT|GL11.GL_DEPTH_BUFFER_BIT);
	        GL11.glClearColor(RED, GREEN, BLUE, 1);
	    }
	 //Creates the projection matrix
	  private void createProjectionMatrix(){
	        float aspectRatio = (float) Display.getWidth() / (float) Display.getHeight();
	        float y_scale = (float) ((1f / Math.tan(Math.toRadians(FOV / 2f))) * aspectRatio);
	        float x_scale = y_scale / aspectRatio;
	        float frustum_length = FAR_PLANE - NEAR_PLANE;
	 
	        projectionMatrix = new Matrix4f();
	        projectionMatrix.m00 = x_scale;
	        projectionMatrix.m11 = y_scale;
	        projectionMatrix.m22 = -((FAR_PLANE + NEAR_PLANE) / frustum_length);
	        projectionMatrix.m23 = -1;
	        projectionMatrix.m32 = -((2 * NEAR_PLANE * FAR_PLANE) / frustum_length);
	        projectionMatrix.m33 = 0;
	    }
	    

}




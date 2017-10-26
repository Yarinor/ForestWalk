package com.base.engine.render;

import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;

import com.base.engine.entities.Entity;
import com.base.engine.models.RawModel;
import com.base.engine.models.TexturedModel;
import com.base.engine.shaders.StaticShader;
import com.base.engine.textures.ModelTexture;
import com.base.engine.toolbox.Maths;
//A class which handles entity rendering
public class EntityRenderer {
	

     
   
    private StaticShader shader;//uses his own static shader
     //A constructor
    public EntityRenderer(StaticShader shader,Matrix4f projectionMatrix){
    	this.shader = shader;
        shader.start();
        shader.loadProjectionMatrix(projectionMatrix);
        shader.stop();
    }
 
   //Used to rendered the entities with the textures
    public void render(Map<TexturedModel,List<Entity>> entities){
    	for(TexturedModel model:entities.keySet()){
    		 prepareTexturedModel(model);
    		 List<Entity>batch = entities.get(model);
    		 for(Entity entity:batch){
    			 prepareInstance(entity);
    			 GL11.glDrawElements(GL11.GL_TRIANGLES, model.getRawModel().getVertexCount(), 
    					 GL11.GL_UNSIGNED_INT, 0);
    		 }
    		 unbindTexturedModel();
    	}
    }
    //Takes the textured models to prepare for rendering
    private void prepareTexturedModel(TexturedModel model){
    	RawModel rawModel = model.getRawModel();
        GL30.glBindVertexArray(rawModel.getVaoID());
        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);
        GL20.glEnableVertexAttribArray(2);
        ModelTexture texture = model.getTexture();
        if(texture.isHasTransparency()){
        	MasterRenderer.disableCulling();
        }
        shader.loadFakeLightingVariable(texture.isUseFakeLighting());
        shader.loadShineVariables(texture.getShineDamper(), texture.getReflectivity());
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, model.getTexture().getID());
    }
    //prepares the instances for the entities of each of the textured models
    private void prepareInstance(Entity entity){
    	Matrix4f transformationMatrix = Maths.createTransformationMatrix(entity.getPosition(),
        entity.getRotX(), entity.getRotY(), entity.getRotZ(), entity.getScale());
        shader.loadTransformationMatrix(transformationMatrix);
    }
    //unbinds the texture models once all the entities that use that texture model finished being rendered
    private void  unbindTexturedModel(){
    	MasterRenderer.enableCulling();
    	GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);
        GL20.glDisableVertexAttribArray(2);
        GL30.glBindVertexArray(0);
    	
    }
 
    
     
  
 
}
package com.base.engine.main;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.lwjgl.openal.AL10;
import org.lwjgl.opengl.Display;

import org.lwjgl.util.vector.Vector3f;

import com.base.engine.audio.AudioMaster;
import com.base.engine.audio.Source;
import com.base.engine.entities.Camera;
import com.base.engine.entities.Entity;
import com.base.engine.entities.Light;
import com.base.engine.entities.Player;
import com.base.engine.models.RawModel;
import com.base.engine.models.TexturedModel;
import com.base.engine.objConverter.ModelData;
import com.base.engine.objConverter.OBJFileLoader;
import com.base.engine.render.DisplayManager;
import com.base.engine.render.Loader;
import com.base.engine.render.MasterRenderer;
import com.base.engine.render.OBJLoader;
import com.base.engine.render.EntityRenderer;
import com.base.engine.shaders.StaticShader;
import com.base.engine.terrains.Terrain;
import com.base.engine.textures.ModelTexture;
//A class that executes all the code written to an executable program
public class MainGameLoop {
	  public static void main(String[] args) {
	        DisplayManager.createDisplay();//creates the display 
	        Loader loader = new Loader();//Creates a new model loader
	        AudioMaster.init();//Initializes the AudioMaster
	        AudioMaster.setListenerData();//sets the the data of the listener
	        
	        int buffer = AudioMaster.loadSound("com/base/engine/audio/horizon.wav");//Buffer integer. Receives the AL buffer number with the loaded wave file
	        Source source = new Source();//Creates a new AL sound source
	        int buffer2 = AudioMaster.loadSound("com/base/engine/audio/TalkingForest.wav");
	        Source source2 = new Source();
	        int buffer3 = AudioMaster.loadSound("com/base/engine/audio/wander.wav");//Buffer integer. Receives the AL buffer number with the loaded wave file
	        Source source3 = new Source();//Creates a new AL sound source
	        int buffer4 = AudioMaster.loadSound("com/base/engine/audio/harmony.wav");
	        Source source4 = new Source();
	        source.play(buffer);//Plays the AL buffer
	        boolean track1Playing=true;//Flags if the track is currently playing
	        boolean track2Playing=false;
	        boolean track3Playing=false;
	        boolean track4Playing=false;
	        
	        ModelData tree = OBJFileLoader.loadOBJ("tree5");//Loads the obj file, and returns a new processed and arranged model data
	        ModelData grass = OBJFileLoader.loadOBJ("grassModel");
	        ModelData house = OBJFileLoader.loadOBJ("WoodenCabinObj");
	        ModelData boy = OBJFileLoader.loadOBJ("boy");
	        ModelData fern = OBJFileLoader.loadOBJ("fern");
	        
	        
	        
	        
	     
	        RawModel treeModel = loader.loadToVAO(tree.getVertices(), tree.getTextureCoords(), tree.getNormals(), tree.getIndices());// loads the model data to vao
	        RawModel grassModel = loader.loadToVAO(grass.getVertices(), grass.getTextureCoords(), grass.getNormals(), grass.getIndices());
	        RawModel fernModel = loader.loadToVAO(fern.getVertices(), fern.getTextureCoords(), fern.getNormals(), fern.getIndices());
	        RawModel houseModel = loader.loadToVAO(house.getVertices(), house.getTextureCoords(), house.getNormals(), house.getIndices());
	        RawModel boyModel = loader.loadToVAO(boy.getVertices(), boy.getTextureCoords(), boy.getNormals(), boy.getIndices());
	        
	        
	        
	        TexturedModel staticTree = new TexturedModel(treeModel,new ModelTexture
	        (loader.loadTexture("tree5")));//Loads the texture unto the model and returns a new textured model
	        TexturedModel staticGrass = new TexturedModel(grassModel,new ModelTexture
	    	(loader.loadTexture("grassTexture")));
	        TexturedModel staticFern= new TexturedModel(fernModel,new ModelTexture
	    	    	(loader.loadTexture("fern")));
	        staticGrass.getTexture().setHasTransparency(true);
	        TexturedModel staticFlower = new TexturedModel(grassModel,new ModelTexture
	    	(loader.loadTexture("flower")));
	        TexturedModel staticHouse = new TexturedModel(houseModel,new ModelTexture
	    	    	(loader.loadTexture("WoodCabinDif")));
	        TexturedModel movableBoy = new TexturedModel(boyModel,new ModelTexture
	    	    	(loader.loadTexture("boy")));
	        
	        
	        
	        
	        
	    	staticGrass.getTexture().setHasTransparency(true);//sets transparency for some part of the texture of the model to hide texture parts which should not be displayed
	    	staticFlower.getTexture().setHasTransparency(true);
	    	staticGrass.getTexture().setUseFakeLighting(true);// Uses fake lighting to make some models self illuminating
	    	staticFlower.getTexture().setUseFakeLighting(true);
	    	staticFern.getTexture().setUseFakeLighting(true);
	    	staticTree.getTexture().setShineDamper(10);
	    	staticTree.getTexture().setReflectivity(1);
	    	staticHouse.getTexture().setShineDamper(10);
	    	staticHouse.getTexture().setReflectivity(1);
	    	movableBoy.getTexture().setShineDamper(10);
	    	
	        
	        
	        List<Entity> entities = new ArrayList<Entity>();//Creates a new list of entities
	        Random random = new Random();// Creates a new random to randomize the position of the entity
	        
	       
	            entities.add(new Entity(staticHouse, new Vector3f(50,1,-700),0,0,0,2));// Adds a new entity to the list in a certain position
	            
	            
	           
	            for(int i=0;i<50;i++){//loops to add multiple entities
		            entities.add(new Entity(staticFern, new Vector3f(random.nextFloat()*1500 -800,0,random.nextFloat() * -600),0,0,0,2));
		            
		           
		        }
	      
	        for(int i=0;i<200;i++){
	            entities.add(new Entity(staticTree, new Vector3f(random.nextFloat()*1500 -800,0,random.nextFloat() * -600),0,0,0,8));
	            entities.add(new Entity(staticFlower, new Vector3f(random.nextFloat()*1500 -800,0,random.nextFloat() * -600),0,0,0,5));
	           
	        }
	        for(int i=0;i<20000;i++){
	 
	            entities.add(new Entity(staticGrass, new Vector3f(random.nextFloat()*1500 -800,0,random.nextFloat() * -775),0,0,0,5));
	        }
	         
	      
	         
	        
	        Light light = new Light(new Vector3f(3000,4000,3000),new Vector3f(1,1,1));//Adds the light source of the world
	        
	        Terrain terrain = new Terrain(-1,-1,loader,new ModelTexture(loader.loadTexture("grass")));// Creates a new terrain instance
	        Terrain terrain2 = new Terrain(0,-1,loader,new ModelTexture(loader.loadTexture("grass")));
	        Terrain terrain3 = new Terrain(2,-3,loader,new ModelTexture(loader.loadTexture("grass")));
	         
	         
	         MasterRenderer renderer = new MasterRenderer();// Creates a new renderer to render the 3D world
	         Player player = new Player(movableBoy,new Vector3f(2,3,-400),0,0,0,8);// Creates a instance that represents the player
	         Camera camera = new Camera(player);// Creates the camera from the player view
	         
	        while(!Display.isCloseRequested()){//The loop that runs to render the world until user closes the display
	        	int Track1state = AL10.alGetSourcei(source.getSourceId(),AL10.AL_SOURCE_STATE);//gets the state of the track playing or stopped
	        	int Track2state = AL10.alGetSourcei(source2.getSourceId(),AL10.AL_SOURCE_STATE);
	        	int Track3state = AL10.alGetSourcei(source3.getSourceId(),AL10.AL_SOURCE_STATE);
	        	int Track4state = AL10.alGetSourcei(source4.getSourceId(),AL10.AL_SOURCE_STATE);
	        	
	        	
	                if( Track1state == AL10.AL_STOPPED && track1Playing == true){// Checks the track state and plays the other track if stopped
	                	track1Playing = false;
	                	track2Playing =true;
	                	track3Playing = false;
	                	track4Playing = false;
	                	source2.play(buffer2);	
	                	
	                }
	                if( Track2state == AL10.AL_STOPPED && track2Playing == true){
	                	track1Playing = false;
	                	track2Playing =false;
	                	track3Playing = true;
	                	track4Playing = false;
	                	source3.play(buffer3);	
	                	
	                }
	                if( Track3state == AL10.AL_STOPPED && track3Playing == true){
	                	track1Playing = false;
	                	track2Playing =false;
	                	track3Playing = false;
	                	track4Playing = true;
	                	source4.play(buffer4);	
	                	
	                }
	                if( Track4state == AL10.AL_STOPPED  && track4Playing == true){
	                	track1Playing =true;
	                	track2Playing =false;
	                	track3Playing = false;
	                	track4Playing = false;
	                	source.play(buffer);	
	                	
	                }
	               
	        	
	            camera.move();//moves the camera according to user input at a given second
	            player.move();//moves the player according to the user input at a given second
	            renderer.processEntity(player);//renders the player to the screen
	            renderer.processTerrain(terrain);//Renders terrain
	            renderer.processTerrain(terrain2);
	            renderer.processTerrain(terrain3);
	          
	            for(Entity entity:entities){//Renders all entities to the screen
	                renderer.processEntity(entity);
	            }
	            renderer.render(light, camera);//renders camera view and the light source
	            DisplayManager.updateDisplay();//finally updates the view on the display
	            
	        }
	        source.delete();//when closing, deletes music source
	        AudioMaster.cleanUp();//destroys buffers
	        renderer.cleanUp();//cleans up shaders
	        loader.cleanUp();//deletes vaos,vbos and textures
	        DisplayManager.closeDisplay();//finally, closes the display
	 
	    }
	 
	}
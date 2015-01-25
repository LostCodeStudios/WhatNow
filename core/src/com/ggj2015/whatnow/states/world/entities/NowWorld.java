package com.ggj2015.whatnow.states.world.entities;

import java.io.IOException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Json;
import com.ggj2015.whatnow.states.world.entities.systems.AnimalSystem;
import com.ggj2015.whatnow.states.world.entities.systems.AnimateSpriteSystem;
import com.ggj2015.whatnow.states.world.entities.systems.NPCActivationSystem;
import com.ggj2015.whatnow.states.world.entities.systems.PlayerControlSystem;
import com.ggj2015.whatnow.states.world.entities.systems.PropActivationSystem;
import com.ggj2015.whatnow.states.world.entities.templates.AnimalTemplate;
import com.ggj2015.whatnow.states.world.entities.templates.DragonTemplate;
import com.ggj2015.whatnow.states.world.entities.templates.NPCTemplate;
import com.ggj2015.whatnow.states.world.entities.templates.PlayerTemplate;
import com.ggj2015.whatnow.states.world.entities.templates.PortalTemplate;
import com.ggj2015.whatnow.states.world.entities.templates.PropTemplate;
import com.ggj2015.whatnow.states.world.entities.templates.TileTemplate;
import com.ggj2015.whatnow.states.world.level.GameObject;
import com.ggj2015.whatnow.states.world.level.Level;
import com.lostcode.javalib.Game;
import com.lostcode.javalib.entities.Entity;
import com.lostcode.javalib.entities.EntityWorld;
import com.lostcode.javalib.utils.SpriteSheet;

public class NowWorld extends EntityWorld {

	private Rectangle bounds;
	private Game game;


	public NowWorld(InputMultiplexer input, Camera camera, String levelData, Game game) {
		super(input, camera, Vector2.Zero.cpy());
		this.game = game;
		buildLevel(levelData);
	}

	
	protected void buildLevel(String levelDataFile){
		Json jsonParser = new Json();
		Level level = jsonParser.fromJson(Level.class, Gdx.files.internal(levelDataFile));
		
		//Establish basic world charactersitics.
		this.camera.position.set(new Vector3(level.getCameraInitial(),0));
		this.physicsWorld.setGravity(level.getGravity());
		this.bounds = level.getBounds();
		try {
			this.spriteSheet = SpriteSheet.fromXML(Gdx.files.internal(level.getSpriteSheetFile()));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		//Initialize the game objects or entities :)
		for(GameObject obj : level.getGameObjects()){
			String template = obj.getTemplate();
			if(!this.containsTemplate(template))
				template = "Tile";
			
			Entity e = this.createEntity(template,
					 obj.getPosition(), obj.getSpriteKey(), obj.getScale(), obj.getLayer());
			e.init(obj.getTag(), obj.getGroup(),obj.getType());
			if(e.getTag() == null)
				System.out.println("FUCK");
		}
	}
	
	public NPCActivationSystem npcActivationSystem;
	
	@Override
	protected void buildSystems() {
		super.buildSystems();
		
		debugView.enabled = true;
		systems.addSystem(new PlayerControlSystem(this.input));
		systems.addSystem(new AnimalSystem());
		systems.addSystem(new PropActivationSystem(this.input));
		systems.addSystem(new AnimateSpriteSystem());
		
		npcActivationSystem = new NPCActivationSystem(this.input);
		systems.addSystem(npcActivationSystem);
	}

	public boolean closeFlag = false;

	
	
	@Override
	public void process() {
		super.process();
		
		if (closeFlag)
			getGame().getScreenManager().closeActiveScreen();
	}


	@Override
	protected void buildTemplates() {
		super.buildTemplates();

		this.addTemplate("Player", new PlayerTemplate());

		this.addTemplate("Tile", new TileTemplate());
		this.addTemplate("Animal", new AnimalTemplate());
		this.addTemplate("Portal", new PortalTemplate());
		this.addTemplate("Prop", new PropTemplate());
		
		this.addTemplate("Dragon", new DragonTemplate());
		this.addTemplate("NPC", new NPCTemplate());
		
	}



	@Override
	protected void buildEntities() {
		super.buildEntities();

	}

	public Game getGame() {
		return game;
	}
	
	public SpriteSheet feetSheet;
	public SpriteSheet handsSheet;
	public SpriteSheet bodiesSheet;
	
	@Override
	protected void buildSpriteSheet() {
		try {
			this.spriteSheet = SpriteSheet.fromXML(Gdx.files.internal("sprite_sheet.xml"));
			feetSheet = SpriteSheet.fromXML(Gdx.files.internal("feet.xml"));
			handsSheet = SpriteSheet.fromXML(Gdx.files.internal("hands.xml"));
			bodiesSheet = SpriteSheet.fromXML(Gdx.files.internal("bodies.xml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Rectangle getBounds() {
		return bounds;
	}

}

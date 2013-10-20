package com.madgear.ninjatrials.trials;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.Entity;
import org.andengine.entity.modifier.PathModifier;
import org.andengine.entity.modifier.PathModifier.Path;
import org.andengine.entity.modifier.SequenceEntityModifier;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.SpriteBackground;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.opengl.texture.TextureManager;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.bitmap.BitmapTextureFormat;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.texture.region.TextureRegionFactory;
import org.andengine.util.adt.align.HorizontalAlign;

import android.util.Log;

import com.madgear.ninjatrials.GameScene;
import com.madgear.ninjatrials.hud.GameHUD;
import com.madgear.ninjatrials.hud.ShurikenEnemyCounter;
import com.madgear.ninjatrials.managers.ResourceManager;
import com.madgear.ninjatrials.managers.SceneManager;
import com.madgear.ninjatrials.test.MusicTest;
import com.madgear.ninjatrials.test.TestingScene;

/**
 * Trial Scene Shuriken V2 for Ninja Trials
 * 
 * @author Madgear Games
 * @version 0.1 16-Oct-2013
 */
public class TrialSceneShuriken extends GameScene{
	
	private final float SCRNWIDTH = ResourceManager.getInstance().cameraWidth;
	private final float SCRENHEIGHT = ResourceManager.getInstance().cameraHeight;
	private int AllowedImpactsOnPlayer = 1;
	private int impactsOnPlayer = 0;
	private ShurikenEnemyCounter shurikenEnemyCounterHUD;
	private int enemyCount = 10;
	private int enemiesLeft = enemyCount;
	private int enemyInsertionInterval = 5; // seconds
	private float enemySpeed = 0.25f; // % of horizontal screen size per second
	private int enemyLifes = 1;
	private float shurikenSpeed = 0.5f; // % of vertical screen size per second
	private int currentImpactsOnPlayer = 0;
	private int enemiesDefeated = 0;
	private int shurikensLaunched = 0;
	private float gameStartTime;
	private float gameEndTime;
	IUpdateHandler trialUpdateHandler;
	private GameHUD gameHUD;
	private boolean readyShow = false;
	private boolean gameStarted = false;
	private boolean gameFinished = false;
	private float timeScore;
	private float precissionScore;
	private float score;
	private float maxTime = 100; // seconds to get a 0 time score
	private Hands hands;
	private ArrayList<Enemy> enemies;
	private int shurikenAnimationCounter;
	
	public TrialSceneShuriken(){
		super(1f);
	}
	
	/**
	 * Loading scene
	 */
	@Override
	public Scene onLoadingScreenLoadAndShown() {
		Scene loadingScene = new Scene();		
		loadingScene.getBackground().setColor(.15f, .15f, .15f);		
        loadingScene.attachChild(getLoadingText("SHURIKEN TRIAL V2"));        
		return loadingScene;
	}

	@Override
	public void onLoadingScreenUnloadAndHidden() {}

	/**
	 * Shows background.
	 * Creates gamehud.
	 * Creates hands.
	 */
	@Override
	public void onLoadScene() {
		// TODO inicializar y mostrar shurikenEnemyCounter
		ResourceManager.getInstance().loadShurikenSceneResources();
		setBackground(getBG());
		gameHUD = new GameHUD();
		shurikenEnemyCounterHUD = new ShurikenEnemyCounter(SCRNWIDTH*.84f, SCRENHEIGHT*.92f, enemiesLeft);
		hands = new Hands();
	}
	
	/**
	 * Sets the gamehud.
	 * Attaches hands.
	 * calls start()
	 */
	@Override
	public void onShowScene() {		
		ResourceManager.getInstance().engine.getCamera().setHUD(gameHUD);
		attachChild(hands);
		attachChild(shurikenEnemyCounterHUD);
		start();
	}

	@Override
	public void onHideScene() {}

	@Override
	public void onUnloadScene() {
		ResourceManager.getInstance().unloadShurikenSceneResources();
	}
	
	/**
	 * Shows Ready and Go messages and then starts the game.
	 */
	private void start() {
		gameStartTime = ResourceManager.getInstance().engine.getSecondsElapsedTotal();
		trialUpdateHandler = new IUpdateHandler() {
            @Override
            public void onUpdate(float pSecondsElapsed) {
            	float waitPeriodForReadyMsg = 2f;
            	float waitPeriodForGoMsg = 5f;
                if(ResourceManager.getInstance().engine.getSecondsElapsedTotal() >
                gameStartTime + waitPeriodForReadyMsg) {
                	if (!readyShow){
                		readyShow = true;
                		gameHUD.showMessage("Ready?", 0, 2);
                    	Log.d("Bruno", "Ready?");
                	}                	
                }
                if(ResourceManager.getInstance().engine.getSecondsElapsedTotal() >
                gameStartTime + waitPeriodForGoMsg) {
                    TrialSceneShuriken.this.unregisterUpdateHandler(trialUpdateHandler);
                    gameHUD.showMessage("Go!", 0, 1);
                    Log.d("Bruno", "Go!");
                    gameStarted = true;
                    
                    generateEnemies();
                    checkEnemiesStatus();
                    gameStartTime = ResourceManager.getInstance().engine.getSecondsElapsedTotal();
                }
            }
            @Override public void reset() {}
        };
        registerUpdateHandler(trialUpdateHandler);
	}
	
	/**
	 * Adds an enemy every enemyInsertionInterval seconds.
	 */
	private void generateEnemies() {
		if (enemies == null){
			enemies = new ArrayList<Enemy>(enemyCount);
		}
		Timer timer = new Timer();
		timer.schedule(new enemyGenerator(), enemyInsertionInterval*1000);
	}
	
	/**
	 * Aux. class for generateEnemies()
	 */
	private class enemyGenerator extends TimerTask {

		@Override
		public void run() {
			Enemy enemy = new Enemy(1, enemySpeed);
			enemies.add(enemy);
			attachChild(enemy);
			Log.d("Bruno", "New enemy generated, there are "+enemies.size()+" enemies ("+enemyCount+" max).");
			if (!gameFinished && enemies.size() < enemyCount){
				generateEnemies();
			}			
		}
		
	}
	
	/**
	 * Checks enemies current status every checkInterval seconds.
	 * The following cases are checked:
	 * - The enemy has impacted on player.
	 * - The enemy has been defeated.
	 * - There are no enemies left.
	 */
	private void checkEnemiesStatus() {
		float checkInterval = .5f;
		Timer timer = new Timer();
		timer.schedule(new enemyStatusChecker(), (int)(checkInterval*1000));
	}
	
	/**
	 * Aux. class for checkEnemiesStatus()
	 */
	private class enemyStatusChecker extends TimerTask {

		@Override
		public void run() {
			for (Enemy enemy: enemies) {
				if (enemy.getPosition().x == -1 && enemy.getPosition().y == -1) {
					enemy.hide();
					enemiesLeft--;
					impactsOnPlayer++;
					if (impactsOnPlayer >= AllowedImpactsOnPlayer) {
						gameOver();
					}
				}
				if (enemy.getLifes() <= 0) {
					enemy.hide();
					enemiesDefeated++;
					enemiesLeft--;
				}
			}
			if (enemiesLeft <= 0) {
				gameOver();
			}
			Log.d("Bruno", "All enemies statuses have been checked.");
			if (!gameFinished){
				checkEnemiesStatus();
			}			
		}		
	}
	
	/*
	 * Stops enemy generation and checking.
	 * Compute scores.
	 * Hide hands.
	 * Shows ResultWinScene or ResultsLoseScene
	 */
	private void gameOver() {
		gameFinished = true;
		gameEndTime = ResourceManager.getInstance().engine.getSecondsElapsedTotal();
		precissionScore = enemiesDefeated / shurikensLaunched;
		timeScore = (maxTime - (gameEndTime - gameStartTime)) / maxTime;
		hands.hide();
		if (impactsOnPlayer >= AllowedImpactsOnPlayer) {
			// TODO mostrar animacion de derrota
		}
		else {
			// TODO mostrar animacion de victoria
		}
	}
	
	private void checkForImpact() {
		boolean hit = false;
		float horizontalErrorMargin = .1f;
		float verticalErrorMargin = .2f;
		for (Enemy enemy: enemies) {
			if (Math.abs(hands.getPosition().x - enemy.getPosition().x) < SCRNWIDTH * horizontalErrorMargin) {
				float timeToImpact = (enemy.getPosition().y - hands.getPosition().y) / (shurikenSpeed * SCRENHEIGHT);
				float enemyPredictedHorizontal;
				if (enemy.getDirection() == 'r'){
					enemyPredictedHorizontal = enemy.getPosition().y - enemySpeed * SCRNWIDTH * timeToImpact;
				}
				else {
					enemyPredictedHorizontal = enemy.getPosition().y + enemySpeed * SCRNWIDTH * timeToImpact;
				}
				if (Math.abs(hands.getPosition().y - enemyPredictedHorizontal) < SCRENHEIGHT * verticalErrorMargin) {
					hit = true;
				}
			}
			if (hit) {
				enemy.setLifes(enemy.getLifes() + 1);
				break;
			}
		}
		
	}
	
	/*
	 * Stops enemy generation and checking.
	 * Returns to TestingScene.
	 */
	@Override
    public void onPressButtonMenu() {
		gameFinished = true;
		SceneManager.getInstance().showScene(new TestingScene());
    }
	
	public void onPressButtonO() {
		hands.launch();
		if (gameStarted) {
			shurikensLaunched++;
			checkForImpact();
		}
	}
	public void onPressDpadLeft() {
		hands.moveLeft();
	}
    public void onPressDpadRight() {
    	hands.moveRight();
    }
    public void onReleaseDpadLeft() {
		hands.stop();
	}
    public void onReleaseDpadRight() {
    	hands.stop();
    }
	
	private class Enemy extends Entity {
		
		private char direction = 'r';
		private int lifes;
		private float speed;
		private Coordinates position;
		private boolean playerHit = false;
		private AnimatedSprite enemy;
		
		public Enemy(int lifes, float speed) {
			this.lifes = lifes;
			this.speed = speed;
			this.position = new Coordinates(SCRNWIDTH*9/10, SCRENHEIGHT*4/5);
			ITiledTextureRegion enemyITTR = ResourceManager.getInstance().shurikenStrawman1;
			enemy = new AnimatedSprite(position.x, position.y, enemyITTR, ResourceManager.getInstance().engine.getVertexBufferObjectManager());
			enemy.setCurrentTileIndex(1);
			attachChild(enemy);
			start();
		}
		
		public void start() {
			/*
			 * TODO
			 * Aparece al fondo
			 * Va recorriendo horizontalmente la pantalla
			 * Se acerca de forma alternada
			 * Hay dos “carriles” horizontales, uno lejos y otro a media distancia.
			 * Si no son abatidos por los shurikens los enemigos
			 * bajarán primero por el que está lejos y recorrerán
			 * una parte del “carril”, tras eso subirán de nuevo
			 * a los árboles y bajarán en el “carril”
			 * que está a media distancia, recorrerán una parte de ese carril
			 * y volverán a ascender a los árboles, tras
			 * eso caerá junto al personaje y mostrará el strawman con cartel     
			 */
			float fromX = position.x;
			float toX = SCRNWIDTH/10;
			float fromY = position.y;
			float toY = position.y;
			float time = (fromX - toX) / (speed * SCRNWIDTH);
			moveSprite(enemy, fromX, fromY, toX, toY, time);
		}
		
		public Coordinates getPosition() {
			if (playerHit) {
				return new Coordinates(-1, -1);
			}
			return position;
		}
		
		public void hide() {
			this.setAlpha(0f);
		}
		
		public char getDirection(){
			return this.direction;
		}
		
		public int getLifes(){
			return this.lifes;
		}
		
		public void setLifes(int lifes){
			this.lifes = lifes;
		}
	}
	
	private class Hands extends Entity {
		
		private Coordinates coordinates;
		private Sprite[] handsSprites = new Sprite[3];
		private Sprite[] shurikenSprites = new Sprite[6]; 
		private boolean ignoreInputBecauseMoving = false;
		private float movementDistanceDelta = 50; // pixels
		private float movementTimeDelta = .025f; // seconds
		private int movementAnimationExtraTimeMargin = 13; // miliseconds
		private IUpdateHandler shurikenUpdateHandler;
		private float shurikenLaunchTime;
		private AnimatedSprite hands;
		
		public Hands() {
			float posX = SCRNWIDTH/2;
			float posY = 160f;
			this.coordinates = new Coordinates(posX, posY);			
			ITiledTextureRegion handsITTR = ResourceManager.getInstance().shurikenRyokoHands;
			hands = new AnimatedSprite(posX, posY, handsITTR, ResourceManager.getInstance().engine.getVertexBufferObjectManager());
			hands.setCurrentTileIndex(2);			
			ITextureRegion[] shurikenShurikens = ResourceManager.getInstance().shurikenShurikens;			
			for (int i = 0; i < 6; i++) {
				shurikenSprites[i] = new Sprite(posX-100, SCRENHEIGHT-300-75*i, shurikenShurikens[i], ResourceManager.getInstance().engine.getVertexBufferObjectManager());
			}
			for (Sprite sprite: shurikenSprites){
	        	sprite.setAlpha(0f);
	        	attachChild(sprite);
	        }			
			attachChild(hands);
		}
		public void moveLeft() {
			Log.d("Bruno", "Hands positions are ("+hands.getX()+", "+hands.getY()+")");
			if (coordinates.x - movementDistanceDelta >= 0 && !ignoreInputBecauseMoving){
				ignoreInputBecauseMoving = true;
				moveSprite(hands, coordinates.x, coordinates.y, coordinates.x - movementDistanceDelta, coordinates.y, movementTimeDelta);
				coordinates.x = coordinates.x - movementDistanceDelta;
				Timer timer = new Timer();
				timer.schedule(new HandsMovementWaiter(this), (int)(movementTimeDelta * 1000) + movementAnimationExtraTimeMargin);
			}			
		}
		public void moveRight() {
			Log.d("Bruno", "Hands positions are ("+hands.getX()+", "+hands.getY()+")");
			if (coordinates.x + movementDistanceDelta <= SCRNWIDTH && !ignoreInputBecauseMoving){
				ignoreInputBecauseMoving = true;
				moveSprite(hands, coordinates.x, coordinates.y, coordinates.x + movementDistanceDelta, coordinates.y, movementTimeDelta);
				coordinates.x = coordinates.x + movementDistanceDelta;
				Timer timer = new Timer();
				timer.schedule(new HandsMovementWaiter(this), (int)(movementTimeDelta * 1000) + movementAnimationExtraTimeMargin);
			}
		}
		public void stop() {
			
		}
		public void hide() {
			// TODO Las oculta lentamente por la parte inferior de la pantalla.
		}
		public Coordinates getPosition() {
			return this.coordinates;
		}
		public void launch() {
			/*
			 * el lanzamiento es puramente vertical
			 * los shurikens se destruyen tras impactar/perderse
			 * pueden simultanearse varios lanzamientos (no hay bloqueo)
			 * la velocidad vertical es constante (shurikenSpeed)
			 */
			Log.d("Bruno", "Launching shuriken");
			for (Sprite shuriken: shurikenSprites) {
				shuriken.setX(this.coordinates.x);
			}
			hands.animate(200, 0);
			shurikenAnimationCounter = 0;
			shurikenLaunchTime = ResourceManager.getInstance().engine.getSecondsElapsedTotal();
			shurikenUpdateHandler = new IUpdateHandler() {
	            @Override
	            public void onUpdate(float pSecondsElapsed) {
	            	float period = .12f;
	                if(ResourceManager.getInstance().engine.getSecondsElapsedTotal() >
	                shurikenLaunchTime + period*shurikenAnimationCounter) {
	                	Log.d("Bruno", "Counter "+shurikenAnimationCounter+".");
	                	if (shurikenAnimationCounter == 0) {
	                		shurikenSprites[5-shurikenAnimationCounter].setAlpha(1f);
	                	}
	                	else if (shurikenAnimationCounter == 6) {
	                		shurikenSprites[0].setAlpha(0f);
	                		Hands.this.unregisterUpdateHandler(shurikenUpdateHandler);
	                	}
	                	else {
	                		shurikenSprites[5-shurikenAnimationCounter+1].setAlpha(0f);
	                		shurikenSprites[5-shurikenAnimationCounter].setAlpha(1f);
	                	}
	                	shurikenAnimationCounter++;
	                }	                
	            }
	            @Override public void reset() {}
	        };
	        registerUpdateHandler(shurikenUpdateHandler);
		}
	}
	
	/**
	 * Aux. class for class Hands.
	 * TimerTask that enables input
	 */
	private class HandsMovementWaiter extends TimerTask {		
		Hands hands;
		
		public HandsMovementWaiter(Hands hands){
			super();
			this.hands = hands;
		}
		
		@Override
		public void run() {
			hands.ignoreInputBecauseMoving = false;			
		}
		
	}
	
	/**
	 * TODO
	 *
	 */
	private class Shuriken extends Entity{
		
		public Shuriken(){
			
		}
	}
	
	private class Coordinates {
		// Coordinates origin (0,0) is the screen lower left corner.
		float x, y;
		public Coordinates(float x, float y) {
			this.x = x;
			this.y = y;
		}
	}
	
	public static int getScore() {
	    // TODO Auto-generated method stub
	    return 1;
	}

	public static int getStamp(int score2) {
	    // TODO Auto-generated method stub
	    return 0;
	}

	public static int getTimeScore() {
	    // TODO Auto-generated method stub
	    return 0;
	}

	public static int getPrecissionScore() {
	    // TODO Auto-generated method stub
	    return 0;
	}
	
	/**
	 * Moves an Sprite through the screen using PathModifier
	 */
	protected void moveSprite(Sprite sprite, float fromX, float fromY, float toX, float toY, float seconds){
		Path p = new Path(2).to(fromX, fromY).to(toX, toY);
		PathModifier pathModifier = new PathModifier(seconds, p);
		sprite.registerEntityModifier(pathModifier);
	}
	
	public Text getLoadingText(String s){
		return new Text(
				SCRNWIDTH * 0.5f,
				SCRENHEIGHT * 0.5f,
                ResourceManager.getInstance().fontBig, s,
                new TextOptions(HorizontalAlign.CENTER),
                ResourceManager.getInstance().engine
                .getVertexBufferObjectManager());
	}
	
	public SpriteBackground getBG(){
		ITextureRegion backgroundTextureRegion = ResourceManager.getInstance().shurikenBackground;
        Sprite backgroundSprite = new Sprite(SCRNWIDTH / 2, SCRENHEIGHT / 2, 
        		backgroundTextureRegion, ResourceManager.getInstance().engine
        		.getVertexBufferObjectManager());
        return new SpriteBackground(backgroundSprite);        
	}
}

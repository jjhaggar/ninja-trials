/*
 * Ninja Trials is an old school style Android Game developed for OUYA & using
 * AndEngine. It features several minigames with simple gameplay.
 * Copyright 2013 Mad Gear Games <madgeargames@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package com.madgear.ninjatrials.sequences;

import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.AlphaModifier;
import org.andengine.entity.modifier.DelayModifier;
import org.andengine.entity.modifier.IEntityModifier.IEntityModifierListener;
import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.modifier.MoveXModifier;
import org.andengine.entity.modifier.MoveYModifier;
import org.andengine.entity.modifier.ScaleModifier;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.util.adt.align.HorizontalAlign;
import org.andengine.util.modifier.IModifier;
import org.andengine.util.modifier.ease.EaseBackInOut;
import org.andengine.util.modifier.ease.IEaseFunction;

import com.madgear.ninjatrials.GameScene;
import com.madgear.ninjatrials.MainMenuScene;
import com.madgear.ninjatrials.managers.GameManager;
import com.madgear.ninjatrials.managers.ResourceManager;
import com.madgear.ninjatrials.managers.SFXManager;
import com.madgear.ninjatrials.managers.SceneManager;
import com.madgear.ninjatrials.test.TestingScene;

/**
 * This is the First Intro class.
 * @author Madgear Games
 *
 */
@SuppressWarnings("static-access")
public class Intro1Scene extends GameScene {

	private final float SCREEN_WIDTH = ResourceManager.getInstance().cameraWidth;
	private final float SCREEN_HEIGHT = ResourceManager.getInstance().cameraHeight;

	private float timeStart = 0;
	private float timeShapes = 3.5f;
	private float timeShoNinj = 3.5f;
	private float timeRun = 1.5f;
	private float timeCut = 1.5f;
	private float timeShoInja = 1.75f;
	private float timeRyokoTria = 1.75f;
	private float timeShuriken = 2.0f;
	private float timeJump = 2f;
	private float timeRyokoIals = 3.0f;
	private float timeLogo = 2f;
	private float timeWait = 3f;

	private TimerHandler timerShapes;
	private TimerHandler timerShoNinj;
	private TimerHandler timerRun;
	private TimerHandler timerCut;
	private TimerHandler timerShoInja;
	private TimerHandler timerRyokoTria;
	private TimerHandler timerShuriken;
	private TimerHandler timerJump;
	private TimerHandler timerRyokoIals;
	private TimerHandler timerLogo;
	private TimerHandler timerWait;

	private Sprite sprGradient;
	private Sprite sprLogo;
	private Sprite sprRyoko;
	private Sprite sprShapes;
	private Sprite sprSho;
	private Sprite sprTrialCut;
	private Sprite sprTrialJump;
	private Sprite sprTrialRun;
	private Sprite sprTrialShuriken;
	private Sprite sprWordMaskNinja;
	private Sprite sprWordMaskTrials;

	IUpdateHandler updateHandler;

    private static final float PUSH_DELAY_TIME = 2.0f;
    public static boolean delayTime = true;
    private boolean pressButtonEnabled = false;
    private TimerHandler timerHandler;

    public Intro1Scene() {
        this(0f);  // loading screen disabled.
    }

    public Intro1Scene(float min) {
        super(min);  // loading screen enabled.
        if (delayTime){
	        timerHandler = new TimerHandler(PUSH_DELAY_TIME, true, new ITimerCallback() {
	            @Override
	            public void onTimePassed(final TimerHandler pTimerHandler) {
	                pressButtonEnabled = true;
	            } 
	        });
	        registerUpdateHandler(timerHandler);
        }
        else{
        	pressButtonEnabled = true;
        }
    }

    @Override
    public Scene onLoadingScreenLoadAndShown() {
        Scene loadingScene = new Scene(); // Provisional, sera una clase externa
        loadingScene.getBackground().setColor(0.3f, 0.9f, 0.2f);
        // Añadimos algo de texto:
        final Text loadingText = new Text(
                ResourceManager.getInstance().cameraWidth * 0.5f,
                ResourceManager.getInstance().cameraHeight * 0.3f,
                ResourceManager.getInstance().fontBig, "Loading...",
                new TextOptions(HorizontalAlign.CENTER),
                ResourceManager.getInstance().engine.getVertexBufferObjectManager());
        loadingScene.attachChild(loadingText);
        return loadingScene;
    }

    @Override
    public void onLoadingScreenUnloadAndHidden() {}

    @Override
    public void onLoadScene() {
    	ResourceManager.getInstance().loadIntro1Resources();
    }

    @Override
    public void onShowScene() {
        this.getBackground().setColor(0.3f, 0.3f, 0.3f);
        final Text loadingText = new Text(
                ResourceManager.getInstance().cameraWidth * 0.5f,
                ResourceManager.getInstance().cameraHeight * 0.5f,
                ResourceManager.getInstance().fontMedium,
                "Intro 1 Scene\n" +
                "Press O for action\n" +
                "You "+ ((delayTime) ? "" : "don't ")+
                "must wait" + 
                ((delayTime) ? " for " + PUSH_DELAY_TIME + " seconds.\n" : "."),
                new TextOptions(HorizontalAlign.CENTER),
                ResourceManager.getInstance().engine.getVertexBufferObjectManager());
        // this.attachChild(loadingText);


        // ***********************************************************************************
        // ************************** SUB-SEQUENCES of the SCENE *****************************
        // ***********************************************************************************

        // "Shapes" sequence
        if (timeStart>0f){
	        timerShapes = new TimerHandler(timeStart, false, new ITimerCallback(){
	            @Override
	            public void onTimePassed(TimerHandler pTimerHandler) {
	            	SFXManager.playMusic(ResourceManager.getInstance().intro1);
	            	shapesSequence();
	            }
	        });
	        this.registerUpdateHandler(timerShapes);
        }
        else {
        	SFXManager.playMusic(ResourceManager.getInstance().intro1);
        	shapesSequence();
        	System.out.println("Timer OK2");

        	// Tests:
        	// shoNinjSequence();
        	// triRunSequence();
        	// triCutSequence() ;
        	// shoInjaSequence();
        	// ryokoTriaSequence();
        	// triShurikenSequence();
        	// triJumpSequence() ;
        	// ryokoIalsSequence();
            // logoSequence();
        }

        // "Sho + Ninj..." sequence
        timerShoNinj = new TimerHandler(timeStart + timeShapes, false, new ITimerCallback(){
            @Override
            public void onTimePassed(TimerHandler pTimerHandler) {
            	shoNinjSequence();
            }
        });
        this.registerUpdateHandler(timerShoNinj);

        // "Run Trial" sequence
        timerRun = new TimerHandler(timeStart + timeShapes + timeShoNinj, 
        		false, new ITimerCallback(){
            @Override
            public void onTimePassed(TimerHandler pTimerHandler) {
            	triRunSequence();
            }
        });
        this.registerUpdateHandler(timerRun);

        // "Cut Trial" sequence
        timerCut = new TimerHandler(timeStart + timeShapes + timeShoNinj + timeRun, 
        		false, new ITimerCallback(){
            @Override
            public void onTimePassed(TimerHandler pTimerHandler) {
            	triCutSequence();
            }
        });
        this.registerUpdateHandler(timerCut);

        // "Sho + ...inja" sequence
        timerShoInja = new TimerHandler(timeStart + timeShapes + timeShoNinj + timeRun + timeCut, 
        		false, new ITimerCallback(){
            @Override
            public void onTimePassed(TimerHandler pTimerHandler) {
            	shoInjaSequence();
            }
        });
        this.registerUpdateHandler(timerShoInja);

        // "Ryoko + Tria..." sequence
        timerRyokoTria = new TimerHandler(timeStart + timeShapes + timeShoNinj + timeRun + timeCut+
        		timeShoInja, false, new ITimerCallback(){
            @Override
            public void onTimePassed(TimerHandler pTimerHandler) {
            	ryokoTriaSequence();
            }
        });
        this.registerUpdateHandler(timerRyokoTria);

        // "Shuriken Trial" sequence
        timerShuriken = new TimerHandler(timeStart + timeShapes + timeShoNinj + timeRun + timeCut+
        		timeShoInja + timeRyokoTria, false, new ITimerCallback(){
            @Override
            public void onTimePassed(TimerHandler pTimerHandler) {
            	triShurikenSequence();
            }
        });
        this.registerUpdateHandler(timerShuriken);

        // "Jump Trial" sequence
        timerJump = new TimerHandler(timeStart + timeShapes + timeShoNinj + timeRun + timeCut+
        		timeShoInja + timeRyokoTria + timeShuriken, false, new ITimerCallback(){
            @Override
            public void onTimePassed(TimerHandler pTimerHandler) {
            	triJumpSequence();
            }
        });
        this.registerUpdateHandler(timerJump);

        // "Ryoko ...ials" sequence
        timerRyokoIals = new TimerHandler(timeStart + timeShapes + timeShoNinj + timeRun + timeCut+
        		timeShoInja + timeRyokoTria + timeShuriken + timeJump, false, new ITimerCallback(){
            @Override
            public void onTimePassed(TimerHandler pTimerHandler) {
            	ryokoIalsSequence();
            }
        });
        this.registerUpdateHandler(timerRyokoIals);

        // "NinjaTrials Logo" sequence
        timerLogo = new TimerHandler(timeStart + timeShapes + timeShoNinj + timeRun + timeCut+
        		timeShoInja + timeRyokoTria + timeShuriken + timeJump + timeRyokoIals, 
        		false, new ITimerCallback(){
            @Override
            public void onTimePassed(TimerHandler pTimerHandler) {
            	logoSequence();
            }
        });
        this.registerUpdateHandler(timerLogo);

        // "Waiting before MainMenu" sequence
        timerWait = new TimerHandler(timeStart + timeShapes + timeShoNinj + timeRun + timeCut+
        		timeShoInja + timeRyokoTria + timeShuriken + timeJump + timeRyokoIals + timeLogo, 
        		false, new ITimerCallback(){
            @Override
            public void onTimePassed(TimerHandler pTimerHandler) {
            	waitSequence();
            }
        });
        this.registerUpdateHandler(timerWait);
    }

    @Override
    public void onHideScene() {}

    @Override
    public void onUnloadScene() {
		ResourceManager.getInstance().unloadIntro1Resources();		
    }

    private void shapesSequence() {
    	System.out.println("ShapesSequence");

    	// Add Sprites to scene
    	sprGradient = new Sprite(SCREEN_WIDTH/2, SCREEN_HEIGHT/2, 
				ResourceManager.getInstance().intro1Gradient.getWidth(), 
				ResourceManager.getInstance().intro1Gradient.getHeight(),
				ResourceManager.getInstance().intro1Gradient,
				ResourceManager.getInstance().engine.getVertexBufferObjectManager());
		attachChild(sprGradient);
		sprShapes = new Sprite(SCREEN_WIDTH, SCREEN_HEIGHT/2, 
				ResourceManager.getInstance().intro1Shapes.getWidth(), 
				ResourceManager.getInstance().intro1Shapes.getHeight(),
				ResourceManager.getInstance().intro1Shapes,
				ResourceManager.getInstance().engine.getVertexBufferObjectManager());
		attachChild(sprShapes);

		// Create & add Modifiers to Sprites
		MoveModifier modMovShapes = new MoveModifier(timeShapes, sprShapes.getX(), sprShapes.getY(), 
				sprShapes.getX()-500, sprShapes.getY()-200);
		sprShapes.registerEntityModifier(modMovShapes);
		ScaleModifier modScaShapes = new ScaleModifier(timeShapes, 1, 1.5f);
		sprShapes.registerEntityModifier(modScaShapes);
		DelayModifier modDelShapes = new DelayModifier(timeShapes,
				new IEntityModifierListener() {
					@Override
					public void onModifierStarted(IModifier<IEntity> pModifier,
							IEntity pItem) {
						// TODO Auto-generated method stub
					}
					@Override
					public void onModifierFinished(
							IModifier<IEntity> pModifier, IEntity pItem) {
						sprShapes.setAlpha(0f);
					}
		});
		sprShapes.registerEntityModifier(modDelShapes);
		DelayModifier modDelGradient = new DelayModifier(timeShapes,
				new IEntityModifierListener() {
					@Override
					public void onModifierStarted(IModifier<IEntity> pModifier,
							IEntity pItem) {
						// TODO Auto-generated method stub
					}
					@Override
					public void onModifierFinished(
							IModifier<IEntity> pModifier, IEntity pItem) {
						sprGradient.setAlpha(0f);
					}
		});
		sprGradient.registerEntityModifier(modDelGradient);
	}

    private void shoNinjSequence() {
    	System.out.println("shoNinjSequence");

    	// Add Sprites to scene
    	if (sprSho==null) {
	    	sprSho = new Sprite(SCREEN_WIDTH/2, SCREEN_HEIGHT/2 - 300, 
					ResourceManager.getInstance().intro1Sho.getWidth(), 
					ResourceManager.getInstance().intro1Sho.getHeight(),
					ResourceManager.getInstance().intro1Sho,
					ResourceManager.getInstance().engine.getVertexBufferObjectManager());
	    	sprSho.setScale(2f);
			attachChild(sprSho);
    	}
    	else
    		sprSho.setPosition(SCREEN_WIDTH/2, SCREEN_HEIGHT/2 - 300);
    	
    	if (sprWordMaskNinja==null) {
			sprWordMaskNinja = new Sprite(SCREEN_WIDTH/2 + 1000, SCREEN_HEIGHT/2, 
					ResourceManager.getInstance().intro1WordmaskNinja.getWidth(), 
					ResourceManager.getInstance().intro1WordmaskNinja.getHeight(),
					ResourceManager.getInstance().intro1WordmaskNinja,
					ResourceManager.getInstance().engine.getVertexBufferObjectManager());
			sprWordMaskNinja.setScaleX(2.5f);
			sprWordMaskNinja.setScaleY(1.3f);
			attachChild(sprWordMaskNinja);
    	}
    	else
    		sprWordMaskNinja.setPosition(SCREEN_WIDTH/2 + 1000, SCREEN_HEIGHT/2);

		// Create & add Modifiers to Sprites
		MoveXModifier modMovXNinja = new MoveXModifier(timeShoNinj, sprWordMaskNinja.getX(), 
				sprWordMaskNinja.getX()-500);
		sprWordMaskNinja.registerEntityModifier(modMovXNinja);
		MoveModifier modMovSho = new MoveModifier(timeShoNinj, sprSho.getX(), sprSho.getY(), 
				sprSho.getX() - 20, sprSho.getY() - 400);
		sprSho.registerEntityModifier(modMovSho);
    }

    private void triRunSequence() {
    	System.out.println("triRunSequence");

    	// Add Sprites to scene
    	sprTrialRun = new Sprite(SCREEN_WIDTH/2, SCREEN_HEIGHT/2, 
				ResourceManager.getInstance().intro1TrialRun.getWidth() + 200, 
				ResourceManager.getInstance().intro1TrialRun.getHeight(),
				ResourceManager.getInstance().intro1TrialRun,
				ResourceManager.getInstance().engine.getVertexBufferObjectManager());
    	sprTrialRun.setScale(2f);
    	attachChild(sprTrialRun);

    	// Create & add Modifiers to Sprites
    	MoveXModifier modMovXTrialRun = new MoveXModifier(timeRun, sprTrialRun.getX(), 
    			sprTrialRun.getX() - 400);
    	sprTrialRun.registerEntityModifier(modMovXTrialRun);
		
		DelayModifier modDelTrialRun = new DelayModifier(timeRun,
				new IEntityModifierListener() {
					@Override
					public void onModifierStarted(IModifier<IEntity> pModifier,
							IEntity pItem) {
						// TODO Auto-generated method stub
					}
					@Override
					public void onModifierFinished(
							IModifier<IEntity> pModifier, IEntity pItem) {
						// sprTrialRun.setAlpha(0f);
					}
		});
		sprTrialRun.registerEntityModifier(modDelTrialRun);
    }

    private void triCutSequence() {
    	System.out.println("triCutSequence");
    	// Add Sprites to scene
    	sprTrialCut = new Sprite(SCREEN_WIDTH/2, SCREEN_HEIGHT/2, 
				ResourceManager.getInstance().intro1TrialCut.getWidth() + 200, 
				ResourceManager.getInstance().intro1TrialCut.getHeight(),
				ResourceManager.getInstance().intro1TrialCut,
				ResourceManager.getInstance().engine.getVertexBufferObjectManager());
    	sprTrialCut.setScale(1.5f);
    	attachChild(sprTrialCut);
    	sprTrialRun.setAlpha(0f); // This should be in triRunSequence(), but it vanishes too soon

    	// Create & add Modifiers to Sprites
    	MoveModifier modMovTrialCut = new MoveModifier(timeCut, 
    			sprTrialCut.getX(), sprTrialCut.getY(), 
    			sprTrialCut.getX() - 100, sprTrialCut.getY() - 50);
    	sprTrialCut.registerEntityModifier(modMovTrialCut);

		ScaleModifier modScaleTrialCut = new ScaleModifier(timeCut, 1.5f, 2.5f);
		sprTrialCut.registerEntityModifier(modScaleTrialCut);

		DelayModifier modDelTrialCut = new DelayModifier(timeCut,
				new IEntityModifierListener() {
					@Override
					public void onModifierStarted(IModifier<IEntity> pModifier,
							IEntity pItem) {
						// TODO Auto-generated method stub
					}
					@Override
					public void onModifierFinished(
							IModifier<IEntity> pModifier, IEntity pItem) {
						sprTrialCut.setAlpha(0f);
					}
		});
		sprTrialCut.registerEntityModifier(modDelTrialCut);
    }

    private void shoInjaSequence() {
    	System.out.println("shoInjaSequence");

    	sprWordMaskNinja.setX(sprWordMaskNinja.getX() - 100);

		// Create & add Modifiers to Sprites
		MoveXModifier modMovXNinja = new MoveXModifier(timeShoInja, sprWordMaskNinja.getX(), 
				sprWordMaskNinja.getX()-500);
		sprWordMaskNinja.registerEntityModifier(modMovXNinja);
		MoveModifier modMovSho = new MoveModifier(timeShoInja, sprSho.getX(), sprSho.getY(), 
				sprSho.getX() - 20, sprSho.getY() - 400);
		sprSho.registerEntityModifier(modMovSho);
    }

    private void ryokoTriaSequence() {
    	System.out.println("ryokoTriaSequence");

    	// Add Sprites to scene
    	sprRyoko = new Sprite(SCREEN_WIDTH/2, SCREEN_HEIGHT/2 - 300, 
				ResourceManager.getInstance().intro1Ryoko.getWidth(), 
				ResourceManager.getInstance().intro1Ryoko.getHeight(),
				ResourceManager.getInstance().intro1Ryoko,
				ResourceManager.getInstance().engine.getVertexBufferObjectManager());
    	sprRyoko.setScale(2f);
		attachChild(sprRyoko);

    	sprWordMaskTrials = new Sprite(SCREEN_WIDTH/2 + 1000, SCREEN_HEIGHT/2, 
				ResourceManager.getInstance().intro1WordmaskTrials.getWidth(), 
				ResourceManager.getInstance().intro1WordmaskTrials.getHeight(),
				ResourceManager.getInstance().intro1WordmaskTrials,
				ResourceManager.getInstance().engine.getVertexBufferObjectManager());
		sprWordMaskTrials.setScaleX(2.5f);
		sprWordMaskTrials.setScaleY(1.3f);
		attachChild(sprWordMaskTrials);

		// Hide Sho and NinjaMask Sprites
		sprSho.setAlpha(0);
		sprWordMaskNinja.setAlpha(0);

		// Create & add Modifiers to Sprites
		MoveXModifier modMovXTrials = new MoveXModifier(timeRyokoTria, sprWordMaskTrials.getX(), 
				sprWordMaskTrials.getX()-500);
		sprWordMaskTrials.registerEntityModifier(modMovXTrials);
		MoveModifier modMovRyoko = new MoveModifier(timeRyokoTria, sprRyoko.getX(), sprRyoko.getY(), 
				sprRyoko.getX() - 20, sprRyoko.getY() - 400);
		sprRyoko.registerEntityModifier(modMovRyoko);

    }

    private void triShurikenSequence() {
    	System.out.println("triShurikenSequence");

    	// Add Sprites to scene
    	sprTrialShuriken = new Sprite(SCREEN_WIDTH/2, SCREEN_HEIGHT/2, 
				ResourceManager.getInstance().intro1TrialShuriken.getWidth() + 200, 
				ResourceManager.getInstance().intro1TrialShuriken.getHeight(),
				ResourceManager.getInstance().intro1TrialShuriken,
				ResourceManager.getInstance().engine.getVertexBufferObjectManager());
    	sprTrialShuriken.setScale(1.5f);
    	attachChild(sprTrialShuriken);

    	// Create & add Modifiers to Sprites
    	MoveModifier modMovTrialShuriken = new MoveModifier(timeShuriken, 
    			sprTrialShuriken.getX(), sprTrialShuriken.getY(), 
    			sprTrialShuriken.getX() - 100, sprTrialShuriken.getY() - 50);
    	sprTrialShuriken.registerEntityModifier(modMovTrialShuriken);

		ScaleModifier modScaleTrialShuriken = new ScaleModifier(timeShuriken, 1.5f, 2.5f);
		sprTrialShuriken.registerEntityModifier(modScaleTrialShuriken);

		DelayModifier modDelTrialShuriken = new DelayModifier(timeShuriken,
				new IEntityModifierListener() {
					@Override
					public void onModifierStarted(IModifier<IEntity> pModifier,
							IEntity pItem) {
						// TODO Auto-generated method stub
					}
					@Override
					public void onModifierFinished(
							IModifier<IEntity> pModifier, IEntity pItem) {
						sprTrialShuriken.setAlpha(0f);
					}
		});
		sprTrialShuriken.registerEntityModifier(modDelTrialShuriken);
    }

    private void triJumpSequence() {
    	System.out.println("triJumpSequence");

    	// Add Sprites to scene
    	sprTrialJump = new Sprite(SCREEN_WIDTH/2, SCREEN_HEIGHT/2 + 200, 
				ResourceManager.getInstance().intro1TrialJump.getWidth(), 
				ResourceManager.getInstance().intro1TrialJump.getHeight(),
				ResourceManager.getInstance().intro1TrialJump,
				ResourceManager.getInstance().engine.getVertexBufferObjectManager());
    	sprTrialJump.setScale(2f);

    	attachChild(sprTrialJump);

    	// Create & add Modifiers to Sprites
    	MoveYModifier modMovYTrialJump = new MoveYModifier(timeJump, 
    			sprTrialJump.getY(), sprTrialJump.getY() - 400);
    	sprTrialJump.registerEntityModifier(modMovYTrialJump);

		DelayModifier modDelTrialJump = new DelayModifier(timeJump,
				new IEntityModifierListener() {
					@Override
					public void onModifierStarted(IModifier<IEntity> pModifier,
							IEntity pItem) {
						// TODO Auto-generated method stub
					}
					@Override
					public void onModifierFinished(
							IModifier<IEntity> pModifier, IEntity pItem) {
						sprTrialJump.setAlpha(0f);
					}
		});
		sprTrialJump.registerEntityModifier(modDelTrialJump);
    }

    private void ryokoIalsSequence() {
    	System.out.println("ryokoIalsSequence");

    	// Prepare X in "Trials" mask
    	sprWordMaskTrials.setX(sprWordMaskTrials.getX() - 200);

		// Create & add Modifiers to Sprites
		MoveXModifier modMovXTrials = new MoveXModifier(timeRyokoIals , sprWordMaskTrials.getX(), 
				sprWordMaskTrials.getX() - 500);
		sprWordMaskTrials.registerEntityModifier(modMovXTrials);
		MoveModifier modMovRyoko = new MoveModifier(timeRyokoIals, sprRyoko.getX(), sprRyoko.getY(), 
				sprRyoko.getX() - 20, sprRyoko.getY() - 400);
		sprRyoko.registerEntityModifier(modMovRyoko);

		DelayModifier modDelRyokoIals = new DelayModifier(timeRyokoIals,
				new IEntityModifierListener() {
					@Override
					public void onModifierStarted(IModifier<IEntity> pModifier,
							IEntity pItem) {
						// TODO Auto-generated method stub
					}
					@Override
					public void onModifierFinished(
							IModifier<IEntity> pModifier, IEntity pItem) {
						sprRyoko.setAlpha(0f);
					}
		});
		sprRyoko.registerEntityModifier(modDelRyokoIals);

		DelayModifier modDelWordMaskTrials = new DelayModifier(timeRyokoIals,
				new IEntityModifierListener() {
					@Override
					public void onModifierStarted(IModifier<IEntity> pModifier,
							IEntity pItem) {
						// TODO Auto-generated method stub
					}
					@Override
					public void onModifierFinished(
							IModifier<IEntity> pModifier, IEntity pItem) {
						sprWordMaskTrials.setAlpha(0f);
					}
		});
		sprWordMaskTrials.registerEntityModifier(modDelWordMaskTrials);
    }

    private void logoSequence() {
    	System.out.println("logoSequence");

    	float logoInitScale = 0.5f;
    	float logoFinalScale = 1.2f;
    	float timeLogoSound = 1.0f;

    	// Add Sprites to scene
    	sprLogo = new Sprite(SCREEN_WIDTH/2, SCREEN_HEIGHT/2, 
				ResourceManager.getInstance().intro1Logo.getWidth(), 
				ResourceManager.getInstance().intro1Logo.getHeight(),
				ResourceManager.getInstance().intro1Logo,
				ResourceManager.getInstance().engine.getVertexBufferObjectManager());
    	sprLogo.setScale(logoInitScale);
    	attachChild(sprLogo);

    	// Create & add Modifiers to Sprites
    	IEaseFunction easFnLogo = EaseBackInOut.getInstance();
    	ScaleModifier modScaleLogo = new ScaleModifier(timeLogo, logoInitScale, logoFinalScale, easFnLogo);
    	sprLogo.registerEntityModifier(modScaleLogo);
    	DelayModifier modDelLogo = new DelayModifier(timeLogo,
				new IEntityModifierListener() {
					@Override
					public void onModifierStarted(IModifier<IEntity> pModifier,
							IEntity pItem) {
						// TODO Auto-generated method stub
					}
					@Override
					public void onModifierFinished(
							IModifier<IEntity> pModifier, IEntity pItem) {
						// sprLogo.setAlpha(0f);
					}
		});
    	sprLogo.registerEntityModifier(modDelLogo);

    	// Logo Sound
    	TimerHandler timerSoundLogo = new TimerHandler(timeLogoSound, false, new ITimerCallback(){
            @Override
            public void onTimePassed(TimerHandler pTimerHandler) {
            	SFXManager.playSound(ResourceManager.getInstance().menuIntro1);
            }
        });
        this.registerUpdateHandler(timerSoundLogo);
    }

    private void waitSequence() {
    	System.out.println("waitSequence");

    	TimerHandler timerWaitToSkip = new TimerHandler(timeWait, false, new ITimerCallback(){
            @Override
            public void onTimePassed(TimerHandler pTimerHandler) {
            	System.out.println("SKIPPING!!");
            	skip();
            }
        });
        this.registerUpdateHandler(timerWaitToSkip);
    }

    /**
     * Skip the Intro Scene.
     */
	private void skip() {
        if(pressButtonEnabled){
        	SFXManager.stopMusic(ResourceManager.getInstance().intro1);
            if(GameManager.DEBUG_MODE)
                SceneManager.getInstance().showScene(new TestingScene());
            else
                SceneManager.getInstance().showScene(new MainMenuScene());
        }
	}
	@Override
    public void onPressButtonMenu() {
		skip();
    }
    @Override
    public void onPressButtonO() {
    	skip();
    }
	@Override
	public void onPressButtonU() {
		skip();
	}
	@Override
	public void onPressButtonY() {
		skip();
	}
	@Override
	public void onPressButtonA() {
		skip();
	}
}
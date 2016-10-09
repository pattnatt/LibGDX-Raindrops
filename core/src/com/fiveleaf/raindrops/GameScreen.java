package com.fiveleaf.raindrops;

import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.TimeUtils;

public class GameScreen extends ScreenAdapter{
	 private OrthographicCamera camera;
	 
	 private Sound rainToCactiSound;
	 private Sound rainToUmbrellaSound;
	 private Music raindropsMusic;
	 
	 public SpriteBatch batch;
	 private RaindropsGame raindropsGame;
	 private RainDrop rainDrop;
	 private Umbrella umbrella;
	 private Cacti cacti;
	 private CactiHealthBar cactiHealthBar;
	 private long lastDropTime;
	 private int cactiCounter;
	    
	 public GameScreen(RaindropsGame raindropsGame) {
		 this.raindropsGame = raindropsGame;
		 this.batch = raindropsGame.batch;
		 rainDrop = new RainDrop(this.raindropsGame, this);
		 umbrella = new Umbrella(this.raindropsGame, this);
		 cacti = new Cacti(this.raindropsGame, this);
		 cactiHealthBar = new CactiHealthBar(cacti, this);
	     //Sound
	     	//rainToCactiSound = Gdx.audio.newSound(Gdx.files.internal("drop_on_cacti.wav"));
	     	//rainToUmbrellaSound = Gdx.audio.newSound(Gdx.files.internal("rain.wav"));
	     	//raindropsMusic = Gdx.audio.newMusic(Gdx.files.internal("raindrops.mp3"));
	     //Background Music
	     	//raindropsMusic.setLooping(true)
	     	//raindropsMusic.play();
	     camera = new OrthographicCamera();
	     camera.setToOrtho(false, 160, 144);
	     rainDrop.spawnRaindrop();
	 }
	 
	 @Override
	 public void render(float delta)
	 {
		 Gdx.gl.glClearColor(0.6055f, 0.7344f, 0.0586f, 1.0f);
	     Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		 camera.update();
		 controlUmbrella();
	     spawnRaindrops();
	     checkRainDrops();
	        
	     batch.setProjectionMatrix(camera.combined);
	     batch.begin();
	     umbrella.draw();
	     rainDrop.draw();
	     cacti.draw();
	     cactiHealthBar.draw();
	     batch.end();  
	 }
	 
	 private void controlUmbrella()
	 {
		 if(Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)) {
	            if(umbrella.umbrellaRactangle.getX() > 0) {
	                umbrella.umbrellaRactangle.x = umbrella.umbrellaRactangle.getX() - 3;
	            }
	        }
	        
	        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT )|| Gdx.input.isKeyPressed(Input.Keys.D)) {
	        	if(umbrella.umbrellaRactangle.getX() < (160 - 32)) {
	                umbrella.umbrellaRactangle.x = umbrella.umbrellaRactangle.getX() + 3;
	            }
	        }
	 }
	 
	 private void spawnRaindrops()
	 {
		 if(TimeUtils.nanoTime() - lastDropTime > 300000000) {
	            rainDrop.spawnRaindrop();
	            lastDropTime = TimeUtils.nanoTime();
	            //rainToCactiSound.play();
	     }
	 }
	 
	 private void checkRainDrops()
	 {
		 Iterator<Rectangle> iterRaindrop = rainDrop.raindropsRactangle.iterator(); 
	     while(iterRaindrop.hasNext()) {
	         Rectangle raindrop = iterRaindrop.next();
	         raindrop.y = raindrop.getY() - (100 * Gdx.graphics.getDeltaTime());
	         if(raindrop.y + 16 < 0) {
	             iterRaindrop.remove();
	         }
	         if(raindrop.overlaps(umbrella.umbrellaRactangle)) {
	             //rainToUmbrellaSound.play();
	             iterRaindrop.remove();
	         }
	         else {
	             Iterator<Rectangle> iterCacti = cacti.cactiRectangle.iterator();
	             cactiCounter = 0;
	             while(iterCacti.hasNext()) {
	                 Rectangle cactus = iterCacti.next();
	                 if(raindrop.overlaps(cactus)) {
	                     //rainToCactiSound.play();
	                     iterRaindrop.remove();
	                     cacti.rainHit(cactiCounter);
	                 }
	                 cactiCounter++;
	             }
	         }
	     }
	 }
	    
	 
}

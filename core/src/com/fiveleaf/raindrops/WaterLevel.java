package com.fiveleaf.raindrops;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class WaterLevel {
    
    private RaindropsGame raindropsGame;
    private GameScreen gameScreen;
    private Texture waterTexture;
    
    public WaterLevel (RaindropsGame raindropsGame, GameScreen gameScreen) {
        this.raindropsGame = raindropsGame;
        this.gameScreen = gameScreen;
        waterTexture = new Texture(Gdx.files.internal("Raindrops_WaterTexture.png"));
    }
    
    public int getWaterLevel () {
        int level = gameScreen.rainDropsCount / 2;
        if(level > gameScreen.MAX_WATERLEVEL){
            return gameScreen.MAX_WATERLEVEL;
        }
        return level;
    }
    public void draw() {
        gameScreen.batch.draw(waterTexture, 0, (this.getWaterLevel()) - 160);
    }
}
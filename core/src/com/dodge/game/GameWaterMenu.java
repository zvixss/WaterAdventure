package com.dodge.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameWaterMenu extends Game {

	private SpriteBatch batch;
	private BitmapFont font;

	public void create() {
		batch = new SpriteBatch();
        font = new BitmapFont(); // Use libGDX's default Arial font
		this.setScreen(new TutorialScreen(this));
	}

	public void render() {
		super.render(); // important!
	}

	public void dispose() {
		batch.dispose();
		font.dispose();
	}

	public SpriteBatch getBatch() {
		return batch;
	}

	public BitmapFont getFont() {
		return font;
	}



}

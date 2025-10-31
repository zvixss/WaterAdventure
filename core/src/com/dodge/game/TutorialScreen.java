package com.dodge.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class TutorialScreen implements Screen {
    final GameWaterMenu game;
    final private SpriteBatch batch;
    final private BitmapFont font;
    final private OrthographicCamera camera;

    private final Texture backgroundImage;
    private final Texture arrowKeysIcon;
    private final Texture progressBarBg;
    private final Texture progressBarFill;

    private float timeElapsed;
    private float opacity = 0;
    private boolean fadingIn = true;

    public TutorialScreen(final GameWaterMenu game){
        this.game = game;
        this.batch = game.getBatch();
        this.font = game.getFont();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);

        this.backgroundImage = new Texture("background.png");
        this.arrowKeysIcon = new Texture("arrow_keys.png");
        this.progressBarBg = new Texture("progress_bar_bg.png");
        this.progressBarFill = new Texture("progress_bar_fill.png");

        this.timeElapsed = 0;
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.5f, 0.5f, 0.5f, 1);

        timeElapsed += delta; // Aumenta el tiempo transcurrido
        if (fadingIn){
            opacity += delta;
            if (opacity >= 1) fadingIn = false;
        } else{
            opacity -= delta * 0.5f;
            if (opacity <= 0.5f) fadingIn = true;
        }

        float progress = Math.min(timeElapsed / 5, 1);

        batch.setProjectionMatrix(camera.combined);

        batch.begin();

        batch.draw(backgroundImage, 0, 0, 800, 480);

        font.getData().setScale(2);
        font.setColor(1, 1, 0, 1); // Amarillo
        font.draw(batch, "Instrucciones del juego:", 100, 440);

        //Explicacion objetivo
        font.getData().setScale(1.2f);
        font.setColor(1, 1, 1, 1); // Blanco
        font.draw(batch,"Objetivo: Conduce la lancha y esquiva todos los obstáculos", 120, 400);
        font.draw(batch, "para ganar puntos y evitar chocar.", 120, 370);
        font.draw(batch, "Conchas = 10 pts.", 120, 340);
        font.draw(batch, "Botellas = 50 pts.", 120, 310);

        // Controles
        batch.draw(arrowKeysIcon, 50, 300, 64, 64);
        font.draw(batch, "Usa las flechas para mover la lancha a todas las direcciones!", 120, 280);

        font.setColor(1, 1, 1, opacity);
        font.draw(batch, "Cargando...", 100, 250);

        batch.draw(progressBarBg, 100, 200, 600, 20);
        batch.draw(progressBarFill, 100, 200, 600 * progress, 20);

        batch.end();

        if (progress >= 1 || Gdx.input.isTouched() || Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            game.setScreen(new MainMenuScreen(game));
            dispose();
        }
    }

    @Override
    public void show() {
        // TODO Auto-generated method stub

    }

    @Override
    public void resize(int width, int height) {
        // TODO Auto-generated method stub

    }

    @Override
    public void pause() {
        // TODO Auto-generated method stub

    }

    @Override
    public void resume() {
        // TODO Auto-generated method stub

    }

    @Override
    public void hide() {
        // TODO Auto-generated method stub

    }

    @Override
    public void dispose() {
        // TODO Auto-generated method stub
        backgroundImage.dispose();
        arrowKeysIcon.dispose();
        progressBarBg.dispose();
        progressBarFill.dispose();
    }
}
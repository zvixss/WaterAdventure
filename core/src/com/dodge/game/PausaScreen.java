package com.dodge.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;


public class PausaScreen implements Screen {

    private final GameWaterMenu game;
    final private GameScreen juego;
    final private SpriteBatch batch;
    final private BitmapFont font;
    final private OrthographicCamera camera;

    private final Texture backgroundImage;
    private final Texture resumeButton;
    private final Texture resumeButtonHover;
    private final Texture exitButton;
    private final Texture exitButtonHover;

    private boolean resumeHover;
    private boolean exitHover;

    public PausaScreen (final GameWaterMenu game, GameScreen juego) {
        this.game = game;
        this.juego = juego;
        this.batch = game.getBatch();
        this.font = game.getFont();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);

        this.backgroundImage = new Texture("background.png");
        this.resumeButton = new Texture("play_button.png");
        this.resumeButtonHover = new Texture("play_button_hover.png");
        this.exitButton = new Texture("exit_button.png");
        this.exitButtonHover = new Texture("exit_button_hover.png");

        this.resumeHover = false;
        this.exitHover = false;
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.411f, 0.411f, 0.411f, 1);

        camera.update();
        batch.setProjectionMatrix(camera.combined);

        int mouseX = Gdx.input.getX();
        int mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();

        resumeHover = mouseX >= 100 && mouseX <= 248 && mouseY >= 150 && mouseY <= 298;
        exitHover = mouseX >= 552 && mouseX <= 700 && mouseY >= 150 && mouseY <= 298;

        batch.begin();

        batch.draw(backgroundImage, 0, 0, 800, 480);
        font.draw(batch, "Juego en Pausa ", 320, 400);

        batch.draw(resumeHover ? resumeButtonHover : resumeButton, 100, 150, 148, 148);
        batch.draw(exitHover ? exitButtonHover : exitButton, 552, 150, 148, 148);

        batch.end();

        if (resumeHover && Gdx.input.justTouched()) {
            game.setScreen(juego);
            dispose();
        }
        if (exitHover && Gdx.input.justTouched()) {
            Gdx.app.exit();
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
        resumeButton.dispose();
        resumeButtonHover.dispose();
        exitButton.dispose();
        exitButtonHover.dispose();
    }

}
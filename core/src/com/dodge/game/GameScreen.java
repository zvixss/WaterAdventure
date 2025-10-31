package com.dodge.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class GameScreen implements Screen {
    final GameLluviaMenu game;
    private OrthographicCamera camera;
    private SpriteBatch batch;
    private BitmapFont font;
    private Lancha lancha;
    private Obstaculos obstaculos;
    private Texture fondo;



    public GameScreen(final GameLluviaMenu game) {
        this.game = game;
        this.batch = game.getBatch();
        this.font = game.getFont();

        // Inicializar sonidos y texturas de los objetos
        Sound choqueSound = Gdx.audio.newSound(Gdx.files.internal("choque.mp3"));
        lancha = new Lancha(new Texture(Gdx.files.internal("lancha.png")), choqueSound);

        Texture concha = new Texture(Gdx.files.internal("concha.png"));
        Texture boya = new Texture(Gdx.files.internal("boya.png"));
        Texture glaciar = new Texture(Gdx.files.internal("glaciar.png"));
        Texture medusa = new Texture(Gdx.files.internal("medusa.png"));
        Sound coinSound = Gdx.audio.newSound(Gdx.files.internal("moneda.mp3"));
        Music instrumentalMusic = Gdx.audio.newMusic(Gdx.files.internal("instrumental.mp3"));

        obstaculos = new Obstaculos(concha, boya, glaciar, medusa, coinSound, instrumentalMusic);

        // Cargar la textura del fondo
        fondo = new Texture(Gdx.files.internal("game_background.png"));

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
        batch = new SpriteBatch();

        lancha.crear();
        obstaculos.crear();
    }

    @Override
    public void render(float delta) {

        ScreenUtils.clear(0, 0, 0.2f, 1);

        camera.update();

        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        // Dibujar fondo y HUD
        batch.draw(fondo, 0, 0, 800, 480);
        font.draw(batch, "Monedas totales: " + lancha.getPuntos(), 5, 475);
        font.draw(batch, "Vidas : " + lancha.getVidas(), 670, 475);
        font.draw(batch, "HighScore : " + game.getHigherScore(), camera.viewportWidth / 2 - 50, 475);

        // Actualizar movimiento y verificar colisiones
        if (!lancha.estaHerido()) {
            lancha.actualizarMovimiento();
            if (!obstaculos.actualizarMovimiento(lancha)) {
                // Si el juego termina, actualizar HighScore y pasar a la pantalla Game Over
                if (game.getHigherScore() < lancha.getPuntos())
                    game.setHigherScore(lancha.getPuntos());
                game.setScreen(new GameOverScreen(game, lancha.getPuntos()));
                dispose();
            }



        }

        lancha.dibujar(batch);
        obstaculos.actualizarDibujoLluvia(batch);

        batch.end();
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void show() {
        obstaculos.continuar();

    }

    @Override
    public void hide() {

    }

    @Override
    public void pause() {
        obstaculos.pausar();
        game.setScreen(new PausaScreen(game, this));
    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        lancha.destruir();
        obstaculos.destruir();
        fondo.dispose();
    }
}
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
    final GameWaterMenu game;
    final private OrthographicCamera camera;
    private SpriteBatch batch; // Usará el batch de GameWaterMenu
    final private BitmapFont font;
    final private Lancha lancha;
    final private Obstaculos obstaculos;
    final private Texture fondo;


    public GameScreen(final GameWaterMenu game) {
        this.game = game;
        this.batch = game.getBatch(); // <-- CORRECCIÓN IMPORTANTE 1: Usar el batch principal
        this.font = game.getFont();

        // Inicializar sonidos y texturas de los objetos
        Sound choqueSound = Gdx.audio.newSound(Gdx.files.internal("choque.mp3"));
        lancha = new Lancha(new Texture(Gdx.files.internal("lancha.png")), choqueSound);

        // Ya no cargamos las texturas de los obstáculos aquí
        Sound coinSound = Gdx.audio.newSound(Gdx.files.internal("moneda.mp3"));
        Music instrumentalMusic = Gdx.audio.newMusic(Gdx.files.internal("instrumental.mp3"));

        // <-- MODIFICACIÓN: Constructor de Obstaculos simplificado
        // La clase Obstaculos ahora carga sus propias texturas.
        obstaculos = new Obstaculos(coinSound, instrumentalMusic);

        // Cargar la textura del fondo
        fondo = new Texture(Gdx.files.internal("game_background.png"));

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);

        // batch = new SpriteBatch(); // <-- CORRECCIÓN IMPORTANTE 2: ELIMINAR ESTA LÍNEA

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

            // La lógica de colisión ahora está dentro de actualizarMovimiento
            if (!obstaculos.actualizarMovimiento(lancha)) {
                // Si el juego termina, actualizar HighScore y pasar a la pantalla Game Over
                if (game.getHigherScore() < lancha.getPuntos())
                    game.setHigherScore(lancha.getPuntos());
                game.setScreen(new GameOverScreen(game, lancha.getPuntos()));
                dispose();
            }

        }

        lancha.dibujar(batch);
        obstaculos.actualizarDibujoObjeto(batch); // Llama al método de dibujo refactorizado

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
        // El batch NO se destruye aquí (se destruye en GameWaterMenu)
        lancha.destruir();
        obstaculos.destruir(); // Ahora también destruye texturas y sonidos de obstáculos
        fondo.dispose();
    }
}
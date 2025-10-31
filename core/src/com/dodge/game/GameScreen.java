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


	//boolean activo = true;

	public GameScreen(final GameLluviaMenu game) {
		this.game = game;
        this.batch = game.getBatch();
        this.font = game.getFont();
		  // load the images for the droplet and the bucket, 64x64 pixels each
		  Sound choqueSound = Gdx.audio.newSound(Gdx.files.internal("choque.mp3"));
		  lancha = new Lancha(new Texture(Gdx.files.internal("lancha.png")),choqueSound);

	      // load the drop sound effect and the rain background "music"
         Texture gota = new Texture(Gdx.files.internal("drop.png"));
         Texture gotaMala = new Texture(Gdx.files.internal("dropBad.png"));

         Sound dropSound = Gdx.audio.newSound(Gdx.files.internal("drop.wav"));

	     Music instrumentalMusic = Gdx.audio.newMusic(Gdx.files.internal("instrumental.mp3"));
         obstaculos = new Obstaculos(gota, gotaMala, dropSound, instrumentalMusic);

	      // camera
	      camera = new OrthographicCamera();
	      camera.setToOrtho(false, 800, 480);
	      batch = new SpriteBatch();
	      // creacion del tarro
	      lancha.crear();

	      // creacion de la lluvia
	      obstaculos.crear();
	}

	@Override
	public void render(float delta) {
		//limpia la pantalla con color azul obscuro.
		ScreenUtils.clear(0, 0, 0.2f, 1);
		//actualizar matrices de la cámara
		camera.update();
		//actualizar
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		//dibujar textos
		font.draw(batch, "Gotas totales: " + lancha.getPuntos(), 5, 475);
		font.draw(batch, "Vidas : " + lancha.getVidas(), 670, 475);
		font.draw(batch, "HighScore : " + game.getHigherScore(), camera.viewportWidth/2-50, 475);

		if (!lancha.estaHerido()) {
			// movimiento del tarro desde teclado
	        lancha.actualizarMovimiento();
			// caida de la lluvia
	       if (!obstaculos.actualizarMovimiento(lancha)) {
	    	  //actualizar HigherScore
	    	  if (game.getHigherScore()<lancha.getPuntos())
	    		  game.setHigherScore(lancha.getPuntos());
	    	  //ir a la ventana de finde juego y destruir la actual
	    	  game.setScreen(new GameOverScreen(game));
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
	  // continuar con sonido de lluvia
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

	}

}

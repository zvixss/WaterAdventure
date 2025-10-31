package com.dodge.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;


public class Lancha {
	private Rectangle hitbox;
	private Texture carImage;
	private Sound sonidoChoque;
	private int vidas = 3;
	private int puntos = 0;
	private int velx = 400;
	private boolean herido = false;
	private int tiempoHeridoMax=50;
	private int tiempoHerido;

	public Lancha(Texture tex, Sound ss) {
		carImage = tex;
		sonidoChoque = ss;
	}

	public int getVidas() {
		return vidas;
	}

	public int getPuntos() {
		return puntos;
	}

	public Rectangle getArea() {
		return hitbox;
	}

	public void sumarPuntos(int pp) {
		puntos+=pp;
	}


	public void crear() {
		hitbox = new Rectangle();
		hitbox.x = 800 / 2 - 64 / 2;
		hitbox.y = 20;
		hitbox.width = 42;
		hitbox.height = 70;
	}

	public void dañar() {
		vidas--;
		herido = true;
		tiempoHerido=tiempoHeridoMax;
		sonidoChoque.play();
	}

	public void dibujar(SpriteBatch batch) {
		if (!herido)
		   batch.draw(carImage, hitbox.x, hitbox.y);
		else {
			batch.draw(carImage, hitbox.x, hitbox.y+ MathUtils.random(-5,5));
		    tiempoHerido--;
		    if (tiempoHerido<=0) herido = false;
		}
	}


	public void actualizarMovimiento() {
		// movimiento desde mouse/touch
		/*if(Gdx.input.isTouched()) {
			    Vector3 touchPos = new Vector3();
			    touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			    camera.unproject(touchPos);
			    bucket.x = touchPos.x - 64 / 2;
		}*/
		//movimiento desde teclado
		if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) hitbox.x -= velx * Gdx.graphics.getDeltaTime();
		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) hitbox.x += velx * Gdx.graphics.getDeltaTime();
		if(Gdx.input.isKeyPressed(Input.Keys.UP)) hitbox.y += velx * Gdx.graphics.getDeltaTime(); // Movimiento hacia arriba
		if(Gdx.input.isKeyPressed(Input.Keys.DOWN)) hitbox.y -= velx * Gdx.graphics.getDeltaTime(); // Movimineto hacia abajo
		// que no se salga de los bordes izq y der.
		if(hitbox.x < 0) hitbox.x = 0;
		if(hitbox.x > 800 - 64) hitbox.x = 800 - 64;
		if(hitbox.y < 0) hitbox.y = 0; // Limite inferior
		if(hitbox.y > 480 - hitbox.height) hitbox.y = 480 - hitbox.height; // Limite Superior
	}


	public void destruir() {
		carImage.dispose();
	}

	public boolean estaHerido() {
	   return herido;
   }

}

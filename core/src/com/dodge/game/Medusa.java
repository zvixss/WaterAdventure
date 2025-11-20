package com.dodge.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Medusa extends Entidad {

    public Medusa(Texture textura, float x, float y, Lancha jugador) {
        super(textura, x, y, 60, 60, 0.25f);
        this.setEstrategiaMovimiento(new MovimientoPersecucion(jugador, 50f, 250f));
    }

    @Override
    public void dibujar(SpriteBatch batch) {
        float drawX = hitbox.x - ((textura.getWidth() * escala - hitbox.width) / 2);
        float drawY = hitbox.y - (textura.getHeight() * escala - hitbox.height) / 2;

        batch.draw(textura, drawX, drawY,
                textura.getWidth() * escala,
                textura.getHeight() * escala);
    }
}
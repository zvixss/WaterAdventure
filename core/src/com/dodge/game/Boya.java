package com.dodge.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Boya extends Entidad {

    public Boya(Texture textura, float x, float y) {
        super(textura, x, y, textura.getWidth() * 0.1f, textura.getHeight() * 0.1f, 0.1f);
        this.setEstrategiaMovimiento(new MovimientoVerticalDescendente(300));
    }

    @Override
    public void dibujar(SpriteBatch batch) {
        batch.draw(textura, hitbox.x, hitbox.y,
                textura.getWidth() * escala, textura.getHeight() * escala);
    }
}
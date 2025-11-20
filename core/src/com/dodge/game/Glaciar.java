package com.dodge.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Glaciar extends Entidad {

    public Glaciar(Texture textura, float x, float y) {
        super(textura, x, y, 20, 28, 0.3f);
        this.setEstrategiaMovimiento(new MovimientoVerticalDescendente(300));
    }

    @Override
    public void dibujar(SpriteBatch batch) {
        float drawX = hitbox.x - ((textura.getWidth() * escala - hitbox.width) / 2);
        batch.draw(textura, drawX, hitbox.y,
                textura.getWidth() * escala, textura.getHeight() * escala);
    }
}
package com.dodge.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Medusa extends Entidad {

    public Medusa(Texture textura, float x, float y) {
        super(textura, x, y, 60, 1, 0.25f);
    }

    @Override
    public void dibujar(SpriteBatch batch) {
        float drawX = hitbox.x - ((textura.getWidth() * escala - hitbox.width) / 2);
        float drawY = hitbox.y - (textura.getHeight() * escala * 0.2f); // Ajuste vertical
        batch.draw(textura, drawX, drawY,
                textura.getWidth() * escala, textura.getHeight() * escala);
    }
}
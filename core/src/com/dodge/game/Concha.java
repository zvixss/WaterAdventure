package com.dodge.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Concha extends Entidad implements IRecolectable {

    public Concha(Texture textura, float x, float y) {
        super(textura, x, y, 32, 32, 0.15f);
        this.setEstrategiaMovimiento(new MovimientoVerticalDescendente(300));
    }

    @Override
    public void dibujar(SpriteBatch batch) {
        batch.draw(textura, hitbox.x, hitbox.y,
                textura.getWidth() * escala, textura.getHeight() * escala);
    }

    @Override
    public int getPuntos() {
        return 10;
    }
}
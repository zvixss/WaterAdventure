package com.dodge.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Medusa extends Entidad {

    private float velocidadPersecucion = 50f;
    private float velocidadVertical = 50f;
    private Lancha objetivo;

    public Medusa(Texture textura, float x, float y, Lancha jugador) {
        super(textura, x, y, 60, 60, 0.25f);
        this.objetivo = jugador;
    }

    @Override
    protected void reaccionar() {
        float delta = Gdx.graphics.getDeltaTime();


        hitbox.y -= velocidadVertical * delta;


        float centroMedusa = hitbox.x + hitbox.width / 2f;
        float centroJugador = objetivo.getArea().x + objetivo.getArea().width / 2f;

        if (centroJugador > centroMedusa) {
            hitbox.x += velocidadPersecucion * delta;
        } else if (centroJugador < centroMedusa) {
            hitbox.x -= velocidadPersecucion * delta;
        }


        float centroYMedusa = hitbox.y + hitbox.height / 2f;
        float centroYJugador = objetivo.getArea().y + objetivo.getArea().height / 2f;

        if (centroYJugador > centroYMedusa) {
            hitbox.y += velocidadPersecucion * 0.4f * delta;
        } else if (centroYJugador < centroYMedusa) {
            hitbox.y -= velocidadPersecucion * 0.4f * delta;
        }
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

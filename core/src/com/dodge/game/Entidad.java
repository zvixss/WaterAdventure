package com.dodge.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;


public abstract class Entidad {

    protected Rectangle hitbox;
    protected Texture textura;
    protected float escala;

    public Entidad(Texture textura, float x, float y, float anchoHitbox, float altoHitbox, float escala) {
        this.textura = textura;
        this.hitbox = new Rectangle(x, y, anchoHitbox, altoHitbox);
        this.escala = escala;
    }

    public final void actualizar(float deltaTime) {
        mover(deltaTime);
        reaccionar();
    }


    protected void mover(float deltaTime) {
        hitbox.y -= 300 * deltaTime;
    }


    protected void reaccionar() {
    }

    public abstract void dibujar(SpriteBatch batch);

    public Rectangle getHitbox() {
        return hitbox;
    }

    public boolean estaFueraDePantalla() {
        return hitbox.y + (textura.getHeight() * escala) < 0;
    }
}
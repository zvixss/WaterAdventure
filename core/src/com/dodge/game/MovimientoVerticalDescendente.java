package com.dodge.game;

public class MovimientoVerticalDescendente implements EstrategiaMovimiento {
    private float velocidad;

    public MovimientoVerticalDescendente(float velocidad) {
        this.velocidad = velocidad;
    }

    @Override
    public void mover(Entidad entidad, float delta) {
        entidad.getHitbox().y -= velocidad * delta;
    }
}
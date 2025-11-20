package com.dodge.game;

public class MovimientoPersecucion implements EstrategiaMovimiento {
    private Lancha objetivo;
    private float velocidadPersecucion;
    private float velocidadVertical;

    public MovimientoPersecucion(Lancha objetivo, float velocidadPersecucion, float velocidadVertical) {
        this.objetivo = objetivo;
        this.velocidadPersecucion = velocidadPersecucion;
        this.velocidadVertical = velocidadVertical;
    }

    @Override
    public void mover(Entidad entidad, float delta) {
        entidad.getHitbox().y -= velocidadVertical * delta;

        float centroEntidad = entidad.getHitbox().x + entidad.getHitbox().width / 2f;
        float centroObjetivo = objetivo.getArea().x + objetivo.getArea().width / 2f;

        if (centroObjetivo > centroEntidad) {
            entidad.getHitbox().x += velocidadPersecucion * delta;
        } else if (centroObjetivo < centroEntidad) {
            entidad.getHitbox().x -= velocidadPersecucion * delta;
        }

        float centroYEntidad = entidad.getHitbox().y + entidad.getHitbox().height / 2f;
        float centroYObjetivo = objetivo.getArea().y + objetivo.getArea().height / 2f;

        if (centroYObjetivo > centroYEntidad) {
            entidad.getHitbox().y += velocidadPersecucion * 0.4f * delta;
        } else if (centroYObjetivo < centroYEntidad) {
            entidad.getHitbox().y -= velocidadPersecucion * 0.4f * delta;
        }
    }
}
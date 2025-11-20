package com.dodge.game;

public interface FabricaElementos {
    Entidad crearObstaculo(float x, float y);
    Entidad crearRecolectable(float x, float y);
}
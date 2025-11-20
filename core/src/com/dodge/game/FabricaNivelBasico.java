package com.dodge.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;

public class FabricaNivelBasico implements FabricaElementos {

    private final Texture texBoya;
    private final Texture texGlaciar;
    private final Texture texMedusa;
    private final Texture texBotella;
    private final Texture texConcha;
    private final Lancha lancha;

    public FabricaNivelBasico(Texture texBoya, Texture texGlaciar, Texture texMedusa,
                              Texture texBotella, Texture texConcha, Lancha lancha) {
        this.texBoya = texBoya;
        this.texGlaciar = texGlaciar;
        this.texMedusa = texMedusa;
        this.texBotella = texBotella;
        this.texConcha = texConcha;
        this.lancha = lancha;
    }

    @Override
    public Entidad crearObstaculo(float x, float y) {
        int subtipo = MathUtils.random(0, 2);

        if (subtipo == 0) {
            return new Boya(texBoya, x, y);
        } else if (subtipo == 1) {
            return new Glaciar(texGlaciar, x, y);
        } else {
            return new Medusa(texMedusa, x, y, lancha);
        }
    }

    @Override
    public Entidad crearRecolectable(float x, float y) {
        if (MathUtils.randomBoolean()) {
            return new Botella(texBotella, x, y);
        } else {
            return new Concha(texConcha, x, y);
        }
    }
}
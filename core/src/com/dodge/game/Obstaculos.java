package com.dodge.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.Iterator;

public class Obstaculos {

    private Array<Entidad> entidades;
    private long lastSpawnTime;

    final private Sound coinSound;
    final private Music instrumentalMusic;

    final private Texture texBoya;
    final private Texture texGlaciar;
    final private Texture texMedusa;
    final private Texture texConcha;
    final private Texture texBotella;

    public Obstaculos(Sound coinSound, Music instrumentalMusic) {
        this.coinSound = coinSound;
        this.instrumentalMusic = instrumentalMusic;

        this.texBoya = new Texture(Gdx.files.internal("boya.png"));
        this.texGlaciar = new Texture(Gdx.files.internal("glaciar.png"));
        this.texMedusa = new Texture(Gdx.files.internal("medusa.png"));
        this.texConcha = new Texture(Gdx.files.internal("concha.png"));
        this.texBotella = new Texture(Gdx.files.internal("botella.png"));
    }

    public void crear() {
        entidades = new Array<>();
        crearObstaculo();

        instrumentalMusic.setLooping(true);
        instrumentalMusic.play();
    }

    private void crearObstaculo() {
        float x = MathUtils.random(0, 800 - 64);
        float y = 480;
        int tipo = MathUtils.random(1, 10) < 5 ? 1 : 2;

        if (tipo == 1) {
            int subtipo = MathUtils.random(0, 2);
            if (subtipo == 0) {
                entidades.add(new Boya(texBoya, x, y));
            } else if (subtipo == 1) {
                entidades.add(new Glaciar(texGlaciar, x, y));
            } else {
                entidades.add(new Medusa(texMedusa, x, y));
            }
        } else {
            if (MathUtils.random(1, 10) == 1) {
                entidades.add(new Botella(texBotella, x, y));
            } else {
                entidades.add(new Concha(texConcha, x, y));
            }
        }

        lastSpawnTime = TimeUtils.nanoTime();
    }

    public boolean actualizarMovimiento(Lancha lancha) {
        if (TimeUtils.nanoTime() - lastSpawnTime > 100000000) {
            crearObstaculo();
        }

        for (Iterator<Entidad> iter = entidades.iterator(); iter.hasNext(); ) {
            Entidad entidad = iter.next();
            entidad.actualizar(Gdx.graphics.getDeltaTime());

            if (entidad.estaFueraDePantalla()) {
                iter.remove();
                continue;
            }

            if (entidad.getHitbox().overlaps(lancha.getArea())) {
                if (entidad instanceof IRecolectable) {
                    IRecolectable recolectable = (IRecolectable) entidad;
                    lancha.sumarPuntos(recolectable.getPuntos());

                    coinSound.play();
                } else {
                    lancha.dañar();
                    if (lancha.getVidas() <= 0) {
                        return false;
                    }
                }
                iter.remove();
            }
        }
        return true;
    }

    public void actualizarDibujoObjeto(SpriteBatch batch) {
        for (Entidad entidad : entidades) {
            entidad.dibujar(batch);
        }
    }

    public void destruir() {
        coinSound.dispose();
        instrumentalMusic.dispose();

        texBoya.dispose();
        texGlaciar.dispose();
        texMedusa.dispose();
        texConcha.dispose();
        texBotella.dispose();
    }

    public void pausar() {
        instrumentalMusic.pause();
    }

    public void continuar() {
        instrumentalMusic.play();
    }
}
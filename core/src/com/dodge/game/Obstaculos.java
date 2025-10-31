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

/**
 * Clase "Manejadora" refactorizada.
 * Ahora usa un único Array<Entidad> y aplica polimorfismo.
 */
public class Obstaculos {

    // Un solo Array que guarda Entidades (Boya, Glaciar, Medusa, Concha)
    private Array<Entidad> entidades;
    private long lastSpawnTime;

    // Sonidos y música (pasados desde GameScreen)
    final private Sound coinSound;
    final private Music instrumentalMusic;

    // Texturas (administradas y cargadas por esta clase)
    final private Texture texBoya;
    final private Texture texGlaciar;
    final private Texture texMedusa;
    final private Texture texConcha;

    /**
     * El constructor ahora solo recibe los sonidos.
     * Las texturas las carga él mismo.
     */
    public Obstaculos(Sound coinSound, Music instrumentalMusic) {
        this.coinSound = coinSound;
        this.instrumentalMusic = instrumentalMusic;

        // Cargar todas las texturas que este manejador necesita
        this.texBoya = new Texture(Gdx.files.internal("boya.png"));
        this.texGlaciar = new Texture(Gdx.files.internal("glaciar.png"));
        this.texMedusa = new Texture(Gdx.files.internal("medusa.png"));
        this.texConcha = new Texture(Gdx.files.internal("concha.png"));
    }

    public void crear() {
        entidades = new Array<>();
        crearObstaculo(); // Creamos el primero

        instrumentalMusic.setLooping(true);
        instrumentalMusic.play();
    }

    private void crearObstaculo() {
        float x = MathUtils.random(0, 800 - 64);
        float y = 480; // Posición inicial arriba de la pantalla

        // Decide el tipo (misma lógica de antes)
        int tipo = MathUtils.random(1, 10) < 5 ? 1 : 2;

        if (tipo == 1) { // Obstáculo dañino
            int subtipo = MathUtils.random(0, 2);
            if (subtipo == 0) {
                entidades.add(new Boya(texBoya, x, y));
            } else if (subtipo == 1) {
                entidades.add(new Glaciar(texGlaciar, x, y));
            } else {
                entidades.add(new Medusa(texMedusa, x, y));
            }
        } else { // Recolectable
            entidades.add(new Concha(texConcha, x, y));
        }

        lastSpawnTime = TimeUtils.nanoTime();
    }

    /**
     * Actualiza el movimiento, las colisiones y el spawn.
     * Devuelve 'false' si el jugador pierde, 'true' si el juego continúa.
     */
    public boolean actualizarMovimiento(Lancha lancha) {
        // 1. Spawnea un nuevo obstáculo si pasó el tiempo
        if (TimeUtils.nanoTime() - lastSpawnTime > 100000000) {
            crearObstaculo();
        }

        // 2. Mueve, comprueba colisiones y elimina entidades
        // Usamos un Iterador para poder eliminar objetos de la lista de forma segura
        for (Iterator<Entidad> iter = entidades.iterator(); iter.hasNext(); ) {
            Entidad entidad = iter.next();
            entidad.actualizarMovimiento(Gdx.graphics.getDeltaTime());

            // 2a. Eliminar si está fuera de pantalla
            if (entidad.estaFueraDePantalla()) {
                iter.remove();
                continue; // Pasa al siguiente objeto
            }

            // 2b. Comprobar colisión con la lancha
            if (entidad.getHitbox().overlaps(lancha.getArea())) {

                // Aquí usamos la interfaz (GM1.5) con 'instanceof'
                // para ver si el objeto es de tipo IRecolectable
                if (entidad instanceof IRecolectable) {
                    lancha.sumarPuntos(10);
                    coinSound.play();
                } else {
                    // Si no es Recolectable, es un obstáculo que hace daño
                    lancha.dañar();
                    if (lancha.getVidas() <= 0) {
                        return false; // Juego terminado
                    }
                }
                iter.remove(); // Eliminar el objeto después de colisionar
            }
        }
        return true; // El juego continúa
    }

    /**
     * Dibuja todas las entidades.
     * Gracias al polimorfismo, solo llamamos a entidad.dibujar()
     * y Java sabe cuál versión (Boya, Glaciar, etc.) ejecutar.
     */
    public void actualizarDibujoObjeto(SpriteBatch batch) {
        for (Entidad entidad : entidades) {
            entidad.dibujar(batch);
        }
    }

    /**
     * Libera la memoria de los recursos.
     */
    public void destruir() {
        coinSound.dispose();
        instrumentalMusic.dispose();

        // Liberar las texturas que cargó esta clase
        texBoya.dispose();
        texGlaciar.dispose();
        texMedusa.dispose();
        texConcha.dispose();
    }

    public void pausar() {
        instrumentalMusic.pause();
    }

    public void continuar() {
        instrumentalMusic.play();
    }
}
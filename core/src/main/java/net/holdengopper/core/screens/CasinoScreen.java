package net.holdengopper.core.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

public class CasinoScreen extends ScreenAdapter {
    private final SpriteBatch batch;
    private final Texture img;
    private final ShapeRenderer shapeRenderer;

    private final Vector2 pos;

    public CasinoScreen() {
        batch = new SpriteBatch();
        img = new Texture("test.jpeg");
        shapeRenderer = new ShapeRenderer();

        pos = new Vector2(100, 100);
    }

    @Override
    public void render(float delta) {
        float maxVelocity = 100.0f;
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            pos.y += maxVelocity * delta;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            pos.x -= maxVelocity * delta;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            pos.y -= maxVelocity * delta;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            pos.x += maxVelocity * delta;
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) Gdx.app.exit();

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
            batch.draw(img, Gdx.graphics.getWidth() - img.getWidth(), Gdx.graphics.getHeight() - img.getHeight());
        batch.end();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(Color.CYAN);
            shapeRenderer.rect(pos.x, pos.y, 100, 100);
        shapeRenderer.end();
    }

    @Override
    public void dispose() {
        super.dispose();
        batch.dispose();
        img.dispose();
    }

    @Override
    public void hide() {
        this.dispose();
    }
}

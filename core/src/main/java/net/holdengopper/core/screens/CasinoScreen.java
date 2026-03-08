package net.holdengopper.core.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.*;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Vector3;
import net.holdengopper.core.screens.menus.MenuScreen;

public class CasinoScreen extends MenuScreen {
    private final PerspectiveCamera camera;
    private final ModelBatch modelBatch;
    private final Model model;
    private final ModelInstance instance;
    private final Environment environment;
    private final Vector3 dir;
    private final Vector3 pos;
    private final Vector3 up;

    public float sensitivity = 0.2f;

    public CasinoScreen() {
        Gdx.gl.glEnable(GL20.GL_DEPTH_TEST);
        Gdx.input.setCursorCatched(true);

        camera = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.position.set(0f, 0f, 10f);
        camera.lookAt(0f, 0f, 0f);
        camera.near = 0.1f;
        camera.far = 300f;
        camera.update();

        modelBatch = new ModelBatch();

        ModelBuilder builder = new ModelBuilder();
        model = builder.createBox(
                2f, 2f, 2f,
                new Material(ColorAttribute.createDiffuse(Color.GREEN)),
                VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal
        );

        instance = new ModelInstance(model);

        environment = new Environment();
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.8f, 0.8f, 0.8f, 1f));
        environment.add(new DirectionalLight().set(1f,1f,1f,-1f,-0.8f,-0.2f));

        dir = new Vector3();
        pos = new Vector3();
        up = new Vector3();
    }

    @Override
    public void render(float delta) {
        float maxVelocity = 10.0f;
        float speed = maxVelocity * delta;

        int mouseX = Gdx.input.getDeltaX();
        int mouseY = Gdx.input.getDeltaY();

        Vector3 right = new Vector3();
        right.set(camera.direction).crs(camera.up).nor();

        camera.rotate(Vector3.Y, mouseX * sensitivity);
        camera.rotate(right, mouseY * sensitivity);

        camera.direction.nor();

        dir.set(camera.direction).scl(speed);
        pos.set(camera.position).scl(speed);

        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            camera.position.add(dir);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            camera.position.sub(dir);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            right.set(camera.direction).crs(camera.up).nor();
            right.scl(speed);
            camera.position.sub(right);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            right.set(camera.direction).crs(camera.up).nor();
            right.scl(speed);
            camera.position.add(right);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            up.set(camera.up).scl(speed);
            camera.position.add(up);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) {
            up.set(camera.up).scl(speed);
            camera.position.sub(up);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit();
        }

        camera.update(true);

        Gdx.gl.glViewport(0,0,Gdx.graphics.getBackBufferWidth(),Gdx.graphics.getBackBufferHeight());
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        modelBatch.begin(camera);
        modelBatch.render(instance, environment);
        modelBatch.end();
    }

    @Override
    public void resize(int width, int height) {
        camera.viewportWidth = width;
        camera.viewportHeight = height;
        camera.update(true);
    }

    @Override
    public void dispose() {
        super.dispose();
        modelBatch.dispose();
        model.dispose();
    }

    @Override
    public void hide() {
        this.dispose();
        Gdx.input.setCursorCatched(false);
    }
}

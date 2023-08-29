package org.yunghegel.gdx.utils.setup;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Cubemap;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.FloatAttribute;
import com.badlogic.gdx.graphics.g3d.environment.PointLight;
import com.badlogic.gdx.graphics.g3d.shaders.DepthShader;
import com.badlogic.gdx.graphics.g3d.utils.DepthShaderProvider;
import com.badlogic.gdx.math.Vector3;
import net.mgsx.gltf.scene3d.scene.Scene;
import net.mgsx.gltf.scene3d.scene.SceneManager;
import net.mgsx.gltf.scene3d.scene.SceneSkybox;
import net.mgsx.gltf.scene3d.shaders.PBRCommon;
import net.mgsx.gltf.scene3d.shaders.PBRShaderConfig;
import net.mgsx.gltf.scene3d.shaders.PBRShaderProvider;
import org.yunghegel.gdx.utils.graphics.EnvUtil;
import org.yunghegel.gdx.utils.graphics.model.ModelBuilderPlus;

public class SceneManagerPlus extends SceneManager {

    private EnvironmentPlus environmentPlus;
    private boolean usesEnvPlus = false;
    private boolean useDefaultConfig = false;
    public boolean updateSkyboxRotation = false;
    public PerspectiveCamera camera;

    public SceneManagerPlus(int bones, int dir, int point, int spot){
        PBRShaderConfig config = PBRShaderProvider.createDefaultConfig();
        DepthShader.Config depthConfig = PBRShaderProvider.createDefaultDepthConfig();
        config.numBones = bones;
        config.numDirectionalLights = dir;
        config.numPointLights = point;
        config.numSpotLights = spot;


        depthConfig.numBones = bones;
        depthConfig.numDirectionalLights = dir;
        depthConfig.numPointLights = point;
        depthConfig.numSpotLights = spot;

        setShaderProvider(new PBRShaderProvider(config));
        setDepthShaderProvider(new DepthShaderProvider(depthConfig));

        camera = new PerspectiveCamera(60f, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.near = .1f;
        camera.far = 1000;

        camera(camera);
    }

    public SceneManagerPlus(){
        this(64, 1, 8, 8);
        initEnvPlus();
        setAmbientLight(1);
    }

    @Override
    protected void updateSkyboxRotation() {
        if(updateSkyboxRotation)
            super.updateSkyboxRotation();
    }

    @Override
    public void render() {
       renderSkybox();
       renderColors();

    }

    public SceneManagerPlus(boolean useDefaultConfig){
        this(64, 1, 8, 8);
        this.useDefaultConfig = useDefaultConfig;
        initEnvPlus();
        setAmbientLight(1);
    }

    public void renderSkybox(){
        getBatch().begin(camera);
        getBatch().render(getSkyBox());
        getBatch().end();
    }

    public SceneManagerPlus grid(float step, int size, float alpha,boolean useAxis, float ...subdivisions){
        add(new ModelInstance(ModelBuilderPlus.createGridLines(alpha,size,step,subdivisions)));
        return this;
    }

    public SceneManagerPlus grid(float step, float alpha,boolean useAxis){
        add(new ModelInstance(ModelBuilderPlus.createGrid(step,alpha,useAxis)));
        return this;
    }

    public SceneManagerPlus grid(float step){
        return grid(step, 0.5f,true);
    }

    public SceneManagerPlus grid(){
       return grid(0.5f);
    }

    public SceneManagerPlus camera(PerspectiveCamera camera){
        this.camera = camera;
        setCamera(camera);
        return this;
    }

    private SceneManagerPlus add(ModelInstance modelInstance){
        addIfMissing(modelInstance);
        return this;
    }

    public SceneManagerPlus add(ModelInstance ...modelInstance){
        for(ModelInstance m : modelInstance)
            add(m);
        return this;
    }

    private SceneManagerPlus remove(ModelInstance modelInstance){
        if(getRenderableProviders().contains(modelInstance, true))
            getRenderableProviders().removeValue(modelInstance, true);
        return this;
    }

    public SceneManagerPlus remove(ModelInstance ...modelInstance){
        for(final ModelInstance m : modelInstance)
            remove(m);
        return this;
    }

    private SceneManagerPlus add(Scene scene){
        addIfMissing(scene);
        return this;
    }

    public SceneManagerPlus add(Scene ...scene){
        for(final Scene s : scene)
            add(s);
        return this;
    }

    private SceneManagerPlus remove(Scene scene){
        removeScene(scene);
        return this;
    }

    public SceneManagerPlus remove(Scene ...scene){
        for(Scene s : scene)
            remove(s);
        return this;
    }

    private SceneManagerPlus add(PointLight pointLight){
        getEnvironmentPlus().add(pointLight);
        return this;
    }

    public SceneManagerPlus add(PointLight ...pointLight){
        for(final PointLight p : pointLight)
            add(p);
        return this;
    }

    private SceneManagerPlus remove(PointLight pointLight){
        getEnvironmentPlus().remove(pointLight);
        return this;
    }

    public SceneManagerPlus remove(PointLight ...pointLight){
        for(final PointLight p : pointLight)
            remove(p);
        return this;
    }

    private SceneManagerPlus addIfMissing(ModelInstance modelInstance){
        if(!getRenderableProviders().contains(modelInstance, true))
            getRenderableProviders().add(modelInstance);
        return this;
    }

    private SceneManagerPlus addIfMissing(Scene scene){
        if(!getRenderableProviders().contains(scene, true))
            add(scene);
        return this;
    }

    public SceneManagerPlus initEnvPlus(){
        environmentPlus = new EnvironmentPlus(useDefaultConfig);
        environmentPlus.set(new ColorAttribute(ColorAttribute.AmbientLight, 1, 1, 1, 1));
        this.environment = environmentPlus;
        usesEnvPlus = true;
        return this;
    }

    public Environment getEnvironment(){
        return environment;
    }

    public EnvironmentPlus getEnvironmentPlus(){
        if(!usesEnvPlus)
            initEnvPlus();
        return (EnvironmentPlus) environment;
    }

    public SceneManagerPlus skybox(String prefix){
        if(!getEnvironmentPlus().ibl){
            getEnvironmentPlus().ibl();
        }
        Cubemap customSkyboxCubemap = EnvUtil.createCubemapDirectionFormat("skybox");
        SceneSkybox skybox = new SceneSkybox(customSkyboxCubemap);
        setSkyBox(skybox);
        return this;
    }

    public SceneManagerPlus skybox(){
        if(!getEnvironmentPlus().ibl){
            getEnvironmentPlus().ibl();
        }
        SceneSkybox skybox = new SceneSkybox(environmentPlus.environmentCubemap);
        setSkyBox(skybox);
        return this;
    }

    public Camera getCamera(){
        return camera;
    }

    public SceneManagerPlus configCamera(float near, float far, Vector3 pos, Vector3 lookAt){
        Camera camera = getCamera();
        camera.near = near;
        camera.far = far;
        camera.position.set(pos);
        camera.lookAt(lookAt);
        camera.update();
        return this;
    }





}

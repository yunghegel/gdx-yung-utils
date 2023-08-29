package org.yunghegel.gdx.utils.setup;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Cubemap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.PointLightsAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.SpotLightsAttribute;
import com.badlogic.gdx.graphics.g3d.environment.PointLight;
import com.badlogic.gdx.graphics.g3d.environment.SpotLight;
import com.badlogic.gdx.utils.Array;
import net.mgsx.gltf.scene3d.attributes.FogAttribute;
import net.mgsx.gltf.scene3d.attributes.PBRCubemapAttribute;
import net.mgsx.gltf.scene3d.attributes.PBRTextureAttribute;
import net.mgsx.gltf.scene3d.lights.DirectionalLightEx;
import net.mgsx.gltf.scene3d.utils.IBLBuilder;

public class EnvironmentPlus extends Environment {

   private Cubemap diffuseCubemap;
   public Cubemap environmentCubemap;
   private Cubemap specularCubemap;
   private Texture brdfLUT;
   private DirectionalLightEx light;

   protected boolean fog = false, dirLight = false, ibl = false;

   public float near=1,far=100,exponent=1;

   public Color col = Color.valueOf("#575657");

    public EnvironmentPlus(){
        super();
        set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1.f));

    }

    public EnvironmentPlus(boolean useDefaultConfig){
        super();
        if(useDefaultConfig) initDefaultConfig();
    }


   public void initDefaultConfig(){
      fog(col, 0, 100,1).ibl().lighting(col, -1, -3, 1);
   }

   public EnvironmentPlus fog(Color col, float near, float far,float exponent){
        this.near = near;
        this.far = far;
        this.exponent = exponent;

      set(new ColorAttribute(ColorAttribute.Fog, col));
      set(FogAttribute.createFog(near, far, exponent));
      fog = true;
      return this;
   }

   public EnvironmentPlus lighting(Color col, float dirx, float diry, float dirz){
      light = new DirectionalLightEx();
      light.direction.set(dirx,diry,dirz).nor();
      light.color.set(col);
      add(light);
      dirLight = true;
      return this;
   }

    public EnvironmentPlus ibl(){
      if(!dirLight) light = new DirectionalLightEx();

      IBLBuilder iblBuilder = IBLBuilder.createOutdoor(light);
      environmentCubemap = iblBuilder.buildEnvMap(1024);
      diffuseCubemap = iblBuilder.buildIrradianceMap(256);
      specularCubemap = iblBuilder.buildRadianceMap(10);
      iblBuilder.dispose();

      brdfLUT = new Texture(Gdx.files.classpath("net/mgsx/gltf/shaders/brdfLUT.png"));
      set(new PBRTextureAttribute(PBRTextureAttribute.BRDFLUTTexture, brdfLUT));
      set(PBRCubemapAttribute.createSpecularEnv(specularCubemap));
      set(PBRCubemapAttribute.createDiffuseEnv(diffuseCubemap));
       ibl = true;
       return this;
    }

    public EnvironmentPlus setFogColor(Color col){
        set(new ColorAttribute(ColorAttribute.Fog, col));
        return this;
    }

    public EnvironmentPlus setFogEquation(){
        FogAttribute fa = get(FogAttribute.class, FogAttribute.FogEquation);
        fa.set(near,far, exponent);
        return this;
    }





    public Array<PointLight> getPointLights(){
       PointLightsAttribute pla = get(PointLightsAttribute.class, PointLightsAttribute.Type);
         if(pla == null) return null;

         Array<PointLight> lights = new Array<PointLight>();
         for(PointLight pl: pla.lights){
             lights.add(pl);
         }
            return lights;
    }

    public Array<SpotLight> getSpotLights(){
       SpotLightsAttribute sla = get(SpotLightsAttribute.class, SpotLightsAttribute.Type);
         if(sla == null) return null;

         Array<SpotLight> lights = new Array<SpotLight>();
         for(SpotLight sl: sla.lights){
             lights.add(sl);
         }
            return lights;
    }

    public boolean hasLight(PointLight light){
       PointLightsAttribute pla = get(PointLightsAttribute.class, PointLightsAttribute.Type);
         if(pla == null) return false;

         for(PointLight pl: pla.lights){
             if(pl.equals(light)) return true;
         }
            return false;
    }

    public boolean hasLight(SpotLight light){
       SpotLightsAttribute sla = get(SpotLightsAttribute.class, SpotLightsAttribute.Type);
         if(sla == null) return false;

         for(SpotLight sl: sla.lights){
             if(sl.equals(light)) return true;
         }
            return false;
    }


   @Override
   public Environment add(PointLight light) {
      if (hasLight(light)) return super.add(light);
      return this;
   }

   @Override
   public Environment add(SpotLight light) {
        if (hasLight(light)) return super.add(light);
      return this;
   }
}

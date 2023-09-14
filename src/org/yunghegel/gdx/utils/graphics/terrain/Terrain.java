package org.yunghegel.gdx.utils.graphics.terrain;

import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import net.mgsx.gltf.scene3d.attributes.PBRTextureAttribute;

public class Terrain {

    protected int size, width;

    protected float magnitude;

    protected ModelInstance modelInstance;

    protected HeightField field;

    protected TextureAttribute textureAttribute;
    protected PBRTextureAttribute pbrTextureAttribute;

    protected Texture texture;

    protected Material mat;



    protected float uvScale = 80;

    public Terrain(int size, float magnitude, Pixmap pixmap, boolean isStatic, boolean isSmooth, Texture texture,int resolution) {
        this.size = size;
        this.magnitude = magnitude;
        modelInstance = init(pixmap, VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal | VertexAttributes.Usage.TextureCoordinates, isStatic, isSmooth, texture,resolution);
    }

    public ModelInstance init(Pixmap pixmap, int attributes, boolean isStatic, boolean isSmooth, Texture texture,int resolution){
        createHeightmap(pixmap, attributes, isStatic, isSmooth,resolution);
        setTexture(texture);
        setupMaterial(textureAttribute);
        modelInstance = createModelInstance();
        modelInstance.materials.set(0, mat);
        return modelInstance;
    }

    protected void createHeightmap(Pixmap pixmap,int attributes, boolean isStatic, boolean isSmooth, int resolution){
        field = new HeightField(isStatic, pixmap, isSmooth, attributes, resolution);
        this.width = pixmap.getWidth();
        pixmap.dispose();

        field.corner00.set(0, 0, 0);
        field.corner10.set(size, 0, 0);
        field.corner01.set(0, 0, size);
        field.corner11.set(size, 0, size);
        field.magnitude.set(0f, magnitude, 0f);
        field.update();
    }



    public void setTexture(Texture texture){
        this.texture = texture;
        this.texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        this.texture.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);

        pbrTextureAttribute  = PBRTextureAttribute.createBaseColorTexture(this.texture);
        pbrTextureAttribute.scaleU = uvScale;
        pbrTextureAttribute.scaleV = uvScale;

        textureAttribute = new TextureAttribute(TextureAttribute.Emissive, this.texture);
        textureAttribute.scaleU = uvScale;
        textureAttribute.scaleV = uvScale;


    }

    protected void setupMaterial(TextureAttribute ta){
        mat = new Material();
//        mat.set(textureAttribute);
//        mat.set(new TextureAttribute(TextureAttribute.Diffuse, texture));
//        mat.set(new TextureAttribute(TextureAttribute.Normal, texture));
//        mat.set(new TextureAttribute(TextureAttribute.Specular, texture));
//        mat.set(new TextureAttribute(TextureAttribute.Ambient, texture));
//        mat.set(new TextureAttribute(TextureAttribute.Emissive, texture));
//        mat.set(new TextureAttribute(TextureAttribute.Reflection, texture));
        mat.set(new PBRTextureAttribute(PBRTextureAttribute.BaseColorTexture, texture));
        mat.set(pbrTextureAttribute);
    }

    public void setUVScale(float uvScale){
        this.uvScale = uvScale;
        if(textureAttribute != null){
            textureAttribute.scaleU = uvScale;
            textureAttribute.scaleV = uvScale;
        }
    }

    protected ModelInstance createModelInstance(){
        ModelBuilder mb = new ModelBuilder();
        mb.begin();
        mb.part("terrain", field.mesh, GL20.GL_TRIANGLES, mat);
        return modelInstance = new ModelInstance(mb.end());
    }

    public ModelInstance getModelInstance(){
        return modelInstance;
    }


}

package org.yunghegel.gdx.utils.graphics.model;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.Material;
import net.mgsx.gltf.scene3d.attributes.PBRColorAttribute;

public class BuilderUtils {

    public static Material buildMaterial(Color color){
        Material mat = new Material();
        mat.set(PBRColorAttribute.createDiffuse(color));
        mat.set(PBRColorAttribute.createSpecular(color));
        mat.set(PBRColorAttribute.createAmbient(color));
        mat.set(PBRColorAttribute.createEmissive(color));
        mat.set(PBRColorAttribute.createBaseColorFactor(color));
        return mat;
    }

    public static long buildAttributes(boolean normal, boolean color, boolean texCoords){
        long attributes = 0;
        if(normal) attributes |= VertexAttributes.Usage.Normal;
        if(color) attributes |= VertexAttributes.Usage.ColorUnpacked;
        if(texCoords) attributes |= VertexAttributes.Usage.TextureCoordinates;
        return attributes;
    }

    public static long buildAttributes(boolean normal, boolean color, boolean texCoords, boolean tangent, boolean binormal){
        long attributes = 0;
        if(normal) attributes |= VertexAttributes.Usage.Normal;
        if(color) attributes |= VertexAttributes.Usage.ColorUnpacked;
        if(texCoords) attributes |= VertexAttributes.Usage.TextureCoordinates;
        if(tangent) attributes |= VertexAttributes.Usage.Tangent;
        if(binormal) attributes |= VertexAttributes.Usage.BiNormal;
        return attributes;
    }

    public static Color getRandomColor(){
        Color color = new Color();
        color.fromHsv((float)Math.random()*360, 1, 1);
        return color;
    }
}

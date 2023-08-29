package org.yunghegel.gdx.utils.graphics.model.gltf;

public enum GLTFSamples {

    TWOCYLINDERENGINE("2CylinderEngine.gltf"),
    ABEAUTIFULGAME("ABeautifulGame.gltf"),
    ALPHABLENDMODETEST("AlphaBlendModeTest.gltf"),
    ANIMATEDCUBE("AnimatedCube.gltf"),
    ANIMATEDMORPHCUBE("AnimatedMorphCube.gltf"),
    ANIMATEDMORPHSPHERE("AnimatedMorphSphere.gltf"),
    ANIMATEDTRIANGLE("AnimatedTriangle.gltf"),
    ANTIQUECAMERA("AntiqueCamera.gltf"),
    ATTENUATIONTEST("AttenuationTest.gltf"),
    AVOCADO("Avocado.gltf"),
    BARRAMUNDIFISH("BarramundiFish.gltf"),
    BOOMBOX("BoomBox.gltf"),
    BOOMBOXWITHAXES("BoomBoxWithAxes.gltf"),
    BOX("Box.gltf"),
    BOXWITHSPACES("Box With Spaces.gltf"),
    BOXANIMATED("BoxAnimated.gltf"),
    BOXINTERLEAVED("BoxInterleaved.gltf"),
    BOXTEXTURED("BoxTextured.gltf"),
    BOXTEXTUREDNONPOWEROFTWO("BoxTexturedNonPowerOfTwo.gltf"),
    BOXVERTEXCOLORS("BoxVertexColors.gltf"),
    BRAINSTEM("BrainStem.gltf"),
    BUGGY("Buggy.gltf"),
    CAMERAS("Cameras.gltf"),
    CESIUMMAN("CesiumMan.gltf"),
    CESIUMMILKTRUCK("CesiumMilkTruck.gltf"),
    CLEARCOATCARPAINT("ClearCoatCarPaint.gltf"),
    CLEARCOATTEST("ClearCoatTest.gltf"),
    CLEARCOATWICKER("ClearcoatWicker.gltf"),
    CORSET("Corset.gltf"),
    CUBE("Cube.gltf"),
    DAMAGEDHELMET("DamagedHelmet.gltf"),

    DRAGONATTENUATION("DragonAttenuation.gltf"),
    DUCK("Duck.gltf"),
    EMISSIVESTRENGTHTEST("EmissiveStrengthTest.gltf"),
    ENVIRONMENTTEST("EnvironmentTest.gltf"),
    FLIGHTHELMET("FlightHelmet.gltf"),
    FOX("Fox.gltf"),
    GEARBOXASSY("GearboxAssy.gltf"),
    GLAMVELVETSOFA("GlamVelvetSofa.gltf"),

    INTERPOLATIONTEST("InterpolationTest.gltf"),
    IRIDESCENCEDIELECTRICSPHERES("IridescenceDielectricSpheres.gltf"),
    IRIDESCENCELAMP("IridescenceLamp.gltf"),
    IRIDESCENCEMETALLICSPHERES("IridescenceMetallicSpheres.gltf"),
    IRIDESCENCESUZANNE("IridescenceSuzanne.gltf"),
    IRIDESCENTDISHWITHOLIVES("IridescentDishWithOlives.gltf"),
    LANTERN("Lantern.gltf"),
    LIGHTSPUNCTUALLAMP("LightsPunctualLamp.gltf"),
    MATERIALSVARIANTSSHOE("MaterialsVariantsShoe.gltf"),
    MESHPRIMITIVEMODES("MeshPrimitiveModes.gltf"),
    METALROUGHSPHERES("MetalRoughSpheres.gltf"),
    METALROUGHSPHERESNOTEXTURES("MetalRoughSpheresNoTextures.gltf"),

    MORPHPRIMITIVESTEST("MorphPrimitivesTest.gltf"),
    MORPHSTRESSTEST("MorphStressTest.gltf"),
    MOSQUITOINAMBER("MosquitoInAmber.gltf"),
    MULTIPLESCENES("MultipleScenes.gltf"),
    MULTIUVTEST("MultiUVTest.gltf"),
    NEGATIVESCALETEST("NegativeScaleTest.gltf"),
    NORMALTANGENTMIRRORTEST("NormalTangentMirrorTest.gltf"),
    NORMALTANGENTTEST("NormalTangentTest.gltf"),
    ORIENTATIONTEST("OrientationTest.gltf"),

    RECIPROCATINGSAW("ReciprocatingSaw.gltf"),
    RECURSIVESKELETONS("RecursiveSkeletons.gltf"),
    RIGGEDFIGURE("RiggedFigure.gltf"),
    RIGGEDSIMPLE("RiggedSimple.gltf"),
    SCIFIHELMET("SciFiHelmet.gltf"),
    SHEENCHAIR("SheenChair.gltf"),
    SHEENCLOTH("SheenCloth.gltf"),
    SIMPLEINSTANCING("SimpleInstancing.gltf"),
    SIMPLEMESHES("SimpleMeshes.gltf"),
    SIMPLEMORPH("SimpleMorph.gltf"),
    SIMPLESKIN("SimpleSkin.gltf"),
    SIMPLESPARSEACCESSOR("SimpleSparseAccessor.gltf"),
    SPECGLOSSVSMETALROUGH("SpecGlossVsMetalRough.gltf"),
    SPECULARTEST("SpecularTest.gltf"),
    SPONZA("Sponza.gltf"),
    STAINEDGLASSLAMP("StainedGlassLamp.gltf"),
    SUZANNE("Suzanne.gltf"),
    TEXTURECOORDINATETEST("TextureCoordinateTest.gltf"),
    TEXTUREENCODINGTEST("TextureEncodingTest.gltf"),
    TEXTURELINEARINTERPOLATIONTEST("TextureLinearInterpolationTest.gltf"),
    TEXTURESETTINGSTEST("TextureSettingsTest.gltf"),
    TEXTURETRANSFORMMULTITEST("TextureTransformMultiTest.gltf"),
    TEXTURETRANSFORMTEST("TextureTransformTest.gltf"),
    TOYCAR("ToyCar.gltf"),
    TRANSMISSIONROUGHNESSTEST("TransmissionRoughnessTest.gltf"),
    TRANSMISSIONTEST("TransmissionTest.gltf"),
    TRIANGLE("Triangle.gltf"),
    TRIANGLEWITHOUTINDICES("TriangleWithoutIndices.gltf"),
    TWOSIDEDPLANE("TwoSidedPlane.gltf"),
    UNICODETEST("UnicodeTest.gltf"),
    UNLITTEST("UnlitTest.gltf"),
    VC("VC.gltf"),
    VERTEXCOLORTEST("VertexColorTest.gltf"),
    WATERBOTTLE("WaterBottle.gltf");

    private final String path;

    GLTFSamples(String path) {
        this.path = path;
    }

    public String path() {
        String withtouExtension = path.substring(0, path.lastIndexOf('.'));
        return withtouExtension + "/" + path;
    }

    public String path(String subPath) {
        if(subPath.endsWith("/"))
            return subPath + path();
        else
            return subPath + "/" + path();
    }

}
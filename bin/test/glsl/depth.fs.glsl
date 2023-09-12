

precision mediump float;



varying vec2 v_texCoords0;
uniform sampler2D u_diffuseTexture;
uniform float u_alphaTest;
varying float v_depth;



vec4 pack_depth(const in float depth){
    const vec4 bit_shift =
        vec4(256.0*256.0*256.0, 256.0*256.0, 256.0, 1.0);
    const vec4 bit_mask  =
        vec4(0.0, 1.0/256.0, 1.0/256.0, 1.0/256.0);
    vec4 res = fract(depth * bit_shift);
    res -= res.xxyz * bit_mask;
    return res;
}

vec4 convert_linear_depth_to_logarithmic_depth(const in float depth){
    const float LOG2 = 1.442695;
    float z = depth * 2.0 - 1.0;
    return vec4(log2(max(0.00000001, 1.0 + z)) * LOG2);
}

void main() {
	#ifdef blendedTextureFlag
		if (texture2D(u_diffuseTexture, v_texCoords0).a < u_alphaTest)
			discard;
	#endif // blendedTextureFlag


    vec4 depth = pack_depth(v_depth);

    gl_FragColor = convert_linear_depth_to_logarithmic_depth(v_depth);






}
#ifdef GL_ES
#define LOWP lowp
#define MED mediump
precision lowp float;
#else
#define LOWP
#define MED
#endif

uniform sampler2D u_texture;
uniform mat4 u_projTrans;

varying MED vec2 v_texCoords0;
varying MED vec2 v_texCoords1;
varying MED vec2 v_texCoords2;
varying MED vec2 v_texCoords3;
varying MED vec2 v_texCoords4;



float unpack_depth(const in vec4 rgba_depth){
    const vec4 bit_shift = vec4(1.0, 1.0 / 255.0, 1.0 / 65025.0, 1.0 / 160581375.0);

    float depth = dot(rgba_depth, bit_shift);
    return depth;
}

void main(){
    float depth =
    abs(unpack_depth(texture2D(u_texture, v_texCoords0))
    + unpack_depth(texture2D(u_texture, v_texCoords1))
    - unpack_depth(4.0 * texture2D(u_texture, v_texCoords2))
    + unpack_depth(texture2D(u_texture, v_texCoords3))
    + unpack_depth(texture2D(u_texture, v_texCoords4)));

    if(depth > 0.004){
        gl_FragColor = vec4(1,1,1,1.0);
    }
    else{
        gl_FragColor = vec4(0,0,0,0);
    }
}
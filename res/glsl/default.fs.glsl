
varying float visibility;
const vec4 u_skycolor = vec4(0.2, 0.2, 0.2,1.0);

varying vec2 v_texcoord;
varying float v_fogDepth;
varying vec4 v_color;
uniform sampler2D u_texture;
varying float v_opacity;
const float near =1.0;
const float far = 50.0;

void main(void){

    vec4 color = texture2D(u_texture, v_texcoord);
    float fogAmount = smoothstep(near, far, v_fogDepth);
    gl_FragColor = mix(v_color, u_skycolor, fogAmount);
    gl_FragColor.a = v_opacity;
}
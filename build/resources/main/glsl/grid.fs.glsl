
uniform sampler2D u_texture;


varying float v_fogDepth;
varying vec4 v_color;
varying float v_opacity;
varying vec4 v_skyColor;
varying float v_fogFar;
varying float v_fogNear;
varying vec2 v_texcoord;
varying float v_fog;
varying vec3 v_dist;


varying vec4 v_position;
varying vec4 v_cameraPosition;
const vec4 u_skycolor = vec4(0.34, 0.34, 0.34,1.0);
const float near =1.0;
const float far = 50.0;

void main(void){

    float distance = distance(v_position, v_cameraPosition);

    vec3 col = normalize(v_dist);

    float fogAmount = smoothstep(near, far, v_fogDepth);


    float num;
    if (distance>=far) num= 1.0;
    if (distance<=near) num = 0.0;

    float alpha = 1.0 - (far - distance) / (near - far);

    gl_FragColor = mix(v_color, u_skycolor, fogAmount);
    gl_FragColor.a = v_opacity;
}


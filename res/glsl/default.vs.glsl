

attribute vec4 a_position;
attribute vec2 a_texCoord0;
attribute vec4 a_color;

uniform mat4 u_viewTrans;
uniform mat4 u_projTrans;
uniform mat4 u_worldTrans;
uniform float u_opacity;

varying float v_fogDepth;
varying vec2 v_texcoord;
varying vec4 v_color;
varying float v_opacity;

varying float visibility;
const vec3 u_skycolor = vec3(0.5, 0.6, 0.7);

const float density = 0.01;
const float gradient = 1.5;

void main(void){

    vec4 worldPos = u_viewTrans * a_position;

    vec4 posRelativeToCam = u_worldTrans * a_position;

    float distance = length(posRelativeToCam.xyz);

    visibility = exp(-pow((distance * density), gradient));
    vec3 tmp = vec3( dot(worldPos.xyz,vec3(127.1,311.7, 74.7)),
    dot(worldPos.xyz,vec3(269.5,183.3,246.1)),
    dot(worldPos.xyz,vec3(113.5,271.9,124.6)));

    vec3 hash = fract(sin(tmp)*43758.5453123);

vec3 noise = vec3(hash.x - 0.5, hash.y - 0.5, hash.z - 0.5);

    vec3 color = u_skycolor + noise * 0.1;
    color *= visibility;

    v_color = a_color;

    v_texcoord = a_texCoord0;
    v_fogDepth = -(u_viewTrans * a_position).z;
    v_opacity = u_opacity;


    gl_Position = u_projTrans * worldPos;

    visibility = exp(-pow((distance * density), gradient));
    visibility = clamp(visibility, 0.1, 1.0);
}


attribute vec3 a_position;
attribute vec4 a_color;
attribute vec2 a_texCoord0;

uniform mat4 u_viewTrans;
uniform mat4 u_projTrans;
uniform mat4 u_worldTrans;
uniform float u_opacity;
uniform float u_fogNear;
uniform float u_fogFar;
uniform vec4 u_skyColor;
uniform mat4 u_projViewWorldTrans;
uniform mat4 u_projViewTrans;
uniform vec4 u_cameraPosition;
varying float v_fogDepth;
varying vec4 v_color;
varying float v_opacity;
varying vec4 v_skyColor;
varying vec2 v_texcoord;

varying float v_fogNear;
varying float v_fogFar;
varying float v_fog;
varying vec3 v_dist;
varying vec4 v_position;
varying vec4 v_cameraPosition;



void main(void){
    vec4 pos = u_projViewWorldTrans * vec4(a_position, 1.0);

    vec4 worldPos = u_viewTrans * vec4(a_position,1.0);

    vec3 flen = u_cameraPosition.xyz - pos.xyz;
    float fog = dot(flen, flen) * u_cameraPosition.w;

    v_dist = flen;
    v_fog = min(fog, 1.0);
    v_cameraPosition = worldPos;
    v_position = worldPos;
    v_texcoord = a_texCoord0;
    v_fogDepth = -(worldPos).z;
    v_opacity = u_opacity;
    v_color = a_color;
    v_skyColor = u_skyColor;
    v_fogNear = u_fogNear;
    v_fogFar = u_fogFar;



    gl_Position = pos;


}
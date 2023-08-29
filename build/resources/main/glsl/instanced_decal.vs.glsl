attribute vec4 a_position;
attribute vec2 a_texCoord0;
attribute vec3 i_position;

uniform mat4 u_projTrans;
uniform mat4 u_viewTrans;
varying vec2 v_texcoord;

void main()
{
    gl_Position = u_projTrans * u_viewTrans * vec4(a_position.xyz + i_position, 1.0);
    v_texcoord= a_texCoord0;
}

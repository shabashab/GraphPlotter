#version 120

attribute vec2 a_position;
//uniform mat4 u_projectionViewMatrix;
uniform vec2 u_offset;
uniform vec2 u_scale;

void main()
{
//    gl_Position = u_projectionViewMatrix * a_position;
//    vec2 normalized_position = a_position / u_screenSize;

    gl_Position = vec4((a_position.xy + u_offset) / u_scale, 0f, 1f);
}

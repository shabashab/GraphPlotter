#version 120

attribute vec2 a_position;
uniform vec2 u_offset;
uniform vec2 u_scale;

void main()
{
    vec2 position = (a_position.xy + u_offset) / u_scale;

    gl_Position = vec4(position.x, position.y * -1, 0f, 1f);
}

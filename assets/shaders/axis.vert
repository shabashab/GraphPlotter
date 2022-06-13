#version 120

attribute vec2 a_position;
uniform vec2 u_offset;
uniform vec2 u_scale;

void main()
{
    vec2 position = vec2(a_position.x + (u_offset.x / u_scale.x * ceil(abs(a_position.y))), a_position.y + (u_offset.y / u_scale.y * ceil(abs(a_position.x))));

    gl_Position = vec4(position.x, position.y * -1, 0f, 1f);
}

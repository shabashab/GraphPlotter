#version 120

attribute vec2 a_position;
uniform vec2 u_offset;
uniform vec2 u_scale;

uniform vec2 u_screenSize;
uniform vec2 u_size;
uniform vec2 u_position;

vec4 transformPositionVector(vec2 positionVector) {
    vec2 normalizedPositionOffset = u_position / u_screenSize;
    vec2 normalizedSize = u_size / u_screenSize;

    vec2 pos2d = (vec2(positionVector.x, positionVector.y * -1) * normalizedSize) + normalizedPositionOffset;
    return vec4(pos2d.xy, 0f, 1f);
}

void main()
{
    vec2 position = vec2(a_position.x + (u_offset.x / u_scale.x * ceil(abs(a_position.y))), a_position.y + (u_offset.y / u_scale.y * ceil(abs(a_position.x))));
    gl_Position = transformPositionVector(position);
}

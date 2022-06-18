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
    vec2 position = (a_position.xy + u_offset) / u_scale;

    gl_Position = transformPositionVector(position);
}

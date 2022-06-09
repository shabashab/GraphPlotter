#version 120

attribute vec4 a_position;
uniform vec2 u_offset;
uniform vec2 u_scale;

void main()
{
    gl_Position = vec4(a_position.x + (u_offset.x / u_scale.x * ceil(abs(a_position.y))), a_position.y + (u_offset.y / u_scale.y * ceil(abs(a_position.x))), a_position.zw);

    //Same as:
//    if(a_position.y == 0f) {
//        gl_Position = vec4(a_position.x, a_position.y + (u_offset.y / u_scale.y), a_position.zw);
//        return;
//    }
//
//    if(a_position.x == 0f) {
//        gl_Position = vec4(a_position.x + (u_offset.x / u_scale.x), a_position.yzw);
//        return;
//    }
//    gl_Position = a_position;
}

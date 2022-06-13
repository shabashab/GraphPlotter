#version 120

attribute float a_side;
attribute float a_component;

uniform vec2 u_offset;
uniform vec2 u_scale;
uniform vec2 u_size;
uniform vec2 u_arrow_size;

void main()
{
    vec2 normalized_offset = (u_offset * (u_size / u_scale)) / u_size;

    vec2 output_position;

    if(a_side == 1f) {
        vec2 normalized_arrow_size = u_arrow_size / u_size;
        if(a_component == 0f) {
            output_position = vec2(1f - normalized_arrow_size.x, normalized_arrow_size.y / 2f);
        }

        if(a_component == 1f) {
            output_position = vec2(1, 0);
        }

        if(a_component == 2f) {
            output_position = vec2(1f - normalized_arrow_size.x, normalized_arrow_size.y / -2f);
        }

        output_position.y += normalized_offset.y;
    }

    if(a_side == 2f) {
        vec2 normalized_arrow_size = vec2(u_arrow_size.x / u_size.y, u_arrow_size.y / u_size.x);
        if(a_component == 0f) {
            output_position = vec2(normalized_arrow_size.y / -2f, 1f - normalized_arrow_size.x);
        }

        if(a_component == 1f) {
            output_position = vec2(0, 1);
        }

        if(a_component == 2f) {
            output_position = vec2(normalized_arrow_size.y / 2f, 1f - normalized_arrow_size.x);
        }

        output_position.x += normalized_offset.x;
    }

    gl_Position = vec4(output_position.x, output_position.y * -1, 0f, 1f);
}

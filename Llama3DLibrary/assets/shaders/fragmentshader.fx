// ======== Constants ========
LL.import("c_fragment_const.fx");
// ======== Float Precision ========
LL.import("c_fragment_precision.fx");
// ======== Varyings ========
LL.import("c_fragment_varying.fx");
// ======== Uniforms ========
LL.import("c_fragment_uniform.fx");
// ======== Fog Effect ========
LL.import("c_fragment_fog_p1.fx");

void main() {

	// ======== Texture Blendings ========
	LL.import("c_fragment_texture_p2.fx");
	// ======== Autofading Of Surfaces ========
	LL.import("c_fragment_autofade_p2.fx");
	// ======== Diffuse-Specular Lighting ========
	LL.import("c_fragment_specular_p2.fx");
	// ======== Calculating Fog Effect ========
	LL.import("c_fragment_fog_p2.fx");
	// ======== Calculating Fragment Color ========
	LL.import("c_fragment_final_p2.fx");

}
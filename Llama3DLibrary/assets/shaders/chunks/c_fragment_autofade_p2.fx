float autofadeAlpha = 1.0;
if (mat.autofade == true && mat.alpha > 0.0) {
	if (depth > mat.aFar) {
		autofadeAlpha = 0.0;
	} else if (depth >= mat.aNear) {
		autofadeAlpha = 1.0 - (depth - mat.aFar) / (mat.aFar - mat.aNear);
	} else if (depth < mat.aNear) {
		autofadeAlpha = 1.0;
	}
} else {
	autofadeAlpha = 1.0;
}
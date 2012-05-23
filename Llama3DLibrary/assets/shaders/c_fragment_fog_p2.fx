if (mat.fog == true && cameraFog == true) {
	// ======== Object Farer Away ========
	if (depth > fogFar) {
		fogDensity = 1.0;
	} else if (depth >= fogNear) {
		fogDensity = (depth - fogNear) / (fogFar - fogNear);
	} else if (depth < fogNear) {
		fogDensity = 0.0;
	}
	
}
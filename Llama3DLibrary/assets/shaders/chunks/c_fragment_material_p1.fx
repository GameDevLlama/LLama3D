uniform struct material {
	vec4 diff;
	vec4 spec;
	float alpha;
	float shine;
	float elum;
	bool fog;

	bool autofade;
	float aNear;
	float aFar;
} mat;
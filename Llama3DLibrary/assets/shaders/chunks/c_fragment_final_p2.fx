//final fragmentcolor
gl_FragColor = autofadeAlpha * mix(colortex * (ambient + mat.diff * diff) + spec, UNIFORM_FOG_COLOR, fogDensity+mat.elum);
package com.llama3d.object.mesh.geometry;

import com.llama3d.object.mesh.Surface;
import com.llama3d.object.mesh.Vertex;

public class GeometryTwistedTorus extends Geometry {
	public GeometryTwistedTorus() {
		this(1f, 2f, 20, 20, 1);
	}

	public GeometryTwistedTorus(float innerRadius, float outerRadius) {
		this(innerRadius, outerRadius, 20, 20, 1);
	}

	public GeometryTwistedTorus(float innerRadius, float outerRadius, int rings, int segments, int twists) {
		Surface surface = new Surface();
		this.surfaces.add(surface);
		Vertex vertex[] = new Vertex[4];
		float position[] = new float[8];
		float radius[] = new float[2];
		float step[] = new float[3];
		step[0] = 360.0f / rings;
		step[1] = 360.0f / segments;
		step[2] = (float) twists;
		radius[0] = (outerRadius + innerRadius) * 0.5f;// radius to ringcenter
		radius[1] = (outerRadius - innerRadius) * 0.5f;// radius of ring itself
		for (float segment1 = 0; segment1 <= 360.0f; segment1 += step[0]) {
			for (float segment2 = 0; segment2 < 360.0; segment2 += step[1]) {
				position[0] = radius[0] + radius[1] * (float) Math.sin(Math.toRadians(segment1 - 90)); // r0
				position[1] = radius[0] + radius[1] * (float) Math.sin(Math.toRadians(segment1 + step[0] - 90)); // r1
				position[2] = (float) Math.cos(Math.toRadians(segment2 + segment2 * step[2])); // x0
				position[3] = (float) Math.cos(Math.toRadians(segment2 + step[1] + (segment2 + step[1]) * step[2])); // x1
				position[4] = (float) Math.sin(Math.toRadians(segment2 + segment2 * step[2])); // z0
				position[5] = (float) Math.sin(Math.toRadians(segment2 + step[1] + (segment2 + step[1]) * step[2])); // z1
				position[6] = radius[1] * (float) Math.cos(Math.toRadians(segment1 - 90)); // y0
				position[7] = radius[1] * (float) Math.cos(Math.toRadians(segment1 + step[0] - 90)); // y1
				// add vertices
				vertex[0] = new Vertex();
				vertex[1] = new Vertex();
				vertex[2] = new Vertex();
				vertex[3] = new Vertex();
				// add positions
				vertex[0].position(position[2] * position[0], position[6], position[4] * position[0]);
				vertex[1].position(position[3] * position[0], position[6], position[5] * position[0]);
				vertex[2].position(position[2] * position[1], position[7], position[4] * position[1]);
				vertex[3].position(position[3] * position[1], position[7], position[5] * position[1]);
				// add normals
				vertex[0].normal(vertex[0].data[0] - position[2] * radius[0], vertex[0].data[1], vertex[0].data[2] - position[4] * radius[0]);
				vertex[1].normal(vertex[1].data[0] - position[3] * radius[0], vertex[1].data[1], vertex[1].data[2] - position[5] * radius[0]);
				vertex[2].normal(vertex[2].data[0] - position[2] * radius[0], vertex[2].data[1], vertex[2].data[2] - position[4] * radius[0]);
				vertex[3].normal(vertex[3].data[0] - position[3] * radius[0], vertex[3].data[1], vertex[3].data[2] - position[5] * radius[0]);
				// add uv-sets
				vertex[0].uvset(segment2 / 360f, segment1 / 360f);
				vertex[1].uvset((segment2 + step[1]) / 360f, segment1 / 360f);
				vertex[2].uvset(segment2 / 360f, (segment1 + step[0]) / 360f);
				vertex[3].uvset((segment2 + step[1]) / 360f, (segment1 + step[0]) / 360f);
				// add triangles
				surface.addTriangle(vertex[2], vertex[0], vertex[1]);
				surface.addTriangle(vertex[2], vertex[1], vertex[3]);
			}
		}
	}
}

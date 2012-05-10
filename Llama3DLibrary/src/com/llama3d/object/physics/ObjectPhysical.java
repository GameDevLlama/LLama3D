package com.llama3d.object.physics;

import javax.vecmath.Vector3f;

import android.opengl.Matrix;

import com.bulletphysics.collision.shapes.BoxShape;
import com.bulletphysics.collision.shapes.CollisionShape;
import com.bulletphysics.collision.shapes.ConeShape;
import com.bulletphysics.collision.shapes.ConvexHullShape;
import com.bulletphysics.collision.shapes.SphereShape;
import com.bulletphysics.dynamics.RigidBody;
import com.bulletphysics.dynamics.RigidBodyConstructionInfo;
import com.bulletphysics.linearmath.MotionState;
import com.bulletphysics.linearmath.Transform;
import com.bulletphysics.util.ObjectArrayList;
import com.llama3d.object.Object3D;
import com.llama3d.object.ObjectTransformable;
import com.llama3d.object.mesh.Mesh;
import com.llama3d.scene.SceneCache;

public class ObjectPhysical {

    // jbullet
    protected RigidBody dynamicBody = null;
    protected MotionState motionState = null;
    protected RigidBodyConstructionInfo dynamicAttributes = null;

    // collisions
    public void collisionBox(float mass) {
        this.collisionBox(1f, 1f, 1f, mass);
    }

    public void collisionBox(float width, float height, float depth, float mass) {
        CollisionShape shape = new BoxShape(new Vector3f(width, height, depth));
        this.collision(shape, mass);
    }

    public void collisionCone(float mass) {
        this.collisionCone(mass, 1, 2);
    }

    public void collisionCone(float mass, float radius, float height) {
        CollisionShape shape = new ConeShape(radius, height);
        this.collision(shape, mass);
    }

    public void collisionSphere(float mass) {
        this.collisionSphere(1f, mass);
    }

    public void collisionSphere(float radius, float mass) {
        CollisionShape shape = new SphereShape(radius);
        this.collision(shape, mass);
    }

    public void collisionMesh(float mass) {
        this.collisionMesh(null, mass);
    }

    public void collisionMesh(Mesh mesh, float mass) {
        if (mesh == null) {
            if (this instanceof Mesh) {
                mesh = (Mesh) this;
                // if (mesh.geometry.buffered != true) {
                // return;
                // }
            } else {
                return;
            }
        } else {
            mesh.hide();
        }
        ObjectArrayList<Vector3f> vertices = new ObjectArrayList<Vector3f>();
        // for (Surface surface : mesh.geometry.surfaces) {// read the mesh data
        // for (int i = 0; i < surface.vertexBuffer.capacity(); i += 3) {
        // vertices.add(new Vector3f(surface.vertexBuffer.get(i),
        // surface.vertexBuffer.get(i + 1), surface.vertexBuffer.get(i + 2)));
        // }
        // }
        CollisionShape shape = new ConvexHullShape(vertices);

        this.collision(shape, mass);
    }

    public void collision(CollisionShape shape, float mass) {
        Vector3f localInertia = new Vector3f(0, 0, 0);
        shape.calculateLocalInertia(mass, localInertia);
        this.motionState = new MotionBody((Object3D) this);
        this.dynamicAttributes = new RigidBodyConstructionInfo(mass, this.motionState, shape, localInertia);
        this.dynamicBody = new RigidBody(this.dynamicAttributes);
        SceneCache.activeScene.physics.dynamicsWorld.addRigidBody(this.dynamicBody);
    }

    public void reset() {
        this.reset(0, 0, 0);
    }

    public void reset(float posX, float posY, float posZ) {
        ObjectTransformable object = (ObjectTransformable) this;
        Transform transformation = new Transform();
        Matrix.setIdentityM(object.translationMatrix, 0);
        Matrix.translateM(object.translationMatrix, 0, posX, posY, posZ);
        transformation.setFromOpenGLMatrix(object.translationMatrix);
        this.dynamicBody.setAngularVelocity(new Vector3f(0, 0, 0));
        this.dynamicBody.setLinearVelocity(new Vector3f(0, 0, 0));
        this.dynamicBody.setCenterOfMassTransform(transformation);
        this.dynamicBody.setWorldTransform(transformation);

        this.dynamicBody.setInterpolationAngularVelocity(new Vector3f(0, 0, 0));
        this.dynamicBody.setInterpolationLinearVelocity(new Vector3f(0, 0, 0));
        this.dynamicBody.setInterpolationWorldTransform(transformation);

        this.dynamicBody.clearForces();
        this.dynamicBody.activate();
    }

    public void setFriction(float friction) {
        if (this.dynamicBody != null) {
            this.dynamicBody.setFriction(friction);
        }
    }

}

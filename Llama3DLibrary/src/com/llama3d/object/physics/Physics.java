package com.llama3d.object.physics;

import javax.vecmath.Vector3f;

import com.bulletphysics.collision.broadphase.AxisSweep3;
import com.bulletphysics.collision.broadphase.BroadphaseInterface;
import com.bulletphysics.collision.dispatch.CollisionDispatcher;
import com.bulletphysics.collision.dispatch.DefaultCollisionConfiguration;
import com.bulletphysics.dynamics.DiscreteDynamicsWorld;
import com.bulletphysics.dynamics.DynamicsWorld;
import com.bulletphysics.dynamics.constraintsolver.ConstraintSolver;
import com.bulletphysics.dynamics.constraintsolver.SequentialImpulseConstraintSolver;

public class Physics {
    
    public static DynamicsWorld activeWorld = null;
    public DynamicsWorld dynamicsWorld = null;
    
    public Physics(){
        this.dynamicsWorld = createDynamicsWorld();
        this.dynamicsWorld.setGravity(new Vector3f(0f,-9.81f,0f));
        Physics.activeWorld = this.dynamicsWorld;
        //dynamicsWorld.addRigidBody(createBoxBody());
        //dynamicsWorld.addRigidBody(createGroundBody());
    }
    
    private DynamicsWorld createDynamicsWorld() {
        // collision configuration contains default setup for memory, collision setup
        DefaultCollisionConfiguration collisionConfiguration = new DefaultCollisionConfiguration();
        // calculates exact collision given a list possible colliding pairs
        CollisionDispatcher dispatcher = new CollisionDispatcher(collisionConfiguration);

        // the maximum size of the collision world. Make sure objects stay 
        // within these boundaries. Don't make the world AABB size too large, it
        // will harm simulation quality and performance
        Vector3f worldAabbMin = new Vector3f(-200,-200,-200);
        Vector3f worldAabbMax = new Vector3f(+200,+200,+200);
        // maximum number of objects
        final int maxProxies = 64;
        // Broadphase computes an conservative approximate list of colliding pairs
        BroadphaseInterface broadphase = new AxisSweep3(worldAabbMin,worldAabbMax,maxProxies);

        // constraint (joint) solver
        ConstraintSolver solver = new SequentialImpulseConstraintSolver();

        // provides discrete rigid body simulation
        return new DiscreteDynamicsWorld(dispatcher,broadphase,solver,collisionConfiguration);
    }
    public void simulate(){
        this.dynamicsWorld.stepSimulation(0.016f,10,0.016f);
    }
    
}

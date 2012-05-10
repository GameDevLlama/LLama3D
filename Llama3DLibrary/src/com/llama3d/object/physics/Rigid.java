package com.llama3d.object.physics;
import javax.vecmath.Quat4f;
import javax.vecmath.Vector3f;

import com.bulletphysics.collision.shapes.BoxShape;
import com.bulletphysics.collision.shapes.CollisionShape;
import com.bulletphysics.dynamics.RigidBody;
import com.bulletphysics.linearmath.MotionState;
import com.bulletphysics.linearmath.Transform;


public class Rigid {

    //jbullet
    protected RigidBody dynamicBody;
    protected MotionState motionState;
    
    //collisions
    public Rigid(){
        this(0.5f,0.5f,0.5f);
    }
    public Rigid(float width,float height,float depth){
        CollisionShape shape = new BoxShape(new Vector3f(width,height,depth));
        float mass = 1f;
        Vector3f localInertia = new Vector3f(0,0,0);
        shape.calculateLocalInertia(mass,localInertia);
        motionState = new MotionState(){
            @Override
            public Transform getWorldTransform(Transform arg0) {
                // TODO Auto-generated method stub
                return null;
            }
            @Override
            public void setWorldTransform(Transform transformation) {
                Quat4f rotationQuaternion = new Quat4f();
                transformation.getRotation(rotationQuaternion);
                //LLVector3 vector = LLEuler.convertToEuler(rotationQuaternion);
                //rotationQuaternion.  
            }     
        };
    }
}

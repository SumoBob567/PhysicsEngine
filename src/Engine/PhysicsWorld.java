package Engine;

import Engine.PhysicsObjects.PhysicsObject;
import Engine.util.Vector3D;

public class PhysicsWorld {
    private PhysicsObject[] objects;
    private PhysicsObject[] gravityObjects;
    private int objectCount = 0;
    private int gravityObjectCount = 0;
    private final double gravitiationalConstant = 1;

    public PhysicsWorld(int capacity, int gravityCapacity) {
        objects = new PhysicsObject[capacity];
        gravityObjects = new PhysicsObject[gravityCapacity];
    }

    public void addObject(PhysicsObject object) {
        objects[objectCount++] = object;
        if (object.isGravityObject()) {
            gravityObjects[gravityObjectCount++] = object;
        }
    }

    public void step(double dt) {
        applyGravity();
        for (int i = 0; i < objectCount; i++) {
            objects[i].integrate(dt);
        }
    }

    public void applyGravity() {
        for (int i = 0; i < gravityObjectCount; i++) {
            for (int j = 0; j < objectCount; j++) {
                PhysicsObject source = gravityObjects[i];
                PhysicsObject end = objects[j];
                if (source != end) {
                    Vector3D sourcePos = source.getPosition();
                    Vector3D endPos = end.getPosition();
                    Vector3D direction = sourcePos.sub(endPos);
                    Vector3D normalDirection = direction.normalise();

                    double sourceMass = source.getMass();
                    double endMass = end.getMass();
                    double distance = direction.magnitude();

                    if (distance > 1e-6) {
                        double forceScale = gravitiationalConstant * sourceMass * endMass / (distance * distance);
                        Vector3D force = normalDirection.scale(forceScale);

                        end.applyForce(force);
                    }
                }
            }
        }
    }

    public PhysicsObject[] getObjects() {
        return objects;
    }
}

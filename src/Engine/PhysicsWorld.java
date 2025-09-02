package Engine;

import Engine.PhysicsObjects.PhysicsObject;

public class PhysicsWorld {
    private PhysicsObject[] objects;
    private int objectCount = 0;

    public PhysicsWorld(int capacity) {
        objects = new PhysicsObject[capacity];
    }

    public void addObject(PhysicsObject object) {
        objects[objectCount++] = object;
    }

    public void step(double dt) {
        for (int i = 0; i < objectCount; i++) {
            objects[i].integrate(dt);
        }
    }
}

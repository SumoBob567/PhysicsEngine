package Engine;

import Engine.PhysicsObjects.PhysicsObject;
import Engine.PhysicsObjects.Sphere;
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
        detectAndResolveCollisions();
        applyGravity();
        for (int i = 0; i < objectCount; i++) {
            objects[i].integrate(dt);
        }
    }


    public void detectAndResolveCollisions() {
        PhysicsObject[] objects = getObjects();

        for (int i = 0; i < objects.length; i++) {
            if (!(objects[i] instanceof Sphere)) continue;
            Sphere a = (Sphere) objects[i];

            for (int j = i + 1; j < objects.length; j++) {
                if (!(objects[j] instanceof Sphere)) continue;
                Sphere b = (Sphere) objects[j];

                Vector3D delta = b.getPosition().sub(a.getPosition());
                double distance = delta.magnitude();
                double minDistance = a.getRadius() + b.getRadius();

                if (distance < minDistance && distance > 0) {
                    double penetration = minDistance - distance;
                    Vector3D normal = delta.normalise();

                    a.setPosition(a.getPosition().sub(normal.scale(penetration * 0.5)));
                    b.setPosition(b.getPosition().add(normal.scale(penetration * 0.5)));

                    Vector3D relativeVelocity = a.getVelocity().sub(b.getVelocity());
                    double velAlongNormal = relativeVelocity.dot(normal);


                    if (velAlongNormal > 0) continue;

                    double restitution = 1.0;
                    double impulseMagnitude = -(1 + restitution) * velAlongNormal;
                    impulseMagnitude /= (1 / a.getMass() + 1 / b.getMass());

                    Vector3D impulse = normal.scale(impulseMagnitude);
                    a.setVelocity(a.getVelocity().add(impulse.scale(1 / a.getMass())));
                    b.setVelocity(b.getVelocity().sub(impulse.scale(1 / b.getMass())));
                }
            }
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

    public PhysicsObject[] getGravityObjects() {
        return gravityObjects;
    }
}

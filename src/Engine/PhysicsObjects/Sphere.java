package Engine.PhysicsObjects;

import Engine.util.Vector3D;

public class Sphere extends PhysicsObject {
    private final double radius;

    public Sphere(Vector3D position, double mass, double radius) {
        super(position, mass);
        this.radius = radius;
    }

    public double getRadius() {
        return radius;
    }
}

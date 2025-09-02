package Engine;

public class Sphere extends PhysicsObject {
    private final double radius;

    public Sphere(Vector3D position, double mass, double radius) {
        super(position, mass);
        this.radius = radius;
    }

    public Sphere(Vector3D position, double mass) {
        super(position, mass);
        this.radius = 1.0;
    }

    public double getRadius() {
        return radius;
    }
}

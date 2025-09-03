package Engine.PhysicsObjects;

import Engine.util.Vector3D;

import java.awt.*;

public class Sphere extends PhysicsObject {
    private final double radius;

    public Sphere(Vector3D position, double mass, double radius) {
        super(position, mass);
        this.radius = radius;
    }

    public double getRadius() {
        return radius;
    }

    @Override
    public boolean drawCheck(int x, int y, int width, int height) {
        double radius = getRadius();
        double r = 1 * radius;
        Vector3D pos = getPosition();

        int cx = (int) (pos.x + width / 2);
        int cy = (int) (pos.y + height / 2);

        int dx = x - cx;
        int dy = y - cy;

        return (dx * dx + dy * dy <= r * r);
    }


    @Override
    public int getColor() {
        return Color.GREEN.getRGB();
    }

    public double rayIntersect(Vector3D origin, Vector3D dir) {
        Vector3D oc = origin.sub(getPosition());
        double a = dir.dot(dir);
        double b = 2.0 * oc.dot(dir);
        double c = oc.dot(oc) - getRadius() * getRadius();
        double discriminant = b*b - 4*a*c;
        if (discriminant < 0) return -1;
        return (-b - Math.sqrt(discriminant)) / (2*a);
    }
}

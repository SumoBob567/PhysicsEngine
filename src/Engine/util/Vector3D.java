package Engine.util;

public class Vector3D {
    public double x;
    public double y;
    public double z;

    public Vector3D() {
        this.x = 0;
        this.y = 0;
        this.z = 0;
    }

    public Vector3D(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3D(Vector3D v) {
        this.x = v.x;
        this.y = v.y;
        this.z = v.z;
    }
    public Vector3D add(Vector3D v) {
        return new Vector3D(x + v.x, y + v.y, z + v.z);
    }
    public Vector3D sub(Vector3D v) {
        return new Vector3D(x - v.x, y - v.y, z - v.z);
    }
    public double dot(Vector3D v) {
        return x * v.x + y * v.y + z * v.z;
    }
    public Vector3D cross(Vector3D v) {
        return new Vector3D((this.y * v.z) - (this.z * v.y), (this.z * v.x) - (this.x * v.z), (this.x * v.y) - (this.y * v.x));
    }
    public double magnitude() {
        return Math.sqrt(dot(this));
    }
    public Vector3D scale(double scalar) {
        return new Vector3D(x * scalar, y * scalar, z * scalar);
    }
    public Vector3D normalise() {
        double mag = magnitude();
        return new Vector3D(x / mag, y / mag, z / mag);
    }
}

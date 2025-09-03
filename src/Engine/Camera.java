package Engine;

import Engine.util.Vector3D;

public class Camera {
    public Vector3D position;
    public Vector3D forward;
    public Vector3D up;
    public Vector3D right;
    public double fov;
    public double aspect;

    public Camera(Vector3D position, Vector3D lookAt, Vector3D up, double fov, double aspect) {
        this.position = position;
        this.forward = lookAt.sub(position).normalise();
        this.right = forward.cross(up).normalise();
        this.up = right.cross(forward).normalise();
        this.fov = fov;
        this.aspect = aspect;
    }

    public Vector3D getRayDirection(double ndcX, double ndcY) {
        return right.scale(ndcX).add(up.scale(ndcY)).add(forward).normalise();
    }

    private Vector3D target = new Vector3D(0, 0, 0);

    public void setPosition(Vector3D position) {
        this.position = position;
        this.forward = target.sub(position).normalise();
        this.right = forward.cross(up).normalise();
        this.up = right.cross(forward).normalise();
    }

    public void setTarget(Vector3D lookAt, Vector3D upVector) {
        this.target = lookAt;
        this.forward = lookAt.sub(position).normalise();
        this.right = forward.cross(upVector).normalise();
        this.up = right.cross(forward).normalise();
    }

    public void setTarget(Vector3D lookAt) {
        setTarget(lookAt, new Vector3D(0, 1, 0));
    }
}

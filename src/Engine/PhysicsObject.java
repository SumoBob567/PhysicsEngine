package Engine;

public class PhysicsObject {
    protected Vector3D position;
    protected Vector3D velocity;
    protected Vector3D acceleration;
    protected double mass;

    public boolean isGravityObject = false;

    public PhysicsObject(Vector3D position, double mass) {
        this.position = position;
        this.velocity = new Vector3D(0, 0, 0);
        this.acceleration = new Vector3D(0, 0, 0);
        this.mass = mass;
    }

    public void applyForce(Vector3D force) {
        Vector3D a = force.scale(1.0 / mass);
        this.acceleration = this.acceleration.add(a);
    }

    public void integrate(double dt) {
        velocity = velocity.add(acceleration.scale(dt));
        position = position.add(velocity.scale(dt));

        acceleration = new Vector3D(0, 0, 0);
    }

    public Vector3D getPosition() {
        return position;
    }
    public Vector3D getVelocity() {
        return velocity;
    }
    public Vector3D getAcceleration() {
        return acceleration;
    }
    public double getMass() {
        return mass;
    }
    public void setPosition(Vector3D position) {
        this.position = position;
    }
    public void setVelocity(Vector3D velocity) {
        this.velocity = velocity;
    }
    public void setAcceleration(Vector3D acceleration) {
        this.acceleration = acceleration;
    }
    public void setMass(double mass) {
        this.mass = mass;
    }
}

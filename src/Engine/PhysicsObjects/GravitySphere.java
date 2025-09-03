package Engine.PhysicsObjects;

import Engine.util.Vector3D;

public class GravitySphere extends Sphere {

    public GravitySphere(Vector3D position, double mass, double radius) {
        super(position, mass, radius);
    }

    @Override
    public boolean isGravityObject() {
        return true;
    }

}

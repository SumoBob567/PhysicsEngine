import Engine.PhysicsObjects.GravitySphere;
import Engine.PhysicsObjects.Sphere;
import Engine.PhysicsWorld;
import Engine.util.Vector3D;


public static void main(String[] args) {
    PhysicsWorld world = new PhysicsWorld(20, 20);
    Sphere sphere = new Sphere(new Vector3D(0, 10, 0), 1, 1);
    GravitySphere gravitySphere = new GravitySphere(new Vector3D(0,0,0), 10, 1);
    world.addObject(sphere);
    world.addObject(gravitySphere);
    sphere.setVelocity(new Vector3D(1, 0, 1));
    gravitySphere.setVelocity(new Vector3D(0, 0, 1));
    for (int i = 0; i < 200; i++) {
        Vector3D position = sphere.getPosition();
        System.out.printf("%.3f %.3f %.3f%n", position.x, position.y, position.z);
        world.step(1);
    }
}
import Engine.PhysicsObject;
import Engine.PhysicsWorld;
import Engine.Sphere;
import Engine.Vector3D;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        PhysicsWorld world = new PhysicsWorld(20);
        Sphere sphere = new Sphere(new Vector3D(), 1,1);
        world.addObject(sphere);
        for (int i = 0; i< 10; i++) {
            Vector3D position = sphere.getPosition();
            System.out.println(position.x + " " + position.y + " " + position.z);
            sphere.applyForce(new Vector3D(0,1,0));
            world.step(1);
        }
    }
}
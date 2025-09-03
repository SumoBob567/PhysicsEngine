import javax.swing.*;

import Engine.Camera;
import Engine.PhysicsWorld;
import Engine.PhysicsObjects.Sphere;
import Engine.PhysicsObjects.GravitySphere;
import Engine.util.Vector3D;

public class Main {
    public static void main(String[] args) {
        int width = 800;
        int height = 600;

        PhysicsWorld world = new PhysicsWorld(10, 10);

        Sphere sphere = new Sphere(new Vector3D(0, 10, 0), 1, 1);
        sphere.setVelocity(new Vector3D(1,0,0));

        Sphere sphere2 = new Sphere(new Vector3D(0, 40, 0), 1, 1);
        sphere2.setVelocity(new Vector3D(0.5,0,0));

        GravitySphere gravitySphere = new GravitySphere(new Vector3D(0,0,0), 10, 2);
        world.addObject(sphere);
        world.addObject(sphere2);
        world.addObject(gravitySphere);

        Camera camera = new Camera(
                new Vector3D(0, 0, 75),
                new Vector3D(0, 0, 0),
                new Vector3D(0, 1, 0),
                Math.toRadians(60),
                (double)width / height
        );

        Render3D panel = new Render3D(width, height, world, camera);
        JFrame frame = new JFrame("Orbit simulation");
        frame.add(panel);
        frame.setSize(width, height);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setResizable(true);

        double radius = 75;
        double cameraHeight = 0;
        double angularSpeed = Math.toRadians(0);
        double[] angle = {0};

        new Timer(16, e -> {
            world.step(0.1);

            Vector3D pos = sphere.getPosition();
            System.out.printf("Sphere position: x=%.3f, y=%.3f, z=%.3f%n", pos.x, pos.y, pos.z);

            angle[0] += angularSpeed;
            double camX = Math.sin(angle[0]) * radius;
            double camZ = Math.cos(angle[0]) * radius;
            camera.setPosition(new Vector3D(camX, cameraHeight, camZ));

            camera.setTarget(new Vector3D(0, 0, 0));

            panel.updateImage();
            panel.repaint();
        }).start();
    }
}

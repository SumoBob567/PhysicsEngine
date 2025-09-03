import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

import Engine.Camera;
import Engine.PhysicsObjects.GravitySphere;
import Engine.PhysicsObjects.Sphere;
import Engine.PhysicsWorld;
import Engine.PhysicsObjects.PhysicsObject;
import Engine.util.Vector3D;

public class Render3D extends JPanel {
    private BufferedImage canvas;
    private PhysicsWorld world;
    private Camera camera;

    public Render3D(int width, int height, PhysicsWorld world, Camera camera) {
        this.canvas = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        this.world = world;
        this.camera = camera;
        updateImage();
    }

    public void updateImage() {
        int width = canvas.getWidth();
        int height = canvas.getHeight();

        PhysicsObject[] objects = world.getObjects();
        PhysicsObject[] gravityObjects = world.getGravityObjects();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                double ndcX = (2 * (x + 0.5) / width - 1) * camera.aspect * Math.tan(camera.fov / 2);
                double ndcY = (1 - 2 * (y + 0.5) / height) * Math.tan(camera.fov / 2);
                Vector3D rayDir = camera.getRayDirection(ndcX, ndcY);

                int pixelColor = Color.BLACK.getRGB();
                double closestT = Double.MAX_VALUE;

                for (PhysicsObject obj : objects) {
                    if (obj == null) continue;

                    if (obj instanceof Sphere) {
                        Sphere s = (Sphere) obj;
                        double t = s.rayIntersect(camera.position, rayDir);
                        if (t > 0 && t < closestT) {
                            closestT = t;

                            Vector3D hitPoint = camera.position.add(rayDir.scale(t));
                            Vector3D normal = hitPoint.sub(s.getPosition()).normalise();

                            double ambient = 0.01;
                            double brightness = ambient;

                            for (PhysicsObject lightObj : gravityObjects) {
                                if (lightObj != null) {
                                    Vector3D lightDir = lightObj.getPosition().sub(hitPoint).normalise();
                                    double distance = lightObj.getPosition().sub(hitPoint).magnitude();
                                    double attenuation = 1.0 / (distance);

                                    brightness += Math.max(0, normal.dot(lightDir)) * attenuation;
                                }
                            }

                            if (s instanceof GravitySphere) {
                                double emission = 1.0;
                                brightness += emission;
                            }


                            brightness = Math.min(1.0, brightness);

                            double gamma = 2.2;
                            double correctedBrightness = Math.pow(brightness, 1.0 / gamma);

                            Color base = new Color(s.getColor());
                            int r = (int)Math.min(255, base.getRed() * correctedBrightness);
                            int g = (int)Math.min(255, base.getGreen() * correctedBrightness);
                            int b = (int)Math.min(255, base.getBlue() * correctedBrightness);

                            pixelColor = new Color(r, g, b).getRGB();

                        }
                    }
                }

                canvas.setRGB(x, y, pixelColor);
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(canvas, 0, 0, null);
    }
}

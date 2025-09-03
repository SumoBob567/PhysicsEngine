import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

import Engine.PhysicsObjects.GravitySphere;
import Engine.PhysicsObjects.PhysicsObject;
import Engine.PhysicsObjects.Sphere;
import Engine.PhysicsWorld;
import Engine.util.Vector3D;

public class ImageTest extends JPanel {
    private BufferedImage canvas;
    private PhysicsObject[] objects;

    public ImageTest(int width, int height, PhysicsObject[] objectlist) {
        this.canvas = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        this.objects = objectlist;

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                canvas.setRGB(x, y, Color.BLACK.getRGB());

                for (int i = 0; i< objectlist.length; i++) {
                    PhysicsObject object = objectlist[i];
                    if (object != null) {
                        if (object.drawCheck(x,y,width,height)) {canvas.setRGB(x, y, Color.RED.getRGB());}
                    }
                }

            }
        }
    }

    public void updateImage() {
        int width = canvas.getWidth();
        int height = canvas.getHeight();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int pixel = Color.BLACK.getRGB();

                for (PhysicsObject object : objects) {
                    if (object != null) {
                        if (object.drawCheck(x, y, width, height)) {
                            pixel = object.getColor();
                        }
                    }
                }
                canvas.setRGB(x, y, pixel);
            }
        }
    }

    public static int convertx(int x, int width) {
        return x + width/2;
    }
    public static int converty(int y, int height) {
        return y + height/2;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(canvas, 0, 0, null);
    }

    public static void main(String[] args) {

        PhysicsWorld world = new PhysicsWorld(20, 20);
        Sphere sphere = new Sphere(new Vector3D(0, 10, 0), 1, 1);
        GravitySphere gravitySphere = new GravitySphere(new Vector3D(0,0,0), 10, 2);
        world.addObject(sphere);
        world.addObject(gravitySphere);

        JFrame frame = new JFrame("Pixel Renderer");
        ImageTest panel = new ImageTest(800, 600, world.getObjects());
        frame.add(panel);
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        sphere.setVelocity(new Vector3D(1, 0, 0));
        gravitySphere.setVelocity(new Vector3D(0, 0, 0));

        Timer timer = new Timer(16, e -> {
            world.step(0.016);
            panel.updateImage();
            panel.repaint();
        });
        timer.start();
    }

}
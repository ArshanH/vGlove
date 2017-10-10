import java.lang.Math;

public class Vector {

   private double x,y,z;

   public Vector() {
      x = 0;
      y = 0;
      z = 0;
   }

   public Vector(double x, double y, double z) {

      double mag = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2) + Math.pow(z, 2));

      this.x = x / mag;
      this.y = y / mag;
      this.z = z / mag;
   }

   public void update(double x, double y, double z) {

      double mag = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2) + Math.pow(z, 2));

      this.x = x / mag;
      this.y = y / mag;
      this.z = z / mag;
   }

   public void print() {
      System.out.printf("%.4f %.4f %.4f\n", (float)x,(float)y, (float)z);
   }

   public double getX() {
      return x;
   }

   public double getY() {
      return y;
   }

   public double getZ() { return z; }

}

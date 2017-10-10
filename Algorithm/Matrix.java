public class Matrix {

   private Vector v0, v1, v2;

   public Matrix() {
      v0 = new Vector();
      v1 = new Vector();
      v2 = new Vector();
   }

   public Matrix(Vector v0) {
      this.v0 = v0;
      v1 = new Vector();
      v2 = new Vector();
   }

   public Matrix(Vector v0, Vector v1) {
      this.v0 = v0;
      this.v1 = v1;
     v2 = new Vector();
   }

   public Matrix(Vector v0, Vector v1, Vector v2) {
      this.v0 = v0;
      this.v1 = v1;
      this.v2 = v2;
   }

   public void updateV0(Vector v) {
      v0 = v;
   }

   public void updateV1(Vector v) {
      v1 = v;
   }

   public void updateV2(Vector v) {
      v2 = v;
   }

   public void crossProductV0V1() {
      double x = v0.getY() * v1.getZ() - v0.getZ() * v1.getY();
      double y = v0.getZ() * v1.getX() - v0.getX() * v1.getZ();
      double z = v0.getX() * v1.getY() - v0.getY() * v1.getX();
      updateV2(new Vector(x, y, z));
   }

   public void inverse() {
      double x0 = v1.getY() * v2.getZ() - v1.getZ() * v2.getY();
      double y0 = v0.getZ() * v2.getY() - v0.getY() * v2.getZ();
      double z0 = v0.getY() * v1.getZ() - v1.getY() * v0.getZ();

      double x1 = v1.getZ() * v2.getX() - v1.getX() * v2.getZ();
      double y1 = v0.getX() * v2.getZ() - v0.getZ() * v2.getX();
      double z1 = v0.getZ() * v1.getX() - v0.getX() * v1.getZ();
      
      double x2 = v1.getX() * v2.getY() - v1.getY() * v2.getX();
      double y2 = v0.getY() * v2.getX() - v0.getX() * v2.getY();
      double z2 = v0.getX() * v1.getY() - v0.getY() * v1.getX();
      
      v0.update(x0, y0, z0);
      v1.update(x1, y1, z1);      
      v2.update(x2, y2, z2);      
   }

   public Vector multiply(Vector v) {
      double x = v0.getX() * v.getX() + v1.getX() * v.getY() + v2.getX() * v.getZ();
      double y = v0.getY() * v.getX() + v1.getY() * v.getY() + v2.getY() * v.getZ();
      double z = v0.getZ() * v.getX() + v1.getZ() * v.getY() + v2.getZ() * v.getZ();
      return new Vector(x,y,z);
   }

   public Vector getV0() {
      return v0;
   }

   public Vector getV1() {
      return v1;
   }

   public Vector getV2() {
      return v2;
   }

   public void print() {
      v0.print();
      v1.print();
      v2.print();
   }
}

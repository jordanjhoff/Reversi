
public class Main {
  public static void main(String[] args) {
    int radius = model.getRadius();

    for (int r = -radius; r <= radius; r++) {
      int rMin = Math.max(-radius, -r - radius);
      int rMax = Math.min(radius, -r + radius);
      for (int q = rMin; q <= rMax; q++) {
        int s = -r - q;

        System.out.println("Hexagon at (" + q + ", " + r + ", " + s + ")");
      }
    }
  }
}
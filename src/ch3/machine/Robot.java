package ch3.machine;

public class Robot {

  Machine location;
  String bin;

  public Robot() {
  }

  public Machine location() {
    return location;
  }

  public void moveTo(final Machine location) {
    this.location = location;
  }

  public void pick() {
    this.bin = location.take();
  }

  public String bin() {
    return bin;
  }

  public void release() {
    location.put(bin);
    bin = null;
  }
}

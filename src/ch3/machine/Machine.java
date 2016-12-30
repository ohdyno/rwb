package ch3.machine;

public class Machine {

  String name;
  String location;
  String bin;

  public Machine(final String name, final String location) {
    this.name = name;
    this.location = location;
  }

  public String take() {
    final String result = bin;
    bin = null;
    return result;
  }

  public String bin() {
    return bin;
  }

  public void put(final String bin) {
    this.bin = bin;
  }

  public String name() {
    return name;
  }
}
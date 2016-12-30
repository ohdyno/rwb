package ch10.queue;

import java.util.ArrayList;

public class Queue {

  ArrayList<String> delegate = new ArrayList<>();

  public Queue() {
  }

  public void addRear(final String s) {
    delegate.add(s);
  }

  public int getSize() {
    return delegate.size();
  }

  public String removeFront() {
    final String result = delegate.get(0).toString();
    delegate.remove(0);
    return result;
  }
}
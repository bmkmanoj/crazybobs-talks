package org.crazybob.talks.references;
public class TestNativeResource {
  private boolean finalized;
  public synchronized void write(byte[] data) {
    if (finalized)
      System.err.println("Yikes!");
  }
  protected synchronized void finalize() {
    finalized = true;
  }

  static class Trash {
    final TestNativeResource nr = new TestNativeResource();
    final TestSegfaultFactory sf = new TestSegfaultFactory(nr);
    @Override
    protected void finalize() throws Throwable {
      System.out.print('.');
    }
  }
  private static void makeTrash() {
    new Trash();
  }
  public static void main(String[] args) throws InterruptedException {
    for (int i = 0; i < 1000; i++) {
      makeTrash();
      System.gc();
      Thread.sleep(10);
    }
  }
}

class TestSegfaultFactory {
  private final TestNativeResource nr;
  public TestSegfaultFactory(TestNativeResource nr) {
    this.nr = nr;
  }
  @Override protected void finalize() {
    nr.write("garbage".getBytes());
  }
}


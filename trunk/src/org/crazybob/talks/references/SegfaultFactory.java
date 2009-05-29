package org.crazybob.talks.references;
public class SegfaultFactory {
  private final NativeResource nr;
  public SegfaultFactory(NativeResource nr) {
    this.nr = nr;
  }

  @Override protected void finalize() {
    /// BAD
    // 50/50 chance of failure
    nr.write("I'm taking the VM with me!".getBytes());
    /// NORMAL
  }
}

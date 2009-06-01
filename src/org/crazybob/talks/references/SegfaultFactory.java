package org.crazybob.talks.references;
public class SegfaultFactory {
  private final NativeMemory nr;
  public SegfaultFactory(NativeMemory nr) {
    this.nr = nr;
  }

  @Override protected void finalize() {
    /// BAD
    // 50/50 chance of failure
    nr.write("I'm taking the VM with me!".getBytes());
    /// NORMAL
  }
}

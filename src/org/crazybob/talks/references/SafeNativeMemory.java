package org.crazybob.talks.references;
public class SafeNativeMemory extends NativeMemory {
  private boolean finalized;

  @Override public synchronized void write(byte[] data) {
    if (!finalized) super.write(data);
    else /* do nothing? */;
  }

  @Override protected synchronized void finalize() {
    finalized = true;
    super.finalize();
  }
}
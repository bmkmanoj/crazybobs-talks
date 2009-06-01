package org.crazybob.talks.references;

public class NativeMemory {
  public NativeMemory() { init(); }

  /** Allocates native memory. */
  private native void init();

  /** Writes to native memory. */
  public native void write(byte[] data);

  /** Frees native memory. */
  @Override protected native void finalize();
}
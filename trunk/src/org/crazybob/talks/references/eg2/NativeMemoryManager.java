package org.crazybob.talks.references.eg2;
import java.lang.ref.ReferenceQueue;
import java.util.Set;
import java.util.HashSet;
public class NativeMemoryManager {
  private static final Set<NativeMemoryReference> refs
      = new HashSet<NativeMemoryReference>();
  private static final ReferenceQueue<NativeMemory> rq
      = new ReferenceQueue<NativeMemory>();
  public synchronized static NativeMemory allocate() {
    NativeMemory nm = new NativeMemory();
    refs.add(new NativeMemoryReference(nm, rq));
    cleanUp();
    return nm;
  }
  private static void cleanUp() {
    NativeMemoryReference ref;
    while ((ref = (NativeMemoryReference) rq.poll()) != null) {
      NativeMemory.free(ref.address);
      refs.remove(ref);
    }
  }
}

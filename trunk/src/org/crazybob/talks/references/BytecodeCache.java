package org.crazybob.talks.references;
import com.google.common.collect.MapMaker;
import com.google.common.base.Function;
import java.util.Map;
public class BytecodeCache {
  final static Map<Class<?>, byte[]> cache = new MapMaker()
      .weakKeys()
      .softValues()
      .makeComputingMap(new Function<Class<?>, byte[]>() {
        public byte[] apply(Class<?> clazz) {
          /// ...
          return null;
        }
      });
  
   public static byte[] bytesFor(Class<?> clazz) {
     return cache.get(clazz);
   }
}

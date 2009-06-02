package org.crazybob.talks.references;
import com.google.common.collect.MapMaker;
import com.google.common.base.Function;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.lang.reflect.Method;
public class GetterMethods {
  final static Map<Class<?>, List<Method>> cache = new MapMaker()
      .weakKeys()
      .softValues()
      .makeComputingMap(new Function<Class<?>, List<Method>>() {
        public List<Method> apply(Class<?> clazz) {
          List<Method> getters = new ArrayList<Method>();
          for (Method m : clazz.getMethods())
            if (m.getName().startsWith("get"))
               getters.add(m);
          return getters;
        }
      });
  public static List<Method> on(Class<?> clazz) {
    return cache.get(clazz);
  }
}

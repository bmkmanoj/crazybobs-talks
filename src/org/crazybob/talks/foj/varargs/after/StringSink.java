package org.crazybob.talks.foj.varargs.after;
import java.util.ArrayList;
import java.util.List;
class StringSink extends Sink<String> {
  final List<String> list = new ArrayList<String>();
  /// BAD
  // Warning: "override generates a more specific varargs
  //           type erasure"
  /// NORMAL
  @Override void add(String... a) {
    list.addAll(Arrays.asList(a));
  }
  @Override public String toString() {
    return list.toString();
  }
  public static void main(String[] args) {
    Sink<String> ss = new StringSink();
    ss.addUnlessNull("cyanide");
    System.out.println(ss);
  }
}

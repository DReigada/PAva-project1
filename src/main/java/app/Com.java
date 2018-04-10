  package app;

import ist.meic.pa.GenericFunctions.GenericFunction;
import ist.meic.pa.GenericFunctions.util.GenericFunctionClassHelper;
import ist.meic.pa.GenericFunctions.util.MethodMap;

import java.util.Vector;

@GenericFunction
public interface Com {

  public GenericFunctionClassHelper helper = new GenericFunctionClassHelper(Com.class, "bine$");

  static Object bine(Object a, Object b) {
    Vector<Object> v = new Vector<Object>();
    v.add(a);
    v.add(b);
    return v;
  }

  static Integer bine(Integer a, Integer b) {
    return a + b;
  }

  static Object bine(String a, Object b) {
    return a + ", " + b + "!";
  }

  static Object bine(String a, Integer b) {
    return (b == 0) ? "" : a + bine(a, b - 1);
  }
}

class Test2 {
  public static void main(String[] args) throws Exception {
//    Com.param1.put(Object.class, Com.class.getMethod("bine", Object.class, Object.class));
//    Com.param1.put(Integer.class, Com.class.getMethod("bine", Integer.class, Integer.class));
//    Com.param1.put(String.class, Com.class.getMethod("bine", String.class, Object.class));
//    Com.param1.put(String.class, Com.class.getMethod("bine", String.class, Integer.class));
//
//    Com.param2.put(Object.class, Com.class.getMethod("bine", Object.class, Object.class));
//    Com.param2.put(Integer.class, Com.class.getMethod("bine", Integer.class, Integer.class));
//    Com.param2.put(Object.class, Com.class.getMethod("bine", String.class, Object.class));
//    Com.param2.put(Integer.class, Com.class.getMethod("bine", String.class, Integer.class));

//    Object res1 = Com.newBine(new Object(), new Object());
//    Object res2 = Com.newBine("asd", 1);
//
//    System.out.println(res1);
//    System.out.println(res2);

//    Object[] arr1 = new Object[]{"Hello", 2, 'A'};
//
//    System.out.println(arr1[1].getClass());
//
//    Set<Method> res1 = Com.param1.get(arr1[0].getClass());
//    Set<Method> res2 = Com.param2.get(arr1[1].getClass());
//
//    res1.retainAll(res2);
//    res1.forEach(System.out::println);
//
////    Object invRes = res1.iterator().next().invoke(null, arr1[0], arr1[1]);
////    res1.iterator().next();
////    res1.iterator().next();
////    res1.iterator().next();
////    System.out.println(invRes);

//    Object[] objs1 = new Object[]{"Hello", 1, 'A'};
//    Object[] objs2 = new Object[]{"World", 2, 'B'};
//    for (Object o1 : objs1) {
//      for (Object o2 : objs2) {
//        System.out.println("Combine(" + o1 + ", " + o2 + ") -> " + Com.newBine(o1, o2));
//      }
//    }

  }
}


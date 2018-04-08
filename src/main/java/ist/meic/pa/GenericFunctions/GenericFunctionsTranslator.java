package ist.meic.pa.GenericFunctions;

import javassist.*;

import java.util.Arrays;

public class GenericFunctionsTranslator implements Translator {

  @Override
  public void start(ClassPool pool) {
  }

  @Override
  public void onLoad(ClassPool pool, String classname) throws NotFoundException, CannotCompileException {
    CtClass clazz = pool.get(classname);
    try {
      Object annot = clazz.getAnnotation(GenericFunction.class);

      if (annot != null) {
        CtMethod[] methods = clazz.getDeclaredMethods();
        // TODO validate methods

        int numArgs = methods[0].getParameterTypes().length;

        addNewMethod(pool, clazz, numArgs);

        changeGenericFunctionMethods(clazz, methods);
      }

    } catch (ClassNotFoundException e) {
      throw new RuntimeException(e);
    }
  }

  private void changeGenericFunctionMethods(CtClass clazz, CtMethod[] methods) throws CannotCompileException {
    for (CtMethod method : methods) {
      CtMethod newMethod = new CtMethod(method, clazz, null);
      newMethod.setBody("" +
          "{" +
          "    System.out.print(\"HEEERE: \");" +
          "    System.out.println(java.util.Arrays.toString($args));" +
          "    return ($r) newMethod$($$);" +
          "}"
      );

      method.setName(method.getName() + "$");

      clazz.addMethod(newMethod);
    }
  }

  private void addNewMethod(ClassPool pool, CtClass clazz, int numArgs) throws NotFoundException, CannotCompileException {
    CtClass[] argsArr = new CtClass[numArgs];
    CtClass objectClass = pool.get("java.lang.Object");
    Arrays.fill(argsArr, objectClass);

    String body = "" +
        "{" +
        "    ist.meic.pa.GenericFunctions.util.MethodMapWithClass[] arr = new ist.meic.pa.GenericFunctions.util.MethodMapWithClass[]{" +
        "        new ist.meic.pa.GenericFunctions.util.MethodMapWithClass(param1, $1.getClass())," + // TODO change this to be generic
        "        new ist.meic.pa.GenericFunctions.util.MethodMapWithClass(param2, $2.getClass())" +
        "    };" +
        "    Object result = ist.meic.pa.GenericFunctions.Helpers.invokeMethod(ist.meic.pa.GenericFunctions.Helpers.getBestMethod(arr), $args);" +
        "    return ($r)result;" +
        "}";

    CtMethod newGenMethod = CtNewMethod.make(Modifier.STATIC, objectClass, "newMethod$", new CtClass[]{objectClass, objectClass}, new CtClass[]{}, body, clazz);

    clazz.addMethod(newGenMethod);
  }
}

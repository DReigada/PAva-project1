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

        addHelperField(clazz, renamedMethod(methods[0].getName()));

        addNewMethod(pool, clazz, numArgs);

        changeGenericFunctionMethods(clazz, methods);
      }

    } catch (ClassNotFoundException e) {
      throw new RuntimeException(e);
    }
  }

  private void addHelperField(CtClass clazz, String methodName) throws CannotCompileException {
    String helperName = "ist.meic.pa.GenericFunctions.util.GenericFunctionClassHelper";

    CtField helperField = CtField.make(
        "public static final " + helperName + " helper$ = " +
            "new " + helperName + "(" + clazz.getName() + ".class, \"" + methodName + "\");", clazz);

    clazz.addField(helperField);
  }

  private void changeGenericFunctionMethods(CtClass clazz, CtMethod[] methods) throws CannotCompileException {
    for (CtMethod method : methods) {
      CtMethod newMethod = new CtMethod(method, clazz, null);
      newMethod.setBody("" +
          "{" +
          "    return ($r) newMethod$($$);" +
          "}"
      );

      method.setName(renamedMethod(method.getName()));

      clazz.addMethod(newMethod);
    }
  }

  private void addNewMethod(ClassPool pool, CtClass clazz, int numArgs) throws NotFoundException, CannotCompileException {
    CtClass[] argsArray = new CtClass[numArgs];
    CtClass objectClass = pool.get("java.lang.Object");
    Arrays.fill(argsArray, objectClass);

    String body = "" +
        "{" +
        "    ist.meic.pa.GenericFunctions.util.MethodMapWithClass[] arr = helper$.validMethods($args);" +
        "    Object result = ist.meic.pa.GenericFunctions.Helpers.invokeMethod(ist.meic.pa.GenericFunctions.Helpers.getBestMethod(arr), $args);" +
        "    return ($r)result;" +
        "}";

    CtMethod newGenMethod = CtNewMethod.make(Modifier.STATIC, objectClass, "newMethod$", argsArray, new CtClass[]{}, body, clazz);

    clazz.addMethod(newGenMethod);
  }

  private String renamedMethod(String oldName) {
    return oldName + "$";
  }
}

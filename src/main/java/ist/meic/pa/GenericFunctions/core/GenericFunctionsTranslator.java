package ist.meic.pa.GenericFunctions.core;

import ist.meic.pa.GenericFunctions.GenericFunction;
import ist.meic.pa.GenericFunctions.exceptions.GenericFunctionException;
import ist.meic.pa.GenericFunctionsExtended.GenericFunctionExtended;
import javassist.*;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

public class GenericFunctionsTranslator implements Translator {

  @Override
  public void start(ClassPool pool) {
  }

  @Override
  public void onLoad(ClassPool pool, String classname) throws NotFoundException, CannotCompileException {
    CtClass clazz = pool.get(classname);
    try {
      Optional<Config> config = getConfig(clazz);

      if (config.isPresent()) {
        CtMethod[] methods = clazz.getDeclaredMethods();
        // TODO validate methods

        int numArgs = methods[0].getParameterTypes().length;

        addHelperField(clazz, renamedMethod(methods[0].getName()), config.get());

        addNewMethod(pool, clazz, numArgs);

        changeGenericFunctionMethods(clazz, methods);
      }

    } catch (ClassNotFoundException e) {
      throw new GenericFunctionException("This should never happen", e);
    }
  }

  private Optional<Config> getConfig(CtClass clazz) throws ClassNotFoundException {
    GenericFunctionExtended extendedAnnotation = (GenericFunctionExtended) clazz.getAnnotation(GenericFunctionExtended.class);
    GenericFunction defaultAnnotation = (GenericFunction) clazz.getAnnotation(GenericFunction.class);

    if (extendedAnnotation != null) {
      return Optional.of(new Config(extendedAnnotation));
    } else if (defaultAnnotation != null) {
      return Optional.of(new Config(defaultAnnotation));
    } else {
      return Optional.empty();
    }
  }

  private void addHelperField(CtClass clazz, String methodName, Config config) throws CannotCompileException {
    String helperName = "ist.meic.pa.GenericFunctions.core.GenericFunctionClassHelper";

    String[] helperArgs = new String[]{
        clazz.getName() + ".class",
        "\"" + methodName + "\"",
        Boolean.toString(config.useCache),
        "\"" + config.methodComparator + "\""
    };

    String joinedArgs = Arrays.stream(helperArgs).collect(Collectors.joining(", ", "(", ")"));

    CtField helperField = CtField.make(
        "public static final " + helperName + " helper$ = new " + helperName + joinedArgs + ";", clazz);

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
        "    Object result = helper$.runFunction($args);" +
        "    return ($r)result;" +
        "}";

    CtMethod newGenMethod = CtNewMethod.make(Modifier.STATIC, objectClass, "newMethod$", argsArray, new CtClass[]{}, body, clazz);

    clazz.addMethod(newGenMethod);
  }

  private String renamedMethod(String oldName) {
    return oldName + "$";
  }
}

package ist.meic.pa.GenericFunctions.core;

import ist.meic.pa.GenericFunctions.GenericFunction;
import ist.meic.pa.GenericFunctions.exceptions.GenericFunctionException;
import ist.meic.pa.GenericFunctionsExtended.GenericFunctionExtended;
import javassist.*;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

public class GenericFunctionsTranslator implements Translator {

  private static int getParameterCount(CtMethod method) {
    try {
      return method.getParameterTypes().length;
    } catch (NotFoundException e) {
      throw new GenericFunctionException("Could not get parameter count for method: " + method.getLongName(), e);
    }
  }

  private static String renamedMethod(String oldName) {
    return oldName + "$";
  }

  @Override
  public void start(ClassPool pool) {
  }

  @Override
  public void onLoad(ClassPool pool, String classname) throws NotFoundException, CannotCompileException {
    CtClass clazz = pool.get(classname);
    if (clazz.isFrozen()) {
      return;
    }

    Optional<Config> config = getConfig(clazz);

    if (config.isPresent()) {
      CtMethod[] methods = clazz.getDeclaredMethods();
      // TODO validate methods

      addHelperField(clazz, renamedMethod(methods[0].getName()), config.get());

      changeGenericFunctionMethods(clazz, methods);
    }
  }

  private Optional<Config> getConfig(CtClass clazz) {
    try {
      GenericFunctionExtended extendedAnnotation = (GenericFunctionExtended) clazz.getAnnotation(GenericFunctionExtended.class);
      GenericFunction defaultAnnotation = (GenericFunction) clazz.getAnnotation(GenericFunction.class);

      if (extendedAnnotation != null) {
        return Optional.of(new Config(extendedAnnotation));
      } else if (defaultAnnotation != null) {
        return Optional.of(new Config(defaultAnnotation));
      } else {
        return Optional.empty();
      }
    } catch (ClassNotFoundException e) {
      throw new GenericFunctionException("Failed to get annotation from class. (This should never happen)", e);
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
          "    return ($r) helper$.runFunction($args);" +
          "}"
      );

      method.setName(renamedMethod(method.getName()));

      clazz.addMethod(newMethod);
    }
  }
}

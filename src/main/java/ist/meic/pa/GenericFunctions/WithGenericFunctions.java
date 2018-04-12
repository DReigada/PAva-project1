package ist.meic.pa.GenericFunctions;

import ist.meic.pa.GenericFunctions.core.GenericFunctionsTranslator;
import javassist.ClassPool;
import javassist.Loader;
import javassist.Translator;

import java.util.Arrays;

public class WithGenericFunctions {
  public static void main(String[] args) throws Throwable {
    if (args.length < 1) {
      throw new RuntimeException("Missing class name argument");
    }

    Translator t = new GenericFunctionsTranslator();
    ClassPool pool = ClassPool.getDefault();
    Loader cl = new Loader();
    cl.addTranslator(pool, t);


    String className = args[0];
    String[] newArgs = Arrays.copyOfRange(args, 1, args.length);

    cl.run(className, newArgs);
  }

}

package ist.meic.pa.GenericFunctions.core;

import ist.meic.pa.GenericFunctions.GenericFunction;
import ist.meic.pa.GenericFunctionsExtended.GenericFunctionExtended;

class Config {
  public final boolean useCache;

  public Config(GenericFunctionExtended annotation) {
    this.useCache = annotation.useCache();
  }

  public Config(GenericFunction annotation) {
    this.useCache = false;
  }
}
package org.ogre4j;

public abstract class ParamCommand {
  public abstract String doGet(Object target);

  public abstract void doSet(Object target, String val);
}

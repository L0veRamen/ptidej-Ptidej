package org.ogre4j;

public abstract class RenderQueueListener extends NativeObject {

  public abstract void renderQueueStarted(int id, boolean skipThisQueue);

  public abstract void renderQueueEnded(int id, boolean repeatThisQueue);
}

//- Copyright © 2008-2010 8th Light, Inc. All Rights Reserved.
//- Limelight and all included source files are distributed under terms of the GNU LGPL.

package limelight.model.api;

import limelight.model.Production;

public class UtilitiesProduction
{
  private Production production;

  public UtilitiesProduction(Production production)
  {
    this.production = production;
  }

  public String getName()
  {
    return production.getName();
  }

  public void setName(String name)
  {
    production.setName(name);
  }

  public boolean allowClose()
  {
    return production.allowClose();
  }

  public void close()
  {
    production.close();
  }

  public Object callMethod(String name, Object... args)
  {
    return production.getProxy().callMethod(name, args);
  }

  public void publish_on_drb(int port)
  {
    production.getProxy().publish_on_drb(port);
  }

  public Object alert(Object message)
  {   
    return production.getProxy().callMethod("alert", message);
  }

  public Object shouldProceedWithIncompatibleVersion(String name, String version)
  {
    return production.getProxy().callMethod("proceed_with_incompatible_version?", name, version);
  }

  public Production getProduction()
  {
    return production;
  }
}
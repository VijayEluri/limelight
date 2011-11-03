//- Copyright © 2008-2011 8th Light, Inc. All Rights Reserved.
//- Limelight and all included source files are distributed under terms of the MIT License.

package limelight.java;

import limelight.Context;
import limelight.model.api.CastingDirector;
import limelight.model.api.PropProxy;
import limelight.ui.events.panel.CastEvent;
import limelight.ui.model.PropPanel;
import limelight.util.StringUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.lang.reflect.Method;

public class JavaCastingDirector implements CastingDirector
{
  private ClassLoader classLoader;

  public JavaCastingDirector(ClassLoader classLoader)
  {
    this.classLoader = classLoader;
  }

  public boolean hasPlayer(String playerName, String playersPath)
  {
    return Context.fs().exists(playerFilePath(playerName, playersPath));
  }

  private String playerFilePath(String playerName, String playersPath)
  {
    return playersPath + "/" + StringUtil.camalize(playerName) + ".xml";
  }

  public void castPlayer(PropProxy propProxy, String playerName, String playersPath)
  {
    String playerPath = playerFilePath(playerName, playersPath);
    final Document document = Xml.loadDocumentFrom(playerPath);
    final Element playerElement = document.getDocumentElement();
    final PropPanel prop = (PropPanel)propProxy.getPeer();
    final Object player = JavaPlayers.toPlayer(playerElement, classLoader, "limelight.ui.events.panel.", prop.getEventHandler());
    if(propProxy instanceof JavaProp)
      ((JavaProp)propProxy).addPlayer(player);
    invokeCastEvents(prop, playerElement, player);
  }

  private void invokeCastEvents(PropPanel prop, Element playerElement, Object player)
  {
    final CastEvent castEvent = new CastEvent(prop);
    for(Element child : Xml.loadChildElements(playerElement))
    {
      if("onCast".equals(child.getNodeName()))
      {
        String methodName = child.getTextContent().trim();
        final Method method = JavaPlayers.findMethod(methodName, player);
        new JavaEventAction(player, method).invoke(castEvent);
      }
    }
  }

  public ClassLoader getClassLoader()
  {
    return classLoader;
  }
}

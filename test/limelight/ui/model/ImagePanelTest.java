//- Copyright © 2008-2011 8th Light, Inc. All Rights Reserved.
//- Limelight and all included source files are distributed under terms of the MIT License.

package limelight.ui.model;

import limelight.io.FileSystem;
import limelight.model.FakeProduction;
import limelight.model.api.FakePropProxy;
import limelight.io.StreamReader;
import limelight.util.TestUtil;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ImagePanelTest
{
  private ImagePanel panel;
  private MockProp parent;
  public FakeScene root;

  @BeforeClass
  public static void classSetUp() throws Exception
  {
    FileSystem.installed();
  }

  @Before
  public void setUp() throws Exception
  {
    root = new FakeScene();
    root.setStage(new MockStage());
    parent = new MockProp();
    root.add(parent);
    panel = new ImagePanel();
    parent.add(panel);
  }

  @Test
  public void setImageFile() throws Exception
  {
    panel.setFilename("blah/blah.png");
    assertEquals("blah/blah.png", panel.getFilename());
  }

  @Test
  public void rotationAngle() throws Exception
  {
    panel.setRotation(0);
    assertEquals(0, panel.getRotation(), 0.1);
  }

  @Test
  public void parentIsSterilized() throws Exception
  {
    assertEquals(true, parent.isSterilized());
  }

  @Test
  public void scaled() throws Exception
  {
    assertEquals(true, panel.isScaled());

    panel.setScaled(false);

    assertEquals(false, panel.isScaled());
  }

  @Test
  public void parentSizeChangesAlwaysRequiresLayout() throws Exception
  {
    root.panelsNeedingLayout.clear();
    assertEquals(false, root.hasPanelNeedingLayout(panel));

    panel.consumableAreaChanged();

    assertEquals(true, root.hasPanelNeedingLayout(panel));
  }

  @Test
  public void settingImageData() throws Exception
  {
    checkSettingImageDataWith("star.gif");
    checkSettingImageDataWith("star.jpg");
    checkSettingImageDataWith("star.jpg");
//    checkSettingImageDataWith("star.tif", "tif");
//    checkSettingImageDataWith("star.tif", "tiff");
    checkSettingImageDataWith("star.png");
    checkSettingImageDataWith("star.bmp");
    checkSettingImageDataWith("star.wbm");
    checkSettingImageDataWith("star.wbm");
  }

  private void checkSettingImageDataWith(String imageFile) throws Exception
  {
    StreamReader reader = new StreamReader(TestUtil.fs.inputStream(TestUtil.dataDirPath(imageFile)));
    byte[] bytes = reader.readBytes(100000);

    panel.setData(bytes);

    assertEquals(200, panel.getImage().getHeight(null));
    assertEquals(200, panel.getImage().getWidth(null));
  }

  @Test
  public void settingImageDataUpdatesInfo() throws Exception
  {
    panel.setFilename(TestUtil.dataDirPath("small_star.gif"));
    panel.getImage();
    root.panelsNeedingLayout.clear();

    checkSettingImageDataWith("star.gif");

    assertEquals(200, panel.getImageWidth(), 0.1);
    assertEquals(200, panel.getImageHeight(), 0.1);
    assertEquals("[DATA]", panel.getFilename());
    assertEquals(true, root.hasPanelNeedingLayout(panel));
    assertEquals(true, root.hasPanelNeedingLayout(parent));
  }

  @Test
  public void doesntCrashWhenNoImageFileProvided() throws Exception
  {
    panel.setFilename(null);
    assertEquals(null, panel.getImage());

    panel.setFilename("");
    assertEquals(null, panel.getImage());
  }
}

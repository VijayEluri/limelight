//- Copyright © 2008-2011 8th Light, Inc. All Rights Reserved.
//- Limelight and all included source files are distributed under terms of the MIT License.

package limelight.io;

import junit.framework.TestCase;
import limelight.Context;

import java.io.File;

public class TempDirectoryTest extends TestCase
{
  private TempDirectory tempDirectory;
  private FakeFileSystem fs;

  public void setUp() throws Exception
  {
    fs = FakeFileSystem.installed();
    tempDirectory = new TempDirectory();
  }

  public void tearDown() throws Exception
  {
    tempDirectory.cleanup();
  }

  public void testLocations() throws Exception
  {
    String systemTempDir = System.getProperty("java.io.tmpdir");
    String limelightTempDir = fs.join(systemTempDir, "limelight");
    assertEquals(limelightTempDir, tempDirectory.getRoot());
    assertEquals(true, fs.exists(tempDirectory.getRoot()));
  }

  public void testCleanup() throws Exception
  {
    fs.createDirectory(fs.join(tempDirectory.getRoot(), "blah"));
    fs.createDirectory(fs.join(tempDirectory.getRoot(), "foo"));
    fs.createDirectory(fs.join(tempDirectory.getRoot(), "bar"));

    tempDirectory.cleanup();

    assertEquals(false, new File(fs.join(tempDirectory.getRoot(), "blah")).exists());
    assertEquals(false, new File(fs.join(tempDirectory.getRoot(), "foo")).exists());
    assertEquals(false, new File(fs.join(tempDirectory.getRoot(), "bar")).exists());
    assertEquals(false, fs.exists(tempDirectory.getRoot()));
  }

  public void testNewTempDir() throws Exception
  {
    String newDirectory = tempDirectory.createNewDirectory();

    assertNotNull(newDirectory);
    assertEquals(true, fs.exists(newDirectory));
    assertEquals(tempDirectory.getRoot(), fs.parentPath(newDirectory));
  }

  public void testMultipleNewTempDirs() throws Exception
  {
    String temp1 = tempDirectory.createNewDirectory();
    String temp2 = tempDirectory.createNewDirectory();

    assertEquals(true, fs.exists(temp1));
    assertEquals(tempDirectory.getRoot(), fs.parentPath(temp1));
    assertEquals(true, fs.exists(temp2));
    assertEquals(tempDirectory.getRoot(), fs.parentPath(temp2));
    assertEquals(false, temp1.equals(temp2));
  }

  public void testDownloadsDirectory() throws Exception
  {
    String downloadsDirectory = tempDirectory.getDownloadsDirectory();

    assertEquals(true, fs.exists(downloadsDirectory));
    assertEquals(true, fs.isDirectory(downloadsDirectory));
    assertEquals(tempDirectory.getRoot(), fs.parentPath(downloadsDirectory));
  }
}

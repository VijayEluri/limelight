//- Copyright © 2008-2011 8th Light, Inc. All Rights Reserved.
//- Limelight and all included source files are distributed under terms of the MIT License.

package limelight.io;

import limelight.Context;
import limelight.LimelightException;
import limelight.util.StringUtil;

import java.io.*;
import java.net.URI;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class FileSystem
{
  public static final Pattern WinDrive = Pattern.compile("[A-Z]\\:\\\\");

  protected String separator = System.getProperty("file.separator");
  protected boolean windows = System.getProperty("os.name").toLowerCase().contains("windows");

  public static FileSystem installed()
  {
    FileSystem fs = new FileSystem();
    Context.instance().fs = fs;
    return fs;
  }

  public void createDirectory(String path)
  {
    resolve(path).mkdirs();
  }

  public boolean exists(String path)
  {
    return resolve(path).exists();
  }

  public boolean isRoot(String path)
  {
    return resolve(path).isRoot();
  }

  public boolean isDirectory(String path)
  {
    return resolve(path).isDirectory();
  }

  public String absolutePath(String path)
  {
    return resolve(path).getAbsolutePath();
  }

  public OutputStream outputStream(String path)
  {
    return resolve(path).outputStream();
  }

  public InputStream inputStream(String path)
  {
    return resolve(path).inputStream();
  }

  public String[] fileListing(String path)
  {
    return resolve(path).listing();
  }

  public long modificationTime(String path)
  {
    return resolve(path).lastModified();
  }

  public void delete(String path)
  {
    resolve(path).delete();
  }

  public void createTextFile(String path, String content)
  {
    createDirectory(parentPath(path));
    try
    {
      final OutputStream output = outputStream(path);
      output.write(content.getBytes());
      output.close();
    }
    catch(IOException e)
    {
      throw new LimelightException(e);
    }
  }

  public String readTextFile(String path)
  {
    StreamReader reader = new StreamReader(inputStream(path));
    String result = reader.readAll();
    reader.close();
    return result;
  }

  public String parentPath(String path)
  {
    final String parent = resolve(path).parentPath();
    if(isAbsolute(path))
      return absolutePath(parent);
    else
      return parent;
  }

  public boolean isAbsolute(String path)
  {
    return resolve(path).isAbsolute();
  }

  public String relativePathBetween(String origin, String target)
  {
    if(origin.equals(target))
      return ".";

    final String absoluteOrigin = absolutePath(origin);
    final String absoluteTarget = absolutePath(target);
    if(absoluteOrigin.equals(absoluteTarget))
      return ".";

    String path = "";
    String commonParent = absoluteOrigin;
    while(!absoluteTarget.startsWith(commonParent))
    {
      path += ".." + separator();
      commonParent = parentPath(commonParent);
      if(isRoot(commonParent))
        break;
    }
    final String result = path + absoluteTarget.substring(commonParent.length());
    return result.startsWith(separator()) ? result.substring(1) : result;
  }

  // UTILITY  METHODS --------------------------------------------------------------------------------------------------

  public String separator()
  {
    return separator;
  }

  public String homeDir()
  {
    return System.getProperty("user.home");
  }

  public String workingDir()
  {
    return System.getProperty("user.dir");
  }

  public String join(String root, String... parts)
  {
    if(isAbsolute(root))
      return removeDuplicateSeprators(root + "/" + StringUtil.join("/", (Object[]) parts));
    else
      return removeDuplicateSeprators(root + separator + StringUtil.join(separator, (Object[]) parts));
  }

  public String baseName(String path)
  {
    String name = filename(path);
    final int extensionIndex = name.lastIndexOf(".");
    if(extensionIndex == -1)
      return name;
    else
      return name.substring(0, extensionIndex);
  }

  public String fileExtension(String path)
  {
    final File file = new File(path);
    String name = file.getName();
    final int extensionIndex = name.lastIndexOf(".");
    if(extensionIndex == -1)
      return "";
    else
      return name.substring(extensionIndex);
  }

  public String filename(String path)
  {
    if(windows ? WinDrive.matcher(path).matches() : "/".equals(path))
      return path;
    if(path.endsWith(separator))
      path = path.substring(0, path.length() - separator.length());
    final int lastSeparator = path.lastIndexOf(separator);
    if(lastSeparator == -1)
      return path;
    return path.substring(lastSeparator + 1);
  }

  public String pathTo(String parent, String target)
  {
    if(target == null)
      return parent;
    else if(parent == null || isAbsolute(target))
      return target;
    else
      return join(parent, target);
  }

  // HELPER METHODS ----------------------------------------------------------------------------------------------------

  private static void deleteDirectory(File current)
  {
    File[] files = current.listFiles();

    for(int i = 0; files != null && i < files.length; i++)
    {
      File file = files[i];
      if(file.isDirectory())
        deleteDirectory(file);
      else
        deleteFile(file);
    }
    deleteFile(current);
  }

  private static void deleteFile(File file)
  {
    if(!file.exists())
      return;
    if(!file.delete())
      throw new RuntimeException("Could not delete '" + file.getAbsoluteFile() + "'");
    waitUntilFileIsDeleted(file);
  }

  private static void waitUntilFileIsDeleted(File file)
  {
    int checks = 10;
    while(file.exists())
    {
      if(--checks <= 0)
      {
        System.out.println("Breaking out of delete wait");
        break;
      }
      try
      {
        Thread.sleep(100);
      }
      catch(InterruptedException e)
      {
        //okay
      }
    }
  }

  private String removeDuplicateSeprators(String path)
  {
    final String duplicate = separator + separator;
    if(path.contains(duplicate))
      return removeDuplicateSeprators(path.replace(duplicate, separator));
    else
      return path;
  }

  // WHERE THE MAGIC HAPPENS -------------------------------------------------------------------------------------------

  protected Path resolve(String path)
  {
    if(path.startsWith("file:"))
      return new FilePath(this, path);
    else if(path.startsWith("jar:"))
      return new ZipPath(this, path);
    else
      return new FilePath(this, path);
  }

  protected static interface Path
  {
    boolean exists();

    void mkdirs();

    boolean isDirectory();

    OutputStream outputStream();

    InputStream inputStream();

    String getAbsolutePath();

    void delete();

    String[] listing();

    long lastModified();

    boolean isRoot();

    String parentPath();

    boolean isAbsolute();
  }

  private static class FilePath implements Path
  {
    private String path;
    private File file;
    private URI uri;
    private FileSystem fs;

    public FilePath(FileSystem fs, String path)
    {
      this.fs = fs;
      this.path = path;
      if(path.startsWith("file:"))
        this.path = path.substring(5);
    }

    public File file()
    {
      if(file == null)
        file = new File(path);
      return file;
    }
    
    public URI uri()
    {
      if(uri == null)
        uri = file().toURI();
      return uri;
    }

    public boolean isRoot()
    {
      try
      {
        return file().getCanonicalFile().getParent() == null;
      }
      catch(IOException e)
      {
        return false;
      }
    }

    public String parentPath()
    {
      final String parentPath = file().getParent();
      if(parentPath == null)
      {
        final String absoluteParentPath = file().getAbsoluteFile().getParent();
        if(absoluteParentPath == null)
          return file().getAbsolutePath();
        else
          return absoluteParentPath;
      }
      return parentPath;
    }

    public boolean isAbsolute()
    {
      return fs.windows ? startsWithWinDive() : path.startsWith(fs.separator);
    }

    private boolean startsWithWinDive()
    {
      final Matcher matcher = WinDrive.matcher(path);
      return matcher.find() && matcher.start() == 0;
    }

    public boolean exists()
    {
      return file().exists();
    }

    public void mkdirs()
    {
      if(!file().exists() && !file().mkdirs())
        throw new LimelightException("Can't establish directory: " + path);
    }

    public boolean isDirectory()
    {
      return file().isDirectory();
    }

    public OutputStream outputStream()
    {
      try
      {
        return new FileOutputStream(path);
      }
      catch(FileNotFoundException e)
      {
        throw new LimelightException(e);
      }
    }

    public InputStream inputStream()
    {
      try
      {
        return new FileInputStream(path);
      }
      catch(FileNotFoundException e)
      {
        throw new LimelightException(e);
      }
    }

    public String getAbsolutePath()
    {
      try
      {
        return uri().toString();
      }
      catch(Exception e)
      {
        throw new LimelightException(e);
      }
    }

    public void delete()
    {
      if(file().isDirectory())
        deleteDirectory(file());
      else
        deleteFile(file());
    }

    public String[] listing()
    {
      return file().list();
    }

    public long lastModified()
    {
      return file().lastModified();
    }
  }

  private static class ZipPath implements Path
  {
    private FilePath pathToZip;
    private String pathToFile;
    private ZipFile zip;
    private FileSystem fs;

    private ZipPath(FileSystem fs, String path)
    {
      this.fs = fs;
      final int bangIndex = path.indexOf("!");
      if(bangIndex == -1)
        throw new LimelightException("Invalid Jar file path: " + path);

      pathToZip = (FilePath)fs.resolve(path.substring(4, bangIndex));
      pathToFile = path.substring(bangIndex + 2);
    }

    private ZipFile zip()
    {
      if(zip == null)
      {
        try
        {
          zip = new ZipFile(pathToZip.file());
        }
        catch(IOException e)
        {
          throw new LimelightException(e);
        }
      }
      return zip;
    }

    private ZipEntry zipEntry()
    {
      return zip().getEntry(pathToFile);
    }

    public boolean exists()
    {
      return pathToZip.exists() && (zipEntry() != null);
    }

    public void mkdirs()
    {
      throw new LimelightException("JarPath.mkdirs() is not supported");
    }

    public boolean isDirectory()
    {
      if(pathToFile.endsWith("/"))
        return zipEntry().isDirectory();
      else
      {
        final ZipEntry entry = zip().getEntry(pathToFile + "/");
        return entry != null && entry.isDirectory();
      }
    }

    public OutputStream outputStream()
    {
      throw new LimelightException("JarPath.outputStream() is not supported");
    }

    public InputStream inputStream()
    {
      try
      {
        return zip().getInputStream(zipEntry());
      }
      catch(IOException e)
      {
        throw new LimelightException(e);
      }
    }

    public String getAbsolutePath()
    {
      return "jar:" + pathToZip.getAbsolutePath() + "!/" + pathToFile;
    }

    public void delete()
    {
      throw new LimelightException("JarPath.delete() is not supported");
    }

    public String[] listing()
    {
      if(!isDirectory())
        return new String[0];
      else
      {
        pathToFile = pathToFile.endsWith("/") ? pathToFile : pathToFile + "/";
        ArrayList<String> list = new ArrayList<String>();
        final Enumeration<? extends ZipEntry> entries = zip().entries();
        while(entries.hasMoreElements())
        {
          ZipEntry entry = entries.nextElement();
          final String entryName = entry.getName();
          if(entryName.startsWith(pathToFile))
          {
            String name = entryName.substring(pathToFile.length());
            if(name.endsWith("/"))
              name = name.substring(0, name.length() - 2);
            if(name.length() > 0 && !name.contains("/"))
              list.add(fs.filename(entryName));
          }
        }
        return list.toArray(new String[list.size()]);
      }
    }

    public long lastModified()
    {
      return zipEntry().getTime();
    }

//    public File file()
//    {
//      throw new LimelightException("JarPath.file() is not supported");
//    }

    public boolean isRoot()
    {
      return pathToFile.isEmpty();
    }

    public String parentPath()
    {
      return "jar:" + pathToZip.getAbsolutePath() + "!" + fs.parentPath("/" + pathToFile);
    }

    public boolean isAbsolute()
    {
      return pathToZip.isAbsolute();
    }
  }
}

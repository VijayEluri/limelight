//- Copyright © 2008-2011 8th Light, Inc. All Rights Reserved.
//- Limelight and all included source files are distributed under terms of the MIT License.

package limelight;

import limelight.commands.*;

import java.util.HashMap;
import java.util.Map;

public class CmdLineMain
{
  public static Map<String, Class<? extends Command>> commands = new HashMap<String, Class<? extends Command>>();
  public static String mainCmd = "java -jar limelight.jar";

  static
  {
    commands.put("help", HelpCommand.class);
    commands.put("open", OpenCommand.class);
    commands.put("pack", PackCommand.class);
    commands.put("unpack", UnpackCommand.class);
    commands.put("create", CreateCommand.class);
  }

  private Command command;

  public static void main(String[] args)
  {
    new CmdLineMain().run(args);
  }

  private Arguments arguments;

  public void run(String... args)
  {
    buildArguments();
    final Map<String, String> options = arguments.parse(args);
    if(options.containsKey("help"))
      command = new HelpCommand();
    else
      command = buildCommand(options);

    applyDebugOption(options);
    applyLogLevelOption(options);

    command.execute(arguments.leftOverArgs());
  }

  private void applyLogLevelOption(Map<String, String> options)
  {
    if(options.containsKey("log"))
      Log.setLevel(options.get("log"));
  }

  private void applyDebugOption(Map<String, String> options)
  {
    if(options.containsKey("debug"))
      Log.debugOn();
  }

  private Command buildCommand(Map<String, String> options)
  {
    String commandName = options.get("command");
    if(commandName == null)
      return new HelpCommand("Command missing");  

    Class<? extends Command> commandClass = commands.get(commandName);
    if(commandClass == null)
     return new HelpCommand("Unrecognized command: " + commandName);

    return Command.instance(commandClass);
  }

  public Arguments getArguments()
  {
    if(arguments == null)
      arguments = buildArguments();
    return arguments;
  }

  private Arguments buildArguments()
  {
    arguments = new Arguments();
    arguments.addParameter("command", "The name of the command to execute. Use --help for a listing of command.");
    arguments.addSwitchOption("h", "help", "Prints this help message");
    arguments.addSwitchOption("d", "debug", "Sets logger to DEBUG level on stderr");
    arguments.addValueOption("l", "log", "LEVEL", "Sets log level: CRITICAL, WARN, INFO, CONFIG, DEBUG, FINE");
    return arguments;
  }

  public Command getCommand()
  {
    return command;
  }

  public static void addCommand(String commandName, Class<? extends Command> commandClass)
  {
    commands.put(commandName, commandClass);
  }
}

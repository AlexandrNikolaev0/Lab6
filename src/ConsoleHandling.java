import javafx.util.Pair;
import jdk.nashorn.internal.codegen.ObjectCreator;

import java.io.*;
import java.util.ArrayList;
import java.util.Hashtable;

public class ConsoleHandling {

    /**<p>Метод, определяющий существование введенной команды и выбирающий команду из доступных</p>
     * @param consoleLine - строка с командой
     * @param world - объект, содержащий коллекцию элементов
     */
    public static void commandSelecter(String consoleLine, World world) {
        String[] commands = {"insert", "show", "remove_lower", "clear", "sort", "info", "remove", "exit"};
        String realCommand = "";
        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                save(world);
            }
        });
        for (String command : commands) {
            if (consoleLine.startsWith(command)) {
                if (consoleLine.indexOf(" ") != -1) {
                    realCommand = consoleLine.substring(0, consoleLine.indexOf(" "));
                } else {
                    realCommand = consoleLine;
                }
            }
        }
        String commandi="Введена неверная команда.\nСписок доступных команд:\n remove_lower {String key}\n " +
                "remove_lower {element}\n remove {String key}\n insert {String key} {element}\n show\n clear\n info\n sort\n exit";
        if (realCommand.equals("")) {
            System.out.println(commandi);
        } else {
            switch (realCommand) {
                case "info":
                    commandInfo(world);
                    break;
                case "show":
                    commandShow(world);
                    break;
                case "clear":
                    commandClear(world);
                    break;
                case "insert":
                    commandInsert(consoleLine, world);
                    break;
                case "remove":
                    commandRemove(consoleLine, world);
                    break;
                case "remove_lower":
                    commandRemoveLower(consoleLine, world);
                    break;
                case "sort":
                    commandSort(world);
                    break;
                case "exit":
                    save(world);

                    break;
                default:
                    System.out.println(commandi);
                    break;
            }

        }
    }

    public static void save(World world)
    {
        //Обновление файла.
        try {
            File file = new File("save.csv");
            OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(file));
            for (int i = 0; i <= world.maxKey; i++) {
                Creature creat = world.getCreatures().get(i);
                if (creat != null) {
                    out.write(creat.getClass().getName() + "," + creat.getX() + "," + creat.getMyName() + "," +
                            creat.getStatus() + ",");
                    if (creat instanceof Human) {
                        Human hum = (Human) creat;
                        out.write(hum.getSex() + ",,,");
                    }
                    if (creat instanceof Dog) {
                        Dog dog = (Dog) creat;
                        out.write("," + dog.isSitting + "," + dog.isShowingTongue + "," + dog.areEyesClosed);
                    }
                    if (!((creat instanceof Human) || (creat instanceof Dog)))
                        out.write(",,,");
                    out.write("\n");
                }
            }
            out.close();
        }
        catch (FileNotFoundException fnfe)
        {
            System.out.println((fnfe));
        }
        catch (IOException ioe)
        {
            System.out.println(ioe);
        }
    }

    /**<p>Метод, выполняющий команду info</p>
     * @param world - объект, содержащиий коллекцию элементов
     */
            public static void commandInfo(World world)
            {
                System.out.println("Тип коллекции: Hashtable<Integer,Creature>\n" +
                        "Количество элементов: "+world.getCreatures().size());
            }

    /**<p>Метод, выполняющий команду show</p>
     * @param world - объект, содержащий коллекцию элементов
     */
            public static void commandShow(World world)
            {
                if(world.getCreatures().size()>0) {
                    System.out.println("Элементы коллекции:");
                    for (int i = 0; i <= world.maxKey; i++) {
                        if(world.getCreatures().get((i))!=null)
                        {
                        System.out.println("Ключ:"+i+"; "+world.getCreatures().get(i));
                        }
                    }

                }
                else
                {
                    System.out.println("Коллекция не содержит объекты");
                }
            }

    /**<p>Метод, выполняющий команду clear</p>
     * @param world - объект, содержащий коллекцию элементов
     */
            public static void commandClear(World world)
            {
                world.getCreatures().clear();
                System.out.println("Все элементы коллекции удалены");
            }

    /**<p>Метод, выполняющий команду insert</p>
     * @param consoleLine - команда с параметрами
     * @param world - объект, содержащий коллекцию элементов
     */
            public static void commandInsert(String consoleLine,World world)
            {

                try {
                    /*String str = "insert {\"Key\": 12, \"Element\": {\"Class\": \"Human\", \"Coord\": 18, \"Name\": \"Alesha\"," +
                            " \"Phys\": \"STRONG\", \"Sex\": \"m\"}}";
                */


                if((consoleLine.indexOf("{") > 0) && (consoleLine.indexOf("}")>0) && (consoleLine.indexOf(":")>0))
                {
                    ArrayList<Object> newCreat = jsonParser.getCreature(consoleLine);
                    Creature value = (Creature) newCreat.get(1);
                    int key = (Integer) newCreat.get(0);
                    if(value!=null) {
                        world.getCreatures().put(key, value);
                        System.out.println("Элемент успешно добавлен");
                        if(key>world.maxKey)
                        world.maxKey=key;
                    }

                }
                else
                {
                    System.out.println("Аргументы команды insert введены неправильно");
                }
                }
                catch (Exception e) { System.out.println("Аргументы команды insert введены неправильно"); }
            }

    /**<p>Метод, выполняющий команду remove по ключу</p>
     * @param consoleLine - команда с параметром
     * @param world - объект, содержащий коллекцию элементов
     */
            public static void commandRemove(String consoleLine,World world) {
                try {
                    if ((consoleLine.indexOf("{") > 0) && (consoleLine.indexOf("}") > 0) && (consoleLine.indexOf(":") > 0)) {
                        consoleLine = consoleLine.substring(consoleLine.indexOf("{") + 1, consoleLine.indexOf("}"));
                        String name = consoleLine.split(":")[0];
                        String value = consoleLine.split(":")[1];
                        value = value.trim();
                        if (name.equals("\"Key\"")) {
                            world.getCreatures().remove(Integer.valueOf(value));
                            System.out.println("Элемент с ключем " + value + " удален");
                        }
                        else { System.out.println("Не найдено поле \"Key\"");}
                    } else {
                        System.out.println("Аргументы команды remove введены неправильно.");
                    }
                }
                catch (Exception e) { System.out.println("Аргументы команды remove введены неправильно."); }
            }


    /**<p>Метод, выполняющий команду remove_lower как по ключу, так и по элементу</p>
     * @param consoleLine - строка с командой
     * @param world - объект, содержащий коллекцию элементов
     * */
            public static void commandRemoveLower(String consoleLine,World world) {
                try {
                        consoleLine = consoleLine.substring(consoleLine.indexOf("{") + 1, consoleLine.indexOf("}"));
                        String name = consoleLine.substring(0,consoleLine.indexOf(":"));
                        String value = consoleLine.substring(consoleLine.indexOf(":")+1);
                        value = value.trim();
                        if (name.equals("\"Key\"")) {
                            for (int i = Integer.valueOf(value) - 1; i >= 0; i--) {
                                world.getCreatures().remove(i);
                            }
                            System.out.println("Элементы с ключами меньше " + Integer.valueOf(value) + " удалены");
                        }
                        if (name.equals("\"Element\"")) {
                           /* String str = "\"Class\": \"Human\", \"Coord\": 18, \"Name\": \"Alesha\"," +
                                    " \"Phys\": \"STRONG\", \"Sex\": \"m\"";*/
                            String str = value;
                            str=str.substring(1);
                            System.out.println(str);
                            Creature newCreat = jsonParser.readCreature(str);

                            double coord = newCreat.getX();
                            for (int i = 0; i <= world.maxKey; i++) {
                                if (world.getCreatures().get(i) != null) {
                                    if (coord > world.getCreatures().get(i).getX())
                                        world.getCreatures().remove(i);
                                }
                            }
                            System.out.println("Элементы с координатой меньше " + coord + " удалены");
                        }
                }
                catch (Exception e){ System.out.println("Аргументы команды remove_lower введены неправильно."); }
             }

    /**<p>Метод, выполняющий команду sort</p>
     * @param world - объект, содержащий коллекцию элементов
     */
              public static void commandSort(World world)
              {
                  Hashtable<Integer,Creature> collection = world.getCreatures();
                  for(int i=0;i<=world.maxKey;i++)
                  {
                      for(int j=world.maxKey;j>0;j--)
                      {
                          collection = world.getCreatures();
                          if(collection.get(j)!=null)
                          {
                              Creature first = collection.get(j);
                              Creature second = collection.get(j-1);
                              if(collection.get(j-1)!=null)
                              {
                                if(first.getX()<second.getX())
                                {
                                    world.getCreatures().replace(j-1,first);
                                    world.getCreatures().replace(j,second);
                                }
                              }
                              else
                              {

                                  world.getCreatures().remove(j-1);
                                  world.getCreatures().put(j-1,first);
                                  world.getCreatures().remove(j);
                              }
                          }
                      }
                  }
                  System.out.println("Элементы отсортированы");
              }

}

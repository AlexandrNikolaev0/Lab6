

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
//Класс, который отвечает за считываение объектов из файла при запуске программы.
public class AutoRead {
    /**<p>Метод, читающий файл с элементами коллекции</p>
     * @return - возвращает массив строк, содержащих параметры элементов в формате csv
     */
    //Метод, считывающий файл с объектами. Возвращает строки объектов в формате
    public static ArrayList<String> readFromFile(String okrus) {
        try { Scanner cons = new Scanner(System.in);
            //System.out.println("Введите название файла с элементами");
            //String file = cons.nextLine();
            String file = okrus;
            if(file!=null) {
                Scanner scn = new Scanner(new File(file));
                ArrayList<String> strs = new ArrayList<String>();
                while (scn.hasNextLine()) {
                    strs.add(scn.nextLine());
                }
                scn.close();

                System.out.println("Файл загружен");
                return strs;
            }
            else
            {
                System.out.println("Файла по данной переменной окружения не существует");
                return null;
            }
        } catch (FileNotFoundException fnfe) {
            System.out.println(("Файл не найден или недоступен для чтения/записи"));
            return null;
        }
    }

    /**<p>Метод, создающий объекты из строк формата csv</p>
     * @param strs - массив строк
     * @return - коллекция объектов
     */
    public static Hashtable<Integer, Creature> parseStrings(ArrayList<String> strs) {
        try {
            Hashtable<Integer, Creature> creatTable = new Hashtable<Integer, Creature>();
            if (strs != null) {
                for (int j = 0; j < strs.size(); j++) {
                    String currentString = strs.get(j);
                    ArrayDeque<Integer> zapyatie = new ArrayDeque<Integer>();
                    boolean[] ifFull = new boolean[8];
                    Arrays.fill(ifFull, Boolean.FALSE);

                    for (int i = 0; i < currentString.length(); i++) {
                        if (currentString.charAt(i) == ',') {
                            zapyatie.add(i);
                        }
                    }

                    String[] parametri = new String[zapyatie.size() + 1];
                    int number = zapyatie.size();
                    for (int i = 0; i < number + 1; i++) {
                        switch (i) {
                            case (0):
                                parametri[i] = currentString.substring(0, zapyatie.getFirst().intValue());
                                break;
                            case (7):
                                if (currentString.length() - 1 > zapyatie.getFirst())
                                    parametri[i] = currentString.substring(zapyatie.pollFirst().intValue() + 1, currentString.length());
                                break;
                            default:
                                parametri[i] = currentString.substring(zapyatie.pollFirst().intValue() + 1, zapyatie.getFirst().intValue());

                                break;
                        }
                        if (parametri[i] != null)
                            if (!parametri[i].equals("")) {
                                ifFull[i] = true;
                            }
                    }

                    String classs = parametri[0];
                    int coord = 0;
                    if (ifFull[1]) {
                        coord = (int) Double.parseDouble(parametri[1]);
                    }
                    String name = parametri[2];
                    String phys = parametri[3];
                    String sex = parametri[4];
                    boolean isSitted = false;
                    if (ifFull[5]) {
                        isSitted = Boolean.parseBoolean(parametri[5]);
                    }
                    boolean isShowingTongue = false;
                    if (ifFull[6]) {
                        isShowingTongue = Boolean.parseBoolean(parametri[6]);
                    }
                    boolean areEyesClosed = false;
                    if (ifFull[7]) {
                        areEyesClosed = Boolean.parseBoolean(parametri[7]);
                    }


                    switch (classs) {
                        case "Creature":
                            creatTable.put(Integer.valueOf(j), CreatureAssembler.creatureAssembly(ifFull, coord, name, phys));
                            break;

                        case "Human":
                            creatTable.put(Integer.valueOf(j), CreatureAssembler.humanAssembly(ifFull, coord, name, phys, sex));
                            break;

                        case "Dog":
                            creatTable.put(Integer.valueOf(j), CreatureAssembler.dogAssembly(ifFull, coord, name, phys, isSitted, isShowingTongue, areEyesClosed));
                            break;

                        default:
                            break;
                    }
                }
                return creatTable;

            } else {
                return null;
            }

        } catch (Exception e) {
            System.out.println("Формат файла неправильный");
            return null;
        }
    }
}


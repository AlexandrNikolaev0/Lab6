import javafx.util.Pair;

import java.util.ArrayList;

public class jsonParser {

    /**<p>Метод, создающий из json-строки объект в зависимости от параметров в строке</p>
     * @param jsonLine - json-строка
     * @return - пара Ключ-Объект
     */
    public static ArrayList<Object> getCreature(String jsonLine)
    {
        jsonLine=jsonLine.substring(jsonLine.indexOf("{")+1,jsonLine.lastIndexOf("}"));
        String firstPart=jsonLine.substring(0,jsonLine.indexOf("}"));
        String secondPart=jsonLine.substring((jsonLine.indexOf("}")+1));
        secondPart=secondPart.trim();
        secondPart=secondPart.substring(1);
        String firstfirst=firstPart.split(":",2)[0].trim();
        String firstsecond=firstPart.split(":",2)[1].trim();
        String secondfirst=secondPart.split(":",2)[0].trim();
        String secondsecond=secondPart.split(":",2)[1].trim();
        int key=-1;
        if(firstfirst.equals("\"Key\"")) {
            key = Integer.parseInt(firstsecond.trim());
            //System.out.println(secondfirst+"  "+secondsecond);

            if (secondfirst.equals("\"Element\"")) {
                secondsecond = secondsecond.substring(secondsecond.indexOf("{") + 1, secondsecond.indexOf("}"));
                Creature creatureClass = readCreature(secondsecond);
                ArrayList<Object> pair = new ArrayList<>();
                pair.add(Integer.valueOf(key));
                pair.add(creatureClass);
                return pair;
            } else {
                System.out.println("Неправильно указан элемент" + secondfirst);
                ArrayList<Object> pair = new ArrayList<>();
                pair.add(Integer.valueOf(-1));
                pair.add(null);
                return pair;
            }
        }
        else
        {
            System.out.println("Неправильно введены аргументы. Необходим параметр Key");
            ArrayList<Object> pair = new ArrayList<>();
            pair.add(Integer.valueOf(-1));
            pair.add(null);
            return pair;
        }

    }

    /**<p>Метод, преобразующий часть json-строки в объект</p>
     * @param secondsecond - часть json-строки, содержащая параметры объекта
     * @return - объект
     */
    public static Creature readCreature(String secondsecond)
    {
        String[] parts=secondsecond.split(",");
        int doesClassExsist=0;
        Creature creatureClass = null;
        for(String part: parts)
        {

            String name = part.split(":")[0];
            String value = part.split(":")[1];

            value = value.trim();
            name = name.substring(name.indexOf("\"")+1,name.lastIndexOf("\""));
            if(name.equals("Class"))
                doesClassExsist++;
            if(value.startsWith("\""))
                value = value.substring(value.indexOf("\"")+1,value.lastIndexOf("\""));

            if(doesClassExsist<=1)
            {

                Human  humanClass=null;
                Dog dogClass=null;
                switch (name)
                {
                    case "Class":
                        switch (value)
                        {
                            case "Creature": creatureClass = new Creature(); break;
                            case "Human": creatureClass = new Human(); break;
                            case "Dog": creatureClass = new Dog(); break;
                            default: System.out.println("Введенный класс не существует.\n(Доступны классы Creature, Human, Dog)"); break;
                        }
                        break;
                    case "Coord":
                        creatureClass.setX(Integer.valueOf(value));
                        break;
                    case "Name":
                        creatureClass.setName(value);
                        break;
                    case "Phys":
                        creatureClass.setStatus(PhysicalStatus.valueOf(value));
                        break;
                    case "Sex":
                        if(creatureClass instanceof Human) {
                            humanClass = (Human) creatureClass;
                            humanClass.setSex(value);
                        }
                        break;
                    case "IsSitted":
                        if(creatureClass instanceof Dog)
                        {
                            dogClass = (Dog) creatureClass;
                            if(Boolean.valueOf(value))
                                dogClass.isSitting=true;
                        }
                        break;
                    case "IsShowingTongue":
                        if(creatureClass instanceof Dog)
                        {
                            dogClass=(Dog) creatureClass;
                            if(Boolean.valueOf(value))
                                dogClass.isShowingTongue=true;
                        }
                        break;
                    case "AreEyesClosed":
                        if(creatureClass instanceof Dog)
                        {
                            dogClass=(Dog) creatureClass;
                            if(Boolean.valueOf(value))
                                dogClass.areEyesClosed=true;
                        }
                        break;
                        default: break;
                }

            }
            else {
                System.out.println("Количество указанных полей \"Class\" не равно 1");
                if(doesClassExsist>1) {
                    creatureClass=null; break;
                }
            }

        }
        return creatureClass;
    }
}

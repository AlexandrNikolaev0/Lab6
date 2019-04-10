import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
	static class Start {
		static void go() {
			Human alice = new Human(10,"Alice","Female", PhysicalStatus.WEAK);
            Dog dog = new Dog(40,"Puppy", PhysicalStatus.USUAL);
            Shelter tree = new Shelter("Tree");
            RealObject stick = new RealObject(40,"Stick");
            RealObject tre=null;//Умышленная ошибка
            try {

            alice.runTo(tree);
            dog.startBark();
            dog.startAttacks(stick);
            alice.runFrom(dog);
            dog.finishBark();
          }
          catch(NullPointerException e)
          {
            throw new NpE();
          }
          catch(NpE e2)
          {
            System.out.println(e2);
          }
		}
	}

  public static void main(String[] args) {
    /*Start.go();
    Finish printend = new Finish() {
      @Override
      public void printEnd() {
        System.out.println("\nHAPPY END.");
      }
    };
    printend.printEnd();*/

    World world = new World("Reality");

    try {
        String okrus = args[0];
        world.setCreatures(AutoRead.parseStrings(AutoRead.readFromFile(okrus)));
        if (world.getCreatures() != null) {
            world.maxKey = world.getCreatures().size();
            String consoleLine = "";
            while (!consoleLine.equals("exit")) {
                Scanner reader = new Scanner(System.in);
                consoleLine = reader.nextLine();
                ConsoleHandling.commandSelecter(consoleLine, world);
            }
        }
    }
    catch (IndexOutOfBoundsException ioobe){
    System.out.println("В переменной окружения не задано имя файла");
    }
	}

}

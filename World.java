    import java.util.Hashtable;
    public class World {
        Hashtable<Integer, Creature> tableOfCreatures;
        String nameOfWorld;
        int maxKey=0;
        public World()
        {
            nameOfWorld = "AnyWorld";
        }

        public World(String name)
        {
            nameOfWorld = name;
        }

        public void setName(String name)
        {
            nameOfWorld = name;
        }

        public String getName()
        {
            return  nameOfWorld;
        }

        public void setCreatures(Hashtable<Integer,Creature> creatures)
        {
            tableOfCreatures = creatures;
        }

        public Hashtable<Integer,Creature> getCreatures()
        {
            return tableOfCreatures;
        }



    }

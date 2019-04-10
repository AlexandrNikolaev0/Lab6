public class Shelter extends RealObject
{


Shelter()
{
  super();
}

Shelter(String name)
{
  super(name);
}

Shelter(double x, String name)
{
  super(x, name);
}

@Override
public String toString()
{
return "Объект класса "+getClass().getName()+", имя:"+name+", координата:"+x;
}

}

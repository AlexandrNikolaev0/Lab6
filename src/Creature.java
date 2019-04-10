enum PhysicalStatus {
	STRONG(12), USUAL(6), WEAK(4);

	private double koeff;

	PhysicalStatus(double k) {
		koeff = k;
	}

	double getKoeff() { return koeff; }
}

public class Creature extends RealObject {
  PhysicalStatus stat;//Тип физической подготовки
  double phys;//Численный коэффициент физ подготовки
  double energy = 100d;//Числовое значение энергии существа
  double RUN_ENERGY_KOEF = 50;//Коэффициент затрат энергии во время бега
  Creature()
  {
    super();
    name="Unknown Creature";
    stat=PhysicalStatus.USUAL;
    phys = stat.getKoeff();
  }

  Creature(double x, String name, PhysicalStatus p) {
    super(x, name);
    stat = p;
    phys = stat.getKoeff();
  }

  void setStatus(PhysicalStatus p) {
    stat = p;
    phys = stat.getKoeff();
  }

  void setEnergy(double newEnergy) {
    energy = newEnergy;
  }

  PhysicalStatus getStatus() {
    return stat;
  }

  double getEnergy() {
    return energy;
  }


	void simpleRun(double shag) // Метод, выполняющий шаг(порцию бега)
	{
		x+=shag;
    System.out.println(name + " is running. X: " + x);
	}

	double getShag()// Метод, возвращающий значение, на которое может переместиться существо за единицу времени. Определяет уменьшение энергии.
	{
		double shag=phys;
		double energyShag;
		if(energy > 0)
    {
    	energy -= RUN_ENERGY_KOEF/(phys);
    	energyShag=shag;
    }
    else
    {
      energy=0;
      energyShag=shag/2;
    }
		return energyShag;
	}

	void run(int direction)
  {
		simpleRun(this.getShag()*direction);
  }// Метод, выполняющий бег в определенном направлении.

  void run(int direction, double xOb)//Метод, выполняющий перемещение в координату, если расстояние до нее меньше длины шага.
  {
		double energyShag = this.getShag()*direction;
		if(Math.abs(x-xOb)<=Math.abs(energyShag))
		{
			x=xOb;
		}
		else
		{
		simpleRun(energyShag);
		}
  }



  void runTo(RealObject rOb) //Метод, выполняющий бег к объекту.
  {
    
		try
		{
		
		if((this.getX()==0)&&(!this.isXSetted))
			throw new XException(this);
		if((rOb.getX()==0)&&(!rOb.isXSetted))
			throw new XException(rOb);
		}
		catch(XException e1) { System.out.println(e1); }
		

		System.out.println(name + " is running to "+ rOb.getMyName());
    while(this.getX()!=rOb.getX()){
       if(this.getX()>rOb.getX()) {
        int direction=-1;
        this.run(direction,rOb.getX());
       }
       if(this.getX()<rOb.getX()) {
        int direction=1;
        this.run(direction,rOb.getX());
       }
     }
    System.out.println(name + " has runned to "+ rOb.getMyName());
  }


  void runFrom(RealObject rOb) { //Метод, выполняющий бег от объекта, пока звук от него не угаснет.
		try
		{
		if((this.getX()==0)&&(!this.isXSetted))
			throw new XException(this);
		if((rOb.getX()==0)&&(!rOb.isXSetted))
			throw new XException(rOb);
		}
		catch(XException e) { System.out.println(e); }
      System.out.println(name + " is running from "+ rOb.getMyName());
      this.setHeardVol(rOb);
      System.out.println(rOb.getMyName()+"'s volume:"+hearVolume);

      while (hearVolume > 2)
      {
        this.setHeardVol(rOb);
        if((this.getX()-rOb.getX())>0)
        {
          int direction=1;
          this.run(direction);
        }
        if((this.getX()-rOb.getX())<0)
        {
          int direction=-1;
          this.run(direction);
        }
        System.out.println(rOb.getMyName()+"'s volume:"+hearVolume);

      }
      System.out.println(name + " has runned from "+ rOb.getMyName());

  }

  @Override
public int hashCode()
{
int res;
res=super.hashCode();
res+=(int)(phys*12 + energy*17)+RUN_ENERGY_KOEF/2;
return res;
}

@Override
public boolean equals(Object ob)
{
if(this==ob)
{
return true;
}
if(ob instanceof Creature)
{
Creature cr = (Creature)ob;
if(super.equals(cr)&&(this.stat==cr.stat)&&(this.phys==cr.phys)&&(this.energy==cr.energy))
{
return true;
}
}
return false;
}

@Override
public String toString()
{
return "Объект класса "+getClass().getName()+", имя:"+name+", координата:"+x+", Физ. состояние:"+stat.toString()+", энергия:"+energy;
}
}

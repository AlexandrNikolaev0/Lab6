public class Dog extends Creature
{
	final int SOUND_VOLUME = 10; //Уровень звука?
	final double ENERGY_BARK = 0.5; //Затраты энергии на лай
	final double ENERGY_ATTACK = 300; //Затраты энергии на атаку
	final double TYPE_CREATURE_POWER_KOEF=1.5;
	boolean isSitting=false;
	boolean isShowingTongue=false;
	boolean areEyesClosed=false;

	Dog()
	{
		super();
		name = "Dog";
		phys*=TYPE_CREATURE_POWER_KOEF;
	}

	Dog (double x, String name, PhysicalStatus p) {
		super(x, name, p);
		phys*=TYPE_CREATURE_POWER_KOEF;
	}

	@Override
public int hashCode()
{
int res;
res=super.hashCode()-(int)(SOUND_VOLUME-ENERGY_BARK*4-ENERGY_ATTACK+TYPE_CREATURE_POWER_KOEF*2);
return res;
}

	@Override
	public boolean equals(Object ob)
	{
		if(this==ob)
		{
			return true;
		}
		if(ob instanceof Dog)
		{
		 	Dog dg = (Dog)ob;
			if(super.equals(dg))
			{
				return true;
			}
		}
		return false;
	}

	@Override
	public String toString()
	{
		String str = "Объект класса "+getClass().getName()+", имя:"+name +", координата:"+x+", Физ. состояние:"+
				stat.toString()+", энергия:"+energy;
		if(isSitting)
			str = str+", сидит";
		if(isShowingTongue)
			str = str+", показывает язык";
		if(areEyesClosed)
			str = str+", глаза закрыты";
	return str;
	}

	void attack(RealObject obj) {
		if (energy > 0) {
			energy -= ENERGY_ATTACK/phys;
			System.out.println(obj.getMyName() + " is attacked by " + name);
		} else {
			energy=0;
		}
	}

	void startAttacks(RealObject obj) {
		while (energy > 0) {
			this.attack(obj);
		}
		energy=0;
		System.out.println(name + " can't attack, because is tired.");
		action.sit();
		action.tongue();
		action.closeEyes();
		System.out.println("\n");
	}

	void startBark() {
		System.out.println(name+" starts bark");
		this.setSpeakedVol((double)phys*SOUND_VOLUME);
	}

	void finishBark()
	{
		System.out.println(name+" finishes bark");
		this.setSpeakedVol(0);
	}
	Action action = new Action();
	class Action {
			void sit() {
				System.out.println(name + " is sitting");
				isSitting=true;
			}

			void tongue() {
				System.out.println(name + " is showing its tongue");
				isShowingTongue=true;
			}
		

			void closeEyes() {
				System.out.println(name + "'s eyes are closed");
				areEyesClosed=true;
			}
	}
}

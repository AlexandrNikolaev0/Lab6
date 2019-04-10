public class Human extends Creature
{
		String sex;
        final double SPEED = 1;
        final double TYPE_CREATURE_POWER_KOEF=1;

		Human()
		{
			super();
			name="Unknown Human";
			phys*=TYPE_CREATURE_POWER_KOEF;
		}

        Human (double x, String name, String sex, PhysicalStatus p) {
			super(x, name, p);
            this.sex = sex;
			phys*=TYPE_CREATURE_POWER_KOEF;
        }

        void myEnergy() {
                class Local {
                void show() {
                                System.out.println(name + "'s energy is " + energy);
                        }
                }
                Local local = new Local();
                local.show();
        }

        public String getSex()
		{
			return sex;
		}

		public void setSex(String s)
		{
			sex=s;
		}

				@Override
			public int hashCode()
			{
			int res;
			res=super.hashCode();
			res+=(int)(sex.length()*19/2 + SPEED*27 - TYPE_CREATURE_POWER_KOEF*11);
			return res;
			}

			@Override
			double getShag()
			{
				double shag=phys*SPEED*TYPE_CREATURE_POWER_KOEF;
				double energyShag;
				if(energy > 0)
		    {
		    	energy -= RUN_ENERGY_KOEF/(phys*TYPE_CREATURE_POWER_KOEF);
		    	energyShag=shag;
		    }
		    else
		    {
		      energy=0;
		      energyShag=shag/2;
		    }
				return energyShag;
			}


				@Override
				public boolean equals(Object ob)
				{
				  if(this==ob)
				  {
				    return true;
				  }
				  if(ob instanceof Human)
				  {
				    Human hu = (Human)ob;
				    if(super.equals(hu)&&(this.sex.equals(hu.sex)))
				    {
				      return true;
				    }
				  }
				  return false;
				}

				@Override
				public String toString()
				{
				return "Объект класса "+getClass().getName()+", имя:"+name +", пол:"+ sex +", координата:"+x+
						", Физ состояние:"+stat.toString()+", энергия:"+energy;
				}
}

public class RealObject extends AbstractObject implements Map, Sound
{
double x;
double hearVolume = 0;
double speakVolume = 0;
boolean isXSetted=false;//Задана ли координата

RealObject()
{
  name = "Unknown Object";
}

RealObject(String name)
{
  this.name = name;
}

RealObject(double x)
{
  this.x = x;
  isXSetted=true;
  name = "Unknown Object";
}

RealObject(double x, String name)
{
  this.x = x;
  isXSetted=true;
  this.name = name;
}

void setX(double x)
{
  this.x = x;
  isXSetted=true;
}

void setName(String name)
{
  this.name = name;
}
@Override
public double getX()
{
  return this.x;
}
@Override
public String getMyName()
{
  return this.name;
}
@Override
public double getSpeakedVol()
{
  return speakVolume;
}
@Override
public double getHeardVol()
{
  return hearVolume;
}
@Override
public void setSpeakedVol(double vol)
{
  speakVolume = vol;
}
@Override
public void setHeardVol(RealObject obj)
{
  hearVolume = obj.getSpeakedVol()/Math.abs(x-obj.x);
}
public boolean getIsXSetted() { return this.isXSetted;}
@Override
public boolean equals(Object ob)
{
if(this==ob)
{
return true;
}
if(ob instanceof RealObject)
{
RealObject rO = (RealObject)ob;
if((this.name.equals(rO.getMyName()))&&(this.x == rO.getX())&&(this.hearVolume == rO.getHeardVol())&&(this.speakVolume == rO.getSpeakedVol())&&(this.isXSetted==rO.getIsXSetted()))
{
return true;
}
}
return false;
}

@Override
public int hashCode()
{
  final int cons = 28;
  int res = 1;
  res = cons*res + name.length()*(cons-2);
  res += (int)x*(cons+14) + (int)(this.getSpeakedVol()*13);
  return res;
}

@Override
public String toString()
{
return "Объект класса "+getClass().getName()+", имя:"+name+", координата:"+x;
}
}

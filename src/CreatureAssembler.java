public class CreatureAssembler {

    /**<p>Метод, создающий объект Creature по параметрам</p>
     * @param ifFull - массив, определяющий наличие параметра
     * @param coord - координата
     * @param name - имя
     * @param phys - физическая подготовка
     * @return - объект Creature
     */
    public static Creature creatureAssembly(boolean[] ifFull, int coord, String name, String phys)
    {
        Creature cr = new Creature();
        if(ifFull[1])
            cr.setX(coord);
        if(ifFull[2])
            cr.setName(name);
        if(ifFull[3])
            cr.setStatus(PhysicalStatus.valueOf(phys));
        return cr;
    }

    /**<p>Метод, создающий объект Human по параметрам</p>
     * @param ifFull - массив, определяющий наличие параметра
     * @param coord - координата
     * @param name - имя
     * @param phys - физическая подготовка
     * @param sex - пол(строка)
     * @return - объект Human
     */
    public static Human humanAssembly(boolean[] ifFull, int coord, String name, String phys, String sex)
    {
        Human hu = new Human();
        if(ifFull[1])
            hu.setX(coord);
        if(ifFull[2])
            hu.setName(name);
        if(ifFull[3])
            hu.setStatus(PhysicalStatus.valueOf(phys));
        if(ifFull[4])
            hu.setSex(sex);
        return hu;
    }

    /**<p>Метод, создающий объект Dog по параметрам</p>
     * @param ifFull - массив, определяющий наличие параметра
     * @param coord - координата
     * @param name - имя
     * @param phys - физическая подготовка
     * @param isSit - состояние "Сидеть"
     * @param isShow - состояние "Показывать язык"
     * @param areEyes - состояник "Закрыть глаза"
     * @return - объект Dog
     */
    public static Dog dogAssembly(boolean[] ifFull, int coord, String name, String phys, boolean isSit, boolean isShow,
                                  boolean areEyes)
    {
        Dog dg = new Dog();
        if(ifFull[1])
            dg.setX(coord);
        if(ifFull[2])
            dg.setName(name);
        if(ifFull[3])
            dg.setStatus(PhysicalStatus.valueOf(phys));
        if(ifFull[5]&&isSit)
            dg.isSitting=true;
        if(ifFull[6]&&isShow)
            dg.isShowingTongue=true;
        if(ifFull[7]&&areEyes)
            dg.areEyesClosed=true;
        return dg;
    }
}

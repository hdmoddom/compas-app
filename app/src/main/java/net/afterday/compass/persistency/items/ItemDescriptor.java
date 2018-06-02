package net.afterday.compass.persistency.items;

import net.afterday.compass.core.serialization.Jsonable;

/**
 * Created by Justas Spakauskas on 3/25/2018.
 */

public interface ItemDescriptor extends Jsonable
{
    double NULL_MODIFIER = -99999999d;
    int getImage();
    String getName();
    int getNameId();
    boolean isBooster();
    boolean isArmor();
    boolean isArtefact();
    boolean isSingleUse();
    boolean isConsumable();
    long getDuration();
    double[] getModifiers();
    int getXpPoints();
    String getDescription();
    int getDescriptionId();
    boolean isUsable();
    boolean isDropable();
}

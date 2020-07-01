package de.marvinleiers.litebansgui.gui;

import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class Icon
{
    public final ItemStack itemStack;

    public final List<GuiClickAction> clickActions = new ArrayList<>();

    public Icon(ItemStack itemStack)
    {
        this.itemStack = itemStack;
    }

    public Icon addClickAction(GuiClickAction clickAction)
    {
        this.clickActions.add(clickAction);
        return this;
    }

    public List<GuiClickAction> getClickActions()
    {
        return this.clickActions;
    }
}
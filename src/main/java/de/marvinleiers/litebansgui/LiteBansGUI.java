package de.marvinleiers.litebansgui;

import de.marvinleiers.litebansgui.gui.GuiClickAction;
import de.marvinleiers.litebansgui.gui.GuiHolder;
import de.marvinleiers.litebansgui.gui.Icon;
import de.marvinleiers.litebansgui.utils.CustomConfig;
import de.marvinleiers.litebansgui.utils.ItemUtils;
import de.marvinleiers.litebansgui.utils.Messages;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicBoolean;

public final class LiteBansGUI extends JavaPlugin implements Listener
{
    public static HashMap<Integer, String> chatCommands = new HashMap<>();
    public static CustomConfig config;

    @Override
    public void onEnable()
    {
        config = new CustomConfig(this.getDataFolder().getPath() + "config.yml");

        Messages.setUp(this);

        chatCommands.put(1, "kick %player% First offense");
        chatCommands.put(2, "mute %player% 15m Second offense");
        chatCommands.put(3, "mute %player% 1h Third offense");
        chatCommands.put(4, "mute %player% 5h Fourth offense");
        chatCommands.put(5, "mute %player% 1d Fifth offense");
        chatCommands.put(6, "mute %player% 7d Sixth offense");
        chatCommands.put(7, "mute %player% Seventh offense");

        this.getServer().getPluginManager().registerEvents(this, this);
        this.getCommand("punish").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        if (!(sender instanceof Player))
        {
            sender.sendMessage(Messages.get("no-player"));
            return true;
        }

        Player player = (Player) sender;

        if (args.length != 1)
        {
            player.sendMessage("§cUsage: /" + label + " <target>");
            return true;
        }

        OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
        AtomicBoolean silent = new AtomicBoolean(false);

        GuiHolder gui = new GuiHolder(45, target.getName());

        Icon ad = new Icon(ItemUtils.create(Material.DIAMOND, "§eAdvertising")).addClickAction(new GuiClickAction()
        {
            @Override
            public void onClick(Player player)
            {
                GuiHolder holder = new GuiHolder(9, target.getName());

                Icon first = new Icon(ItemUtils.create(Material.EMERALD_BLOCK, "§cFirst offense")).addClickAction(new GuiClickAction()
                {
                    @Override
                    public void onClick(Player player)
                    {
                        Bukkit.dispatchCommand(player, "tempban " + target.getName() + (silent.get() ? "-s" : "") + " 14d advertisment");
                        player.closeInventory();
                    }
                });
                holder.setIcon(0, first);

                Icon second = new Icon(ItemUtils.create(Material.EMERALD_BLOCK, "§cSecond offense")).addClickAction(new GuiClickAction()
                {
                    @Override
                    public void onClick(Player player)
                    {
                        Bukkit.dispatchCommand(player, "tempban " + target.getName() + (silent.get() ? "-s" : "") + " 30d advertisment");
                        player.closeInventory();
                    }
                });
                holder.setIcon(1, second);

                Icon third = new Icon(ItemUtils.create(Material.EMERALD_BLOCK, "§cThird offense")).addClickAction(new GuiClickAction()
                {
                    @Override
                    public void onClick(Player player)
                    {
                        Bukkit.dispatchCommand(player, "ban " + target.getName() + (silent.get() ? "-s" : "") + " advertisment");
                        player.closeInventory();
                    }
                });
                holder.setIcon(2, third);

                Inventory adInv = holder.getInventory();

                player.openInventory(adInv);
            }
        });

        gui.setIcon(6, ad);

        Icon xray = new Icon(ItemUtils.create(Material.SKELETON_SKULL, "§eX-Ray")).addClickAction(new GuiClickAction()
        {
            @Override
            public void onClick(Player player)
            {
                GuiHolder holder = new GuiHolder(9, target.getName());

                Icon first = new Icon(ItemUtils.create(Material.EMERALD_BLOCK, "§cFirst offense")).addClickAction(new GuiClickAction()
                {
                    @Override
                    public void onClick(Player player)
                    {
                        Bukkit.dispatchCommand(player, "tempban " + target.getName() + (silent.get() ? "-s" : "") + " 30d xray");
                        player.closeInventory();
                    }
                });
                holder.setIcon(0, first);

                Icon second = new Icon(ItemUtils.create(Material.EMERALD_BLOCK, "§cSecond offense")).addClickAction(new GuiClickAction()
                {
                    @Override
                    public void onClick(Player player)
                    {
                        Bukkit.dispatchCommand(player, "ban " + target.getName() + (silent.get() ? "-s" : "") + " xray");
                        player.closeInventory();
                    }
                });
                holder.setIcon(1, second);

                Inventory xrayInv = holder.getInventory();

                player.openInventory(xrayInv);
            }
        });

        gui.setIcon(5, xray);

        Icon skinName = new Icon(ItemUtils.create(Material.BLACK_BANNER, "§eInnap Skin/Name")).addClickAction(new GuiClickAction()
        {
            @Override
            public void onClick(Player player)
            {
                GuiHolder holder = new GuiHolder(9, target.getName());

                Icon first = new Icon(ItemUtils.create(Material.EMERALD_BLOCK, "§cFirst offense")).addClickAction(new GuiClickAction()
                {
                    @Override
                    public void onClick(Player player)
                    {
                        Bukkit.dispatchCommand(player, "ban " + target.getName() + (silent.get() ? "-s" : "") + " Change your name and appeal or play in an alt");
                        player.closeInventory();
                    }
                });
                holder.setIcon(0, first);

                Inventory inv = holder.getInventory();

                player.openInventory(inv);
            }
        });

        gui.setIcon(4, skinName);

        Icon banEvasion = new Icon(ItemUtils.create(Material.EMERALD, "§eBan Evasion")).addClickAction(new GuiClickAction()
        {
            @Override
            public void onClick(Player player)
            {
                Icon firstOffense = new Icon(ItemUtils.create(Material.EMERALD_BLOCK, "&cFirst offense")).addClickAction(new GuiClickAction()
                {
                    @Override
                    public void onClick(Player player)
                    {
                        Bukkit.dispatchCommand(player, "ipban " + target.getName() + (silent.get() ? "-s" : "") + " Ban evasion");
                        player.closeInventory();
                    }
                });
            }
        });

        gui.setIcon(0, banEvasion);

        Icon exploits = new Icon(ItemUtils.create(Material.BARRIER, "§eExploits")).addClickAction(new GuiClickAction()
        {
            @Override
            public void onClick(Player player)
            {
                GuiHolder holder = new GuiHolder(9, target.getName());

                Icon items = new Icon(ItemUtils.create(Material.DIAMOND, "§cIllegal items/names")).addClickAction(new GuiClickAction()
                {
                    @Override
                    public void onClick(Player player)
                    {
                        GuiHolder holder = new GuiHolder(9, target.getName());

                        Icon first = new Icon(ItemUtils.create(Material.EMERALD_BLOCK, "§cFirst offense")).addClickAction(new GuiClickAction()
                        {
                            @Override
                            public void onClick(Player player)
                            {
                                Bukkit.dispatchCommand(player, "tempban " + target.getName() + (silent.get() ? "-s" : "") + " 30d Illegal items/names");
                                player.closeInventory();
                            }
                        });
                        holder.setIcon(0, first);

                        Icon second = new Icon(ItemUtils.create(Material.EMERALD_BLOCK, "§cSecond offense")).addClickAction(new GuiClickAction()
                        {
                            @Override
                            public void onClick(Player player)
                            {
                                Bukkit.dispatchCommand(player, "ban " + target.getName() + (silent.get() ? "-s" : "") + " Illegal items/names");
                                player.closeInventory();
                            }
                        });
                        holder.setIcon(1, second);

                        Inventory xrayInv = holder.getInventory();

                        player.openInventory(xrayInv);
                    }
                });
                holder.setIcon(0, items);

                Icon glitches = new Icon(ItemUtils.create(Material.DRAGON_EGG, "§cAbuse of glitches")).addClickAction(new GuiClickAction()
                {
                    @Override
                    public void onClick(Player player)
                    {
                        GuiHolder holder = new GuiHolder(9, target.getName());

                        Icon first = new Icon(ItemUtils.create(Material.EMERALD_BLOCK, "§cFirst offense")).addClickAction(new GuiClickAction()
                        {
                            @Override
                            public void onClick(Player player)
                            {
                                Bukkit.dispatchCommand(player, "tempban " + target.getName() + (silent.get() ? "-s" : "") + " 30d Abuse of glitches");
                                player.closeInventory();
                            }
                        });
                        holder.setIcon(0, first);

                        Icon second = new Icon(ItemUtils.create(Material.EMERALD_BLOCK, "§cSecond offense")).addClickAction(new GuiClickAction()
                        {
                            @Override
                            public void onClick(Player player)
                            {
                                Bukkit.dispatchCommand(player, "ban " + target.getName() + (silent.get() ? "-s" : "") + " Abuse of glitches");
                                player.closeInventory();
                            }
                        });
                        holder.setIcon(1, second);

                        Inventory xrayInv = holder.getInventory();

                        player.openInventory(xrayInv);
                    }
                });
                holder.setIcon(1, glitches);

                Inventory xrayInv = holder.getInventory();

                player.openInventory(xrayInv);
            }
        });

        gui.setIcon(2, exploits);

        Icon chat = new Icon(ItemUtils.create(Material.PAPER, "§eChat")).addClickAction(new GuiClickAction()
        {
            @Override
            public void onClick(Player player)
            {
                GuiHolder holder = new GuiHolder(9, target.getName());

                for (int i = 1; i <= 7; i++)
                {
                    String prefix = "th";
                    if (i == 1) prefix = "st";
                    if (i == 2) prefix = "nd";
                    if (i == 3) prefix = "rd";

                    int finalI = i;

                    holder.setIcon(i - 1, new Icon(ItemUtils.create(Material.EMERALD_BLOCK, "§c" + i + prefix + " offense")).addClickAction(new GuiClickAction()
                    {
                        @Override
                        public void onClick(Player player)
                        {
                            Bukkit.dispatchCommand(player, chatCommands.get(finalI).replace("%player%", target.getName()));
                            player.closeInventory();
                        }
                    }));
                }

                player.openInventory(holder.getInventory());
            }
        });

        gui.setIcon(1, chat);

        Icon hackedClient = new Icon(ItemUtils.create(Material.GRASS_BLOCK, "§eHacked Client")).addClickAction(new GuiClickAction()
        {
            @Override
            public void onClick(Player player)
            {
                GuiHolder cats = new GuiHolder(9, "Choose a hack | " + target.getName());
                String[] cat = new String[4];
                cat[0] = "Movement";
                cat[1] = "Combat";
                cat[2] = "World";
                cat[3] = "Other";

                for (int i = 1; i <= 4; i++)
                {
                    int finalI = i;

                    cats.setIcon(i - 1, new Icon(ItemUtils.create(Material.EMERALD_BLOCK, "§c" + cat[i - 1])).addClickAction(new GuiClickAction()
                    {
                        @Override
                        public void onClick(Player player)
                        {
                            Bukkit.dispatchCommand(player, "ban " + target.getName() + (silent.get() ? "-s" : "") + " " + cat[finalI - 1]);
                            player.closeInventory();
                        }
                    }));
                }

                player.openInventory(cats.getInventory());
            }
        });

        gui.setIcon(3, hackedClient);

        Inventory guiInventory = gui.getInventory();
        player.openInventory(guiInventory);

        GuiHolder silentGui = new GuiHolder(9, target.getName());

        Icon silentItem = new Icon(ItemUtils.create(Material.REDSTONE_BLOCK, "§cSilent")).addClickAction(player1 -> {
            player1.openInventory(guiInventory);
            silent.set(true);
        });

        Icon notSilentItem = new Icon(ItemUtils.create(Material.EMERALD_BLOCK, "§aPublic")).addClickAction(player1 -> {
            player1.openInventory(guiInventory);
            silent.set(false);
        });

        silentGui.setIcon(0, silentItem);
        silentGui.setIcon(1, notSilentItem);

        player.openInventory(silentGui.getInventory());

        return true;
    }

    @EventHandler
    public void onClick(InventoryClickEvent event)
    {
        if (event.getView().getTopInventory().getHolder() instanceof GuiHolder)
        {
            event.setCancelled(true);

            if (event.getWhoClicked() instanceof Player)
            {
                Player player = (Player) event.getWhoClicked();

                ItemStack itemStack = event.getCurrentItem();

                if (itemStack == null || itemStack.getType() == Material.AIR)
                    return;

                GuiHolder customHolder = (GuiHolder) event.getView().getTopInventory().getHolder();

                Icon icon = customHolder.getIcon(event.getRawSlot());
                if (icon == null)
                    return;

                for (GuiClickAction clickAction : icon.getClickActions())
                {
                    clickAction.onClick(player);
                }
            }
        }
    }
}

package com.efnilite.skematic.elements.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.aliases.ItemType;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import com.efnilite.skematic.util.FaweUtil;
import com.sk89q.worldedit.extent.clipboard.BlockArrayClipboard;
import com.sk89q.worldedit.regions.CuboidRegion;
import com.sk89q.worldedit.regions.Region;
import org.bukkit.event.Event;

@Name("Fawe - Fill Region")
@Description("Fill a region (selection) with a block.")
@Since("2.1")
public class EffCuboidFill extends Effect {

    private Expression<CuboidRegion> cuboid;
    private Expression<ItemType> blocks;

    static {
        Skript.registerEffect(EffCuboidFill.class, "set [(all [[of] the]|the)] fawe blocks in %fawe cuboidregions% to %itemtypes%");
    }

    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {

        cuboid = (Expression<CuboidRegion>) expressions[0];
        blocks = (Expression<ItemType>) expressions[1];

        return true;
    }

    @Override
    protected void execute(Event e) {
        CuboidRegion cuboid = this.cuboid.getSingle(e);
        ItemType[] blocks = this.blocks.getArray(e);

        if (cuboid == null || blocks == null || cuboid.getWorld() == null) {
            return;
        }

        new BlockArrayClipboard(cuboid).setBlocks((Region) cuboid, FaweUtil.parsePattern(blocks));
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "fill " + cuboid.toString(e, debug) + " with " + blocks.toString(e, debug);
    }
}

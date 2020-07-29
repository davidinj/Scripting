package tutorial.tasks;

import org.powerbot.script.Area;
import org.powerbot.script.Condition;
import org.powerbot.script.Filter;
import org.powerbot.script.Locatable;
import org.powerbot.script.Tile;
import org.powerbot.script.rt4.BasicQuery;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.Constants;
import org.powerbot.script.rt4.Npc;

public class Fight extends Task {

    int[] cowIds = {2793, 2791, 2790};

    Area cowArea = new Area(

            new Tile(3242, 3298, 0),

            new Tile(3246, 3279, 0),

            new Tile(3253, 3278, 0),

            new Tile(3253, 3255, 0),

            new Tile(3265, 3255, 0),

            new Tile(3265, 3296, 0)

    );

    public Fight(ClientContext ctx) {
        super(ctx);
    }

    @Override
    public boolean activate() {

        double healthPercent = (double)ctx.skills.level(Constants.SKILLS_HITPOINTS)
                / (double)ctx.skills.realLevel(Constants.SKILLS_HITPOINTS);

        return
                (ctx.players.local().interacting().valid() && ctx.players.local().interacting().healthPercent() == 0)
                        ||
                        (!ctx.players.local().healthBarVisible()
                                && healthPercent >= 0.35
                                && !ctx.players.local().interacting().valid());
    }

    @Override
    public void execute() {
        System.out.println("Executing fighting");
        Filter<Npc> filter = npc -> !npc.healthBarVisible();

        Npc cow = ctx.npcs.select().within(cowArea).id(cowIds).nearest().select(filter).poll();

        if (cow.inViewport()) {
            cow.interact("Attack");
            Condition.sleep(2000);
        } else {
            ctx.camera.turnTo(cow);
        }
    }
}

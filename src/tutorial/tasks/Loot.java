package tutorial.tasks;

import org.powerbot.script.Condition;
import org.powerbot.script.Tile;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.GroundItem;

import java.util.concurrent.Callable;

public class Loot extends Task {

    int featherId = 314;
    Tile cow_tile = Tile.NIL;

    public Loot(ClientContext ctx) {
        super(ctx);
    }

    @Override
    public boolean activate() {
        if (ctx.players.local().interacting().valid()) {
            cow_tile = ctx.players.local().interacting().tile();
        }
        return !ctx.groundItems.select().at(cow_tile).id(featherId).isEmpty();
    }

    @Override
    public void execute() {
        System.out.println("Executing looting");
        GroundItem feathers = ctx.groundItems.select().at(cow_tile).id(featherId).poll();

        if (feathers.inViewport()) {
            feathers.interact("Take", feathers.name());
            Callable<Boolean> booleanCallable = new Callable<Boolean>() {
                @Override
                public Boolean call() throws Exception {
                    return !feathers.valid();
                }
            };
            Condition.wait(booleanCallable, 300, 10);
        } else {
            ctx.camera.turnTo(feathers);
        }
    }
}

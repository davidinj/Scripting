package tutorial;

import org.powerbot.script.PaintListener;
import org.powerbot.script.PollingScript;
import org.powerbot.script.Script;
import org.powerbot.script.rt4.ClientContext;
import tutorial.tasks.Fight;
import tutorial.tasks.Loot;
import tutorial.tasks.Task;

import java.awt.*;
import java.util.ArrayList;

@Script.Manifest(name = "HelloWorld", description = "Tutorial")

public class CowKiller extends PollingScript<ClientContext> implements PaintListener {

    ArrayList<Task> tasks = new ArrayList<>();

    @Override
    public void poll() {
        for (Task task : tasks) {
            if (task.activate()) {
                task.execute();
            }
        }
    }

    @Override
    public void start() {
        System.out.println("Hello World");
        Fight fight = new Fight(ctx);
        Loot loot = new Loot(ctx);
        tasks.add(fight);
        tasks.add(loot);
    }

    @Override
    public void repaint(Graphics graphics) {
        graphics.setColor(new Color(0, 0, 0, 180));
        graphics.fillRect(0, 0, 150, 100);

        graphics.setColor(new Color(255, 255, 255));
        graphics.drawRect(0, 0, 150, 100);

        graphics.drawString("Cow Killer", 20, 20);
        graphics.drawString("Experience gained:", 0, 40);

    }
}

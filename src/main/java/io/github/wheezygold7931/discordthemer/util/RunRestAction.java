package io.github.wheezygold7931.discordthemer.util;

import io.github.wheezygold7931.discordthemer.ActionMode;
import net.dv8tion.jda.core.requests.RestAction;

public class RunRestAction {

    public RunRestAction(RestAction restAction, ActionMode actionMode) {
        switch (actionMode) {
            case QUEUE: {
                restAction.queue();
                break;
            }
            case BLOCKING: {
                restAction.complete();
                break;
            }
            default:
                break;
        }
    }
}

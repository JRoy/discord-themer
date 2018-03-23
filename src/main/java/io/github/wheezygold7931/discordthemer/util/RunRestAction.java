package io.github.wheezygold7931.discordthemer.util;

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
                //I guess we default to queue /shrug
                restAction.queue();
                break;
        }
    }
}

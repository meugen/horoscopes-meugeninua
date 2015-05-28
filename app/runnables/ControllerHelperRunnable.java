package runnables;

import helpers.controllers.ControllerHelper;

/**
 * Runnable for execute controller helper.
 *
 * @author meugen
 */
public final class ControllerHelperRunnable implements Runnable {

    private final ControllerHelper controllerHelper;

    /**
     * Constructor.
     *
     * @param controllerHelper Controller helper
     */
    public ControllerHelperRunnable(final ControllerHelper controllerHelper) {
        this.controllerHelper = controllerHelper;
    }

    /**
     * {@inheritDoc}
     */
    public void run() {
        this.controllerHelper.execute();
    }
}

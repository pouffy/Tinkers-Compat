package io.github.pouffy.tcompat.common.capability.living.status;

import io.github.pouffy.tcompat.common.capability.living.status.types.VoidTouchedStatus;

public class LivingStatusHooks {

    /**
     * Incoming damage multiplied by 0.05 x the amplifier is added on top of incoming damage.
     * @see VoidTouchedStatus
     */
    public static final String VOID_TOUCHED = "void_touched";
    /**
     * Entity takes freezing damage. Incoming cold damage is increased.
     * #@see CryogenicStatus
     */
    public static final String CRYOGENIC = "cryogenic";
}

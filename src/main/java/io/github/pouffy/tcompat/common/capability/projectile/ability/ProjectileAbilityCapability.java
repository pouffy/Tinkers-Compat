package io.github.pouffy.tcompat.common.capability.projectile.ability;

import io.github.pouffy.tcompat.common.capability.projectile.ability.types.*;
import io.github.pouffy.tcompat.common.network.ProjectileAbilitySyncPacket;
import io.github.pouffy.tcompat.common.network.TCompatNetworking;
import io.github.pouffy.tcompat.common.network.base.BasePacket;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraftforge.network.simple.SimpleChannel;
import org.apache.commons.lang3.tuple.Triple;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class ProjectileAbilityCapability implements ProjectileAbility {

    private final Projectile projectile;

    public final Map<String, IProjectileAbility> abilities;

    public ProjectileAbilityCapability(Projectile projectile) {
        this.projectile = projectile;
        this.abilities = Map.ofEntries(
                IProjectileAbility.makeEntry(new PhoenixTouchedAbility()),
                IProjectileAbility.makeEntry(new LeechingAbility()),
                IProjectileAbility.makeEntry(new AmphithereAbility()),
                IProjectileAbility.makeEntry(new StymphalianAbility()),
                IProjectileAbility.makeEntry(new VoidScatterAbility())
        );
    }

    @Override
    public Projectile projectile() {
        return this.projectile;
    }

    @Override
    public Map<String, IProjectileAbility> getAbilities() {
        return abilities;
    }

    @Override
    public void sendClient(IProjectileAbility ability) {
        if (ability == null) return;
        this.setSynched(Direction.CLIENT, ability.getSetKey(), ability.isActive());
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        this.getAbilities().forEach((name, ability) -> tag.put(name, ability.serializeTag()));
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag tag) {
        this.getAbilities().forEach((name, ability) -> ability.deserializeTag(tag.getCompound(name)));
    }

    @Override
    public Map<String, Triple<Type, Consumer<Object>, Supplier<Object>>> getSynchableFunctions() {
        Map<String, Triple<Type, Consumer<Object>, Supplier<Object>>> functions = new HashMap<>();
        for (var ability : this.getAbilities().entrySet()) {
            if (ability.getValue() == null) continue;
            functions.put(ability.getValue().getSetKey(), Triple.of(Type.BOOLEAN, (object) -> this.getAbility(ability.getKey()).setActive((boolean) object), () -> this.getAbility(ability.getKey()).isActive()));
        }
        return functions;
    }

    @Override
    public BasePacket getSyncPacket(String key, Type type, Object value) {
        return new ProjectileAbilitySyncPacket(this.projectile().getId(), key, type, value);
    }

    @Override
    public SimpleChannel getPacketChannel() {
        return TCompatNetworking.INSTANCE;
    }
}

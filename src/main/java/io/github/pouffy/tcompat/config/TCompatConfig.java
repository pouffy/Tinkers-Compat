package io.github.pouffy.tcompat.config;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;
import org.apache.commons.lang3.tuple.Pair;

public class TCompatConfig {

    public static final ForgeConfigSpec SERVER_SPEC;
    public static final ServerConfig SERVER;
    public static final ForgeConfigSpec CLIENT_SPEC;
    public static final ClientConfig CLIENT;

    private TCompatConfig() {}

    static {
        Pair<ServerConfig, ForgeConfigSpec> serverPair = new ForgeConfigSpec.Builder().configure(ServerConfig::new);
        SERVER = serverPair.getLeft();
        SERVER_SPEC = serverPair.getRight();

        Pair<ClientConfig, ForgeConfigSpec> clientPair = new ForgeConfigSpec.Builder().configure(ClientConfig::new);
        CLIENT = clientPair.getLeft();
        CLIENT_SPEC = clientPair.getRight();
    }

    public static void register(ModLoadingContext modLoadingContext) {
        modLoadingContext.registerConfig(ModConfig.Type.SERVER, TCompatConfig.SERVER_SPEC, String.format("%s-server.toml", "tcompat"));
        modLoadingContext.registerConfig(ModConfig.Type.CLIENT, TCompatConfig.CLIENT_SPEC, String.format("%s-client.toml", "tcompat"));
    }
}

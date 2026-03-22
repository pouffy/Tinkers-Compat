package io.github.pouffy.tcompat.datagen.tag;

import io.github.pouffy.tcompat.TCompat;
import io.github.pouffy.tcompat.common.data.TCTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;
import slimeknights.tconstruct.common.TinkerTags;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.concurrent.CompletableFuture;

import static io.github.pouffy.tcompat.TCompat.getResource;

@SuppressWarnings("unused")
@ParametersAreNonnullByDefault
public class TCBlockTagProv extends BlockTagsProvider {
    public TCBlockTagProv(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, TCompat.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        addPlanks();
    }

    private void addPlanks() {
        this.tag(TCTags.Blocks.VOID_TOUCHED_EFFICIENT)
                .addOptionalTag(TCTags.Blocks.END_STONE)
                .addOptionalTag(TinkerTags.Blocks.ENDERBARK_ROOTS)
                .addOptionalTag(TinkerTags.Blocks.ENDER_SLIME_SPAWN)
                .addOptionalTag(getResource("tconstruct:enderbark_logs"))
                .addOptionalTag(getResource("c:end_stones"))
                .addOptional(getResource("biomesoplenty:unmapped_end_stone"))
                .addOptional(getResource("biomesoplenty:null_end_stone"))
                .addOptional(getResource("biomesoplenty:algal_end_stone"));

        this.tag(TCTags.Blocks.OBSIDIAN_BREAKER_EFFICIENT)
                .addOptionalTag(getResource("c:nether_stones"))
                .addOptionalTag(getResource("c:nether_ores"))
                .addOptionalTag(getResource("c:nether_pframe"))
                .addOptionalTag(getResource("c:is_obsidian"))
                .addOptional(getResource("minecraft:obsidian"))
                .addOptional(getResource("minecraft:crying_obsidian"))
                .addOptional(getResource("betternether:blue_crying_obsidian"))
                .addOptional(getResource("betternether:weeping_obsidian"))
                .addOptional(getResource("betternether:blue_weeping_obsidian"));
    }

    private static TagKey<Block> blockTag(String name) {
        return TagKey.create(Registries.BLOCK, getResource(name));
    }
}

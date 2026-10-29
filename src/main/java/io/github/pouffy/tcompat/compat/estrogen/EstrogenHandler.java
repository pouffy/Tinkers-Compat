package io.github.pouffy.tcompat.compat.estrogen;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.ListBuilder;
import io.github.pouffy.tcompat.common.modifier.TCModifiers;
import io.github.pouffy.tcompat.common.util.EquipmentHelper;
import io.github.pouffy.tcompat.common.util.ObjectRetriever;
import lombok.val;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Style;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.FormattedCharSink;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.regex.Pattern;

public class EstrogenHandler {

    @Mod.EventBusSubscriber(Dist.CLIENT)
    public static class TextRendererFeatures {
        private static boolean uwufyInternal = false;

        public static boolean getUwufy() {
            return uwufyInternal;
        }

        public void setUwufy(boolean uwufy) {
            uwufyInternal = uwufy;
        }

        private boolean dirty = false;

        @SubscribeEvent
        void onTick(TickEvent.ClientTickEvent event) {
            if (event.phase == TickEvent.Phase.END) {
                var client = Minecraft.getInstance();
                if (client.player == null) {
                    disconnect();
                } else {
                    var colonThree = ObjectRetriever.getItem("estrogen:colon_three");
                    boolean skipModifier = false;
                    if (colonThree.isPresent()) {
                        Inventory inventory = client.player.getInventory();
                        skipModifier = !inventory.items.stream().filter(item -> item.is(colonThree.get())).toList().isEmpty();
                    }
                    if (!skipModifier) {
                        setUwufy(EquipmentHelper.getEffectiveLevel(client.player, TCModifiers.colonThree) > 0);
                    }
                }
                if (dirty) {
                    dirty = false;
                    client.updateTitle();
                }
            }
        }

        void disconnect() {
            setUwufy(false);
        }
    }

    public static class UwUfy {

        public static String[] PHRASES = new String[]{
                "UwU",
                "owo",
                "OwO",
                "uwu",
                ">w<",
                "^w^",
                ":3",
                "^-^",
                "^_^",
                "^w^",
                ":3"
        };

        public static String uwufy(String input) {
            int stringLength = input.length();

            input = uwufyText(input);

            if (stringLength % 3 == 0) {
                input = input.toUpperCase(Locale.ROOT);
            }
            input = stringLength % 2 == 0 ? modifyLasts(input) : modifyFirsts(input);

            return input + " " + PHRASES[stringLength % PHRASES.length];
        }

        public static String uwufyText(String text) {
            String regexed = text.replaceAll("[rl]", "w").replaceAll("[RL]", "W")
                    .replace("ove", "uv").replace("OVE", "UV")
                    .replace("o", "owo").replace("O", "OwO")
                    .replace("!", "!!!").replaceAll("\\?", "???");

            regexed = Pattern.compile("%(\\p{L})").matcher(regexed).replaceAll(m -> "%" + m.group(1).toLowerCase());
            regexed = Pattern.compile("\\$(\\p{L})").matcher(regexed).replaceAll(m -> "\\$" + m.group(1).toLowerCase());

            return regexed;
        }

        public static String modifyLasts(String text) {
            return text.replaceAll("(\\p{L})(\\b)", "$1$1$1$1$2");
        }

        public static String modifyFirsts(String text) {
            return text.replaceAll("\\b(\\p{L})(\\p{L}*)\\b", "$1-$1$2");
        }
    }

    public static class UwUOrderedText implements FormattedCharSequence {
        private final FormattedCharSequence wrapped;
        private final List<Pair<Style, String>> uwufiedStrings;

        public UwUOrderedText(FormattedCharSequence wrapped) {
            this.wrapped = wrapped;
            this.uwufiedStrings = collectAndUwufy(wrapped);
        }

        private static List<Pair<Style, String>> collectAndUwufy(FormattedCharSequence wrapped) {
            List<Pair<Style, String>> strings = new ArrayList<>();
            Style[] currentStyle = new Style[1];
            StringBuilder[] currentText = new StringBuilder[1];
            boolean[] hasCurrent = {false};

            wrapped.accept((index, style, codePoint) -> {
                if (!hasCurrent[0]) {
                    currentStyle[0] = style;
                    currentText[0] = new StringBuilder();
                    hasCurrent[0] = true;
                }

                if (!Objects.equals(currentStyle[0], style)) {
                    strings.add(Pair.of(currentStyle[0], currentText[0].toString()));
                    currentStyle[0] = style;
                    currentText[0] = new StringBuilder();
                }

                currentText[0].appendCodePoint(codePoint);
                return true;
            });

            if (hasCurrent[0]) {
                strings.add(Pair.of(currentStyle[0], currentText[0].toString()));
            }

            return uwufy(strings);
        }

        private static List<Pair<Style, String>> uwufy(List<Pair<Style, String>> strings) {
            int length = strings.stream().mapToInt(pair -> pair.getSecond().length()).sum();
            List<Pair<Style, String>> result = new ArrayList<>(strings.size());

            for (Pair<Style, String> pair : strings) {
                String text = pair.getSecond();
                if (Style.DEFAULT_FONT.equals(pair.getFirst().getFont())) {
                    text = UwUfy.uwufyText(text);
                    if (length % 3 == 0) {
                        text = text.toUpperCase(Locale.ROOT);
                    }
                    text = length % 2 == 0
                            ? UwUfy.modifyLasts(text)
                            : UwUfy.modifyFirsts(text);
                }
                result.add(Pair.of(pair.getFirst(), text));
            }

            if (!result.isEmpty()) {
                int lastIndex = result.size() - 1;
                Pair<Style, String> last = result.get(lastIndex);
                if (Style.DEFAULT_FONT.equals(last.getFirst().getFont())) {
                    result.set(lastIndex, Pair.of(
                            last.getFirst(),
                            last.getSecond() + " " + UwUfy.PHRASES[length % UwUfy.PHRASES.length]
                    ));
                }
            }

            return result;
        }

        @Override
        public boolean accept(FormattedCharSink visitor) {
            int index = 0;
            for (Pair<Style, String> pair : uwufiedStrings) {
                String text = pair.getSecond();
                for (int i = 0; i < text.length(); i++) {
                    if (!visitor.accept(++index, pair.getFirst(), text.charAt(i))) {
                        return false;
                    }
                }
            }
            return true;
        }

        public static UwUOrderedText orderedToUwUText(FormattedCharSequence sequence) {
            return new UwUOrderedText(sequence);
        }

    }
}

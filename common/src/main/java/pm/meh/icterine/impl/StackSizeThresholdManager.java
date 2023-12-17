package pm.meh.icterine.impl;

import net.minecraft.world.item.ItemStack;
import pm.meh.icterine.iface.IItemStackMixin;
import pm.meh.icterine.util.LogHelper;

import java.util.SortedSet;
import java.util.TreeSet;

public class StackSizeThresholdManager {

    private static final SortedSet<Integer> stackSizeThresholds = new TreeSet<>();

    public static void clear() {
        stackSizeThresholds.clear();
        stackSizeThresholds.add(1);
    }

    public static void add(int value) {
        stackSizeThresholds.add(value);
    }

    public static boolean doesStackPassThreshold(ItemStack stack) {
        int prevValue = ((IItemStackMixin) (Object) stack).icterine$getPreviousStackSize();
        int newValue = stack.getCount();

        // We already check this at InventoryChangeTriggerMixin
//        if (newValue < prevValue) {
//            return false;
//        }

        for (int thr : stackSizeThresholds) {
            if (prevValue < thr && newValue >= thr) {
                return true;
            }
        }

        return false;
    }

    public static void debugPrint() {
        LogHelper.debug("InventoryChangeTrigger stack size thresholds: " + stackSizeThresholds);
    }
}
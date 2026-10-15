package com.krypton.client.feature;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.math.BlockPos;
import java.util.*;

/** Scans only blocks in chunks already loaded by the local client. */
public final class AmethystScanner {
    public record Finding(BlockPos pos, String kind, int maturity, long foundAt) {}
    private static final LinkedHashMap<String, Finding> FINDINGS = new LinkedHashMap<>();
    private static long lastScan;
    private AmethystScanner() {}
    public static void tick(MinecraftClient mc) {
        if (mc.world == null || mc.player == null || System.currentTimeMillis() - lastScan < 2500) return;
        lastScan = System.currentTimeMillis();
        BlockPos center = mc.player.getBlockPos();
        int r = 16;
        for (int x = -r; x <= r; x++) for (int y = -12; y <= 12; y++) for (int z = -r; z <= r; z++) {
            BlockPos p = center.add(x, y, z);
            // Avoid querying unloaded chunks: this feature never requests hidden chunk data.
            if (!mc.world.isChunkLoaded(p.getX() >> 4, p.getZ() >> 4)) continue;
            BlockState s = mc.world.getBlockState(p);
            String kind = null; int maturity = 0;
            if (s.isOf(Blocks.AMETHYST_CLUSTER)) { kind = "Cluster"; maturity = 4; }
            else if (s.isOf(Blocks.LARGE_AMETHYST_BUD)) { kind = "Large bud"; maturity = 3; }
            else if (s.isOf(Blocks.MEDIUM_AMETHYST_BUD)) { kind = "Medium bud"; maturity = 2; }
            else if (s.isOf(Blocks.SMALL_AMETHYST_BUD)) { kind = "Small bud"; maturity = 1; }
            if (kind != null) {
                String key = p.getX()+","+p.getY()+","+p.getZ();
                FINDINGS.put(key, new Finding(p.toImmutable(), kind, maturity, System.currentTimeMillis()));
            }
        }
        while (FINDINGS.size() > 500) FINDINGS.remove(FINDINGS.keySet().iterator().next());
    }
    public static List<Finding> findings() { return List.copyOf(FINDINGS.values()); }
    public static void clear() { FINDINGS.clear(); }
}

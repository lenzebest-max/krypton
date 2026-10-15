package com.krypton.client.module;

import java.util.*;

/** Central module catalog. Add modules here; the ClickGUI discovers them automatically. */
public final class ModuleRegistry {
    private static final List<Module> MODULES = new ArrayList<>();
    private ModuleRegistry() {}
    private static void add(String cat, String names) {
        for (String item : names.split("\\|")) {
            String[] parts = item.split("~", 2);
            MODULES.add(new Module(cat, parts[0].trim(), parts.length > 1 ? parts[1].trim() : "Client-side toggle"));
        }
    }
    public static void init() {
        if (!MODULES.isEmpty()) return;
        add("Combat", "Aim Assist~Practice-only target tracking|Trigger Bot~Practice-world click helper placeholder|Auto Clicker~Configurable click helper placeholder|Auto Totem~Inventory reminder|Auto Armor~Armor management helper|Auto Crystal~Practice-only module placeholder|Crystal Optimizer~Practice-only module placeholder|Anchor Macro~Macro preset|Shield Breaker~Combat status helper|Hit Effects~Local hit effects toggle|Target HUD~Target display toggle|Elytra Swap~Inventory helper|Mace Swap~Inventory helper|Auto Weapon~Weapon reminder|Auto Potion~Potion reminder|Auto Eat~Food reminder|Jump Reset~Practice helper|Pop Counter~Totem pop counter placeholder|Reach Display~Local distance display|Combat Notifications~Combat notification toggle");
        add("Movement", "Sprint~Sprint helper toggle|Sneak~Sneak helper toggle|Freecam~Local camera feature placeholder|Free Look~Camera look toggle|Zoom~Zoom toggle|Elytra Helper~Elytra helper toggle|Auto Firework~Firework reminder|Step~Movement experiment placeholder|Safe Walk~Edge safety helper placeholder|Parkour~Movement helper|Speed Display~Movement stats|Jump Circles~Visual effect toggle|Pearl Helper~Pearl trajectory helper placeholder|Movement HUD~Movement HUD toggle|Flight Lab~Single-player experiment placeholder");
        add("Render", "Player ESP~Visibility visualization placeholder|Mob ESP~Entity visualization placeholder|Storage ESP~Visible storage highlighting placeholder|Block ESP~Selected block highlighting placeholder|Ore Highlights~Visible ore highlighting placeholder|Fullbright~Brightness helper toggle|Tracers~Visible entity lines placeholder|Name Tags~Name tag display toggle|Hole ESP~Local terrain visualization placeholder|Tunnel ESP~Local terrain visualization placeholder|Spawner ESP~Visible spawner highlighting placeholder|Projectile Trails~Projectile trail toggle|Pearl Trajectory~Trajectory preview placeholder|Chest Highlights~Visible chest highlighting placeholder|Entity Radar~Nearby visible entity list placeholder");
        add("Donut SMP", "Amethyst Finder~Scan currently loaded nearby blocks for amethyst growth|Cluster Maturity Scanner~Record visible mature clusters|Chunk Visit Estimator~Heuristic only; never proof of player presence|Stash Notes~Local notes placeholder|Base Waypoints~Local waypoint notes|Chunk History~Local scan history|Player Encounter Log~Local encounter notes|Spawner Tracker~Visible spawner notes|Netherite Finder~Visible block helper placeholder|Resource Tracker~Resource notes|Auto Sell~Trading helper placeholder|Auto Loot~Loot helper placeholder|Auto Mine~Mining helper placeholder|Auto Reconnect~Reconnect preference placeholder|Anti Trap Alerts~Local hazard alerts placeholder");
        add("Auction", "AH Sniper~Price alert and manual confirmation only|Auto Buy~Disabled placeholder; no unattended purchases|Auto Sell~Disabled placeholder; no unattended listings|Undercut Calculator~Calculate listing prices|Price History~Local manually recorded prices|Profit Calculator~Net resale calculations|Market Scanner~Manual listing analysis|Arbitrage Finder~Compare recorded market prices|Investment Watchlist~Watch item prices|Portfolio Tracker~Track manually entered holdings|Price Alerts~Local threshold alerts|Resale Simulator~Estimate fees and margins");
        add("Player", "Inventory HUD~Inventory display toggle|Durability HUD~Durability display toggle|Totem Counter~Inventory counter placeholder|Armor Status~Armor status toggle|Auto Tool~Tool reminder|Inventory Organizer~Organization helper placeholder|Item Value Display~Manual item value labels|Shulker Preview~Container preview placeholder");
        add("HUD", "ClickGUI~Open module menu|Module Search~Search modules|Keybind Manager~Keybind settings placeholder|Array List~Enabled module list toggle|HUD Editor~HUD layout placeholder|Coordinates~Coordinate display toggle|FPS Counter~FPS display toggle|CPS Counter~CPS display placeholder|Ping Display~Ping display toggle|Keystrokes~Keystrokes display placeholder|Custom Crosshair~Crosshair customization placeholder|Theme Manager~Dark purple theme|Config Profiles~Profile management placeholder|Chat Macros~Saved text snippets placeholder|Notifications~Client notification toggle");
        add("Utility", "Screenshot Notes~Record discovery notes|Session Timer~Session timing toggle|Playtime Tracker~Session tracking toggle|Biome Display~Current biome helper|Direction HUD~Facing direction toggle|Compass~Direction helper|Clock~World time helper|Weather HUD~Weather display toggle|Death Coordinates~Remember local death location|Waypoint Manager~Manage local waypoints");
    }
    public static List<Module> all() { return Collections.unmodifiableList(MODULES); }
    public static List<Module> category(String category) { return MODULES.stream().filter(m -> m.category().equals(category)).toList(); }
    public static List<String> categories() { return MODULES.stream().map(Module::category).distinct().toList(); }
    public static Module find(String name) { return MODULES.stream().filter(m -> m.name().equalsIgnoreCase(name)).findFirst().orElse(null); }
}

# Krypton Client — expandable Fabric client framework

A modular, client-side Minecraft project with an original compact charcoal/purple ClickGUI inspired by the general style of popular clients. It is not affiliated with or a copy of Krypton.

## Current working pieces
- Right Shift opens the ClickGUI; Escape or Right Shift closes it.
- Search, category filtering, scrollable module lists, and persistent-in-session on/off state.
- 100 module entries in seven categories, registered from `ModuleRegistry.java`.
- Amethyst Finder scans blocks only in chunks already loaded by your client, every 2.5 seconds, within 16 horizontal blocks and 12 vertical blocks. It records visible amethyst buds/clusters and shows a count in the GUI. It does **not** reveal unloaded chunks or prove that a player loaded a chunk.
- GitHub Actions workflow builds the mod JAR and uploads it as an artifact.

## Important status
This is a usable expandable **starter client**, not a finished implementation of every listed module. Modules that are not specifically implemented currently serve as toggleable framework entries/placeholders. Auction modules are analysis/planning entries; there is no unattended auction buying, selling, staff evasion, or anti-ban bypass. The client cannot guarantee compatibility with a public server's rules.

## Build
Use the Java version and Fabric Loom version configured in `gradle.properties`, then run:

```sh
gradle --no-daemon clean build
```

Or push the repository to GitHub and run **Actions → Build Krypton Client**. Download the `krypton-client-jar` artifact.

## Expanding it
1. Add a module entry to `ModuleRegistry.init()` in the appropriate category.
2. Add actual behavior in a separate feature class.
3. Call that feature from client tick/render events only when its module is enabled.
4. Keep server-visible automation and game-version-specific code isolated so it can be tested and disabled independently.

## Version note
The project is configured for Minecraft 26.2 / Fabric as requested. Fabric's 26.2 toolchain and mapping APIs may change; the first CI build is the compatibility check. If upstream dependency coordinates are not yet available, update `gradle.properties` to a released Fabric-supported version before building.

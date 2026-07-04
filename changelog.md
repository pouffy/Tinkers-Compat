# Tinkers' Compatibility Changelog

## 2.0.2

### Changes
- Updated Credits.
- Updated dependencies.
- Fixed L_Ender's Cataclysm recipe conditions.
- Fixed incorrectly spelled or misformatted item ids in some recipes.
- Fixed a locale issue related to putting wood or rock names into lowercase for tags.
- Slime in a Bucket variants now only register if Quark's Slime in a Bucket module is enabled.
- Renamed Divinite to Angilite.
- Renamed LightningOwner's folder to lightning.
- `salvageAll` can now exclude certain salvage types.

### Additions
- Added a proper handler for Aether methods.
- Added Client-Only handlers for methods that are only for the client.
- Wood tags now support vertical planks from Quark (& Every Compat).
- Added translations for all item & fluid tags.

### Removals
- Removed `tcompat:vampire_healing` capability as it can now be managed through the handler.

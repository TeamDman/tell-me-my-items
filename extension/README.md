# tell-me-my-items README

Intellisense for Minecraft items via a json file.

Use the minecraft mod to generate the json:  
https://www.curseforge.com/minecraft/mc-mods/tell-me-my-items

## Features

![example](https://i.imgur.com/nNdOes9.gif)

- Autosuggest
- Command: reload from json
- Command: disable suggestions

## Requirements

You must use the MC mod to generate a json file, there are none provided by default.
Additionally, suggestions are not loaded by default. Use the reload command to load manualy, or turn on the auto load preference.

## Extension Settings

- `tmmi.path` - path to json file to load
- `tmmi.autoLoad` - load suggestions automatically, default: false

## Known Issues

It's always suggesting.

## Release Notes

### 0.0.1

Initial release, woot!

### 0.0.2

Added new preference to control auto loading, disabled by default.

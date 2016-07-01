Cordova Plugin: Device Rotation
===============================

This is the first version of an Android only (for now) Cordova plugin that gives developers access
to the device rotation in a very simple way.

There are no tests and the plugin has not been used in production. Feel free to submit PR:s, but if
they are large please submit an issue first to discuss your work.

This plugin has two simple interfaces, `onOrientationChange` and `getOrientation`.
Both take a callback, which is called with the current orientation. The possible orientations are
`"up"`, `"down"` and `"unknown"`.

## `onOrientationChange`

Registers a callback which is called each time the orientation changes.

```
CordovaPluginDeviceRotation.onOrientationChange(newOrientation => {
  console.log("New orientation", newOrientation);
});
```

## `getOrientation`

Get the current orientation. The callback is only called once.

```
CordovaPluginDeviceRotation.getOrientation(currentOrientation => {
  console.log("New orientation", newOrientation);
});
```
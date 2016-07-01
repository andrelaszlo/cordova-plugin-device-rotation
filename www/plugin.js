
var exec = require('cordova/exec');

var PLUGIN_NAME = 'CordovaPluginDeviceRotation';

var CordovaPluginDeviceRotation = {
  getOrientation: function(cb) {
    exec(cb, null, PLUGIN_NAME, 'getOrientation', []);
  },
  onOrientationChange: function(cb) {
    exec(cb, null, PLUGIN_NAME, 'onOrientationChange', []);
  }
};

module.exports = CordovaPluginDeviceRotation;

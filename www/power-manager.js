
var exec = require('cordova/exec');

module.exports = {

    acquirePartial: function() {
        exec(null, null, "PowerManager", "acquire", []);
    },
    releasePartial: function() {
        exec(null, null, "PowerManager", "release", []);
    }
};

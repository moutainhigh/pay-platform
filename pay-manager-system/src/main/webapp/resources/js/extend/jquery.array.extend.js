/**
 * User: zjt
 * DateTime: 2016/11/13 15:08
 *
 * 对数组的扩展
 */
$(function () {

    Array.prototype.indexOf = function(val) {
        for (var i = 0; i < this.length; i++) {
            if (this[i] == val) return i;
        }
        return -1;
    };

    Array.prototype.removeByValue = function(val) {
        var index = this.indexOf(val);
        if (index > -1) {
            this.splice(index, 1);
        }
    };

    Array.prototype.remove = function(index) {
        if (index > -1) {
            this.splice(index, 1);
        }
    };

    Array.prototype.contains = function (obj) {
        var i = this.length;
        while (i--) {
            if (this[i] === obj) {
                return true;
            }
        }
        return false;
    }

});
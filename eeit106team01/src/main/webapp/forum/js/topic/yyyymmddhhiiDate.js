Date.prototype.yyyymmddhhii = function() {
    let mm = this.getMonth() + 1; // getMonth() is zero-based
    let dd = this.getDate();
    let hh = this.getHours();
    let ii = this.getMinutes();

    return [this.getFullYear() + "-",
            (mm>9 ? '' : '0') + mm + "-",
            (dd>9 ? '' : '0') + dd + " ",
            (hh>9 ? '' : '0') + hh + ":",
            (ii>9 ? '' : '0') + ii
            ].join('');
};
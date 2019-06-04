function getUrlParameters() {
    let parameters = [];
    let hash;
    let hashes = window.location.href.slice(window.location.href.indexOf('?') + 1).split('&');
    for (let i = 0; i < hashes.length; i++) {
        hash = hashes[i].split('=');
        parameters.push(hash[0]);
        parameters[hash[0]] = hash[1];
    }
    return parameters;
}
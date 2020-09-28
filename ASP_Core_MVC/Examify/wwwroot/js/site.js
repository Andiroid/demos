// Please see documentation at https://docs.microsoft.com/aspnet/core/client-side/bundling-and-minification
// for details on configuring this project to bundle and minify static web assets.

// Write your JavaScript code.
var _0xb07e = ["\x73\x75\x62\x73\x74\x72\x69\x6E\x67", "\x2E", "\x63\x68\x61\x72\x41\x74", "\x67\x65\x74\x45\x6C\x65\x6D\x65\x6E\x74\x73\x42\x79\x43\x6C\x61\x73\x73\x4E\x61\x6D\x65", "\x67\x65\x74\x45\x6C\x65\x6D\x65\x6E\x74\x42\x79\x49\x64", "\x2F", "\x6C\x61\x73\x74\x49\x6E\x64\x65\x78\x4F\x66", "\x68\x72\x65\x66", "\x6C\x6F\x63\x61\x74\x69\x6F\x6E", "\x73\x75\x62\x73\x74\x72"]; function __$(_0xed42x2) { var _0xed42x3 = _0xed42x2[_0xb07e[0]](1); return $ = _0xb07e[1] === _0xed42x2[_0xb07e[2]](0) ? document[_0xb07e[3]](_0xed42x3) : document[_0xb07e[4]](_0xed42x3) } function __file() { return $ = window[_0xb07e[8]][_0xb07e[7]][_0xb07e[9]](window[_0xb07e[8]][_0xb07e[7]][_0xb07e[6]](_0xb07e[5]) + 1) }

nav = () => {
    __$("#nav").className = __$("#nav").className === "topnav" ? "topnav responsive" : "topnav";
}

scrollUp = () => {
    let scrollLocation = document.documentElement.scrollTop || document.body.scrollTop;
    if (scrollLocation > 0) {
        window.requestAnimationFrame(scrollUp);
        window.scrollTo(0, scrollLocation - (scrollLocation / 5));
    }
}
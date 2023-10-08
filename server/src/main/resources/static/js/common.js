//获取项目根路径
function getRootPath() {
    let pathName = window.document.location.pathname;
    return pathName.substring(0, pathName.substr(1).indexOf('/') + 1);
}
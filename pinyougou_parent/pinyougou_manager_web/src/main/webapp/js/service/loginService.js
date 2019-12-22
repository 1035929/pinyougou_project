/*
登录服务层
 */
app.service('loginService',function ($http) {
    this.loginShowName=function () {
        return $http.get('../login/showName.do');
    }
})
app.service('loginService',function($http) {
    //获取登录名
    this.getName=function () {
        return $http.get('../login/getName.do');
    }
});
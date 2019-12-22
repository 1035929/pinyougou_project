app.service('loginService',function ($http) {
    this.userLogin=function () {
        return $http.get('../login/showName.do');
    }
})
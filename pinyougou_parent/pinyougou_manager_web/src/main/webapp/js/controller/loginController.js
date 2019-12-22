/*
登录控制层
 */
app.controller('loginController',function ($scope, loginService) {
    $scope.loginShowName=function () {
        loginService.loginShowName().success(
            function (response) {
                $scope.loginName=response.loginName;
            }
        );
    }
})
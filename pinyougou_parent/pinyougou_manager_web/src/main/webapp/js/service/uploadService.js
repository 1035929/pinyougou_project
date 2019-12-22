/*
上传操作的服务层js
 */
app.service('uploadService',function($http) {
    //上传
    this.uploadFile=function () {
        //获取form表单里的元素
        var formData = new FormData();
        //注意：key值必须和后台controller方法中的参数名称一致
        formData.append("file",file.files[0]);
        return $http({
            url:"../upload/uploadFile.do",
            method:"post",
            data:formData,
            headers:{'Content-Type':undefined},
            transformRequest:angular.identity
        })

    };
})
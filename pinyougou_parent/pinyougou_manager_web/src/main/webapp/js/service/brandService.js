/*
brand服务层
*/
app.service('brandService',function ($http) {
    //查询全部
    this.findAll=function () {
        return $http.get('../brand/findAll.do');
    };
    //分页
    this.findByPage=function (page,size) {
        return $http.get('../brand/findPage.do?page='+page+'&size='+size);
    };
    //新增，修改
    this.save=function (entity,methodName) {
        return $http.post('../brand/'+methodName+'.do',entity);
    };
    //单一查询
    this.findOne=function (id) {
        return $http.get('../brand/findOne.do?id='+id);
    };
    //删除
    this.delete=function (selectIds) {
        return $http.get('../brand/deleteBrand.do?ids='+selectIds);
    };
    //条件查询
    this.search=function(page,size,searchEntity){
        //执行post请求
        return $http.post('../brand/search.do?page='+page+'&size='+size,searchEntity);
    };
    //select2
    this.selectBrandList=function () {
        return $http.get('../brand/selectBrandList.do');
    }
});
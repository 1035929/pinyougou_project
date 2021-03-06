app.controller('baseController',function ($scope) {
    $scope.paginationConf = {
        currentPage: 1,
        totalItems: 10,
        itemsPerPage: 10,
        perPageOptions: [10, 20, 30, 40, 50],
        onChange: function(){
            $scope.reloadList();//重新加载

        }
    };
    $scope.reloadList=function(){
        $scope.search($scope.paginationConf.currentPage,$scope.paginationConf.itemsPerPage);
    };

    $scope.selectIds=[];
    $scope.updateSelection=function (event,brandId) {
        if (event.target.checked){
            $scope.selectIds.push(brandId);
        } else {
            var index=$scope.selectIds.indexOf(brandId);
            $scope.selectIds.splice(index,1);
        }
    };
    //在list集合中根据某key的值查询对象
    $scope.searchObjectByKey=function (list,key,keyValue) {
        for (var i=0;i<list.length;i++){
            if(list[i][key]==keyValue){
                return list[i];
            }
        }
        return null;
    }

})
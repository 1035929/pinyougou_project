app.controller("searchController",function ($scope,$location, searchSerivce) {

    $scope.searchMap={'keywords':'','category':'','brand':'','spec':{},'price':'','pageNo':1,'pageSize':40,'sort':'','sortFiled':''}

    $scope.search=function () {
        $scope.searchMap.pageNo=parseInt($scope.searchMap.pageNo);
        searchSerivce.search($scope.searchMap).success(
            function (response) {
                $scope.resultMap=response;
                buildPageLabel()
            }
        );
    }
    //构建分页栏
    buildPageLabel=function(){
        $scope.pageLabel=[];
        var firstPage=1;//开始页码
        var lastPage=$scope.resultMap.totalPages;//截止页码
        $scope.firstDot=true;//前面的点
        $scope.lastDot=true;//后面的点
        if ($scope.resultMap.totalPages>5){//如果页码数量大于5
            if ($scope.searchMap.pageNo<=3){//如果当前页码小于等于3，显示前5页
                lastPage=5;
                $scope.firstDot=false;
            } else if ($scope.searchMap.pageNo>=$scope.resultMap.totalPages-2) {//显示后5页
                firstPage=$scope.resultMap.totalPages-4;
                $scope.lastDot=false;
            }else{//显示以当前页为中心的5页
                firstPage=$scope.searchMap.pageNo-2;
                lastPage=$scope.searchMap.pageNo+2;
            }
        }else {
            $scope.firstDot=false;//前面无点
            $scope.lastDot=false;//后面无点
        }
        for (var i=firstPage;i<=lastPage;i++){
            $scope.pageLabel.push(i);
        }

    }

    //分页查询
    $scope.queryByPage=function(pageNo){
        if (pageNo<1 || pageNo>$scope.resultMap.totalPages) {
            return;
        }
        $scope.searchMap.pageNo=pageNo;
        $scope.search();
    }

    //添加搜索项
    $scope.addSearchItem=function (key, value) {
        if (key=='category' || key=='brand' || key=='price') {
            $scope.searchMap[key]=value;
        }else {
            $scope.searchMap.spec[key]=value;
        }
        $scope.search();
    }
    //撤销搜索项
    $scope.removeSearchItem=function (key) {
        if (key=='category' || key=='brand' || key=='price') {
            $scope.searchMap[key]="";
        }else {
            delete $scope.searchMap.spec[key];
        }
        $scope.search();
    }

    //判断最前页
    $scope.isTopPage=function () {
        if ($scope.searchMap.pageNo==1){
            return true;
        } else {
            return false;
        }
    }
    //判断最后页
    $scope.isEndPage=function () {
        if ($scope.searchMap.pageNo==$scope.resultMap.totalPages){
            return true;
        } else {
            return false;
        }
    }
    //排序查询
    $scope.sortSearch=function (sortFiled,sort) {
        $scope.searchMap.sortFiled=sortFiled;
        $scope.searchMap.sort=sort;
        $scope.search()
    }
    //隐藏品牌
    $scope.keywordsIsBrand=function () {
       for (var i=0;i<$scope.resultMap.brandList.length;i++){
           if ($scope.searchMap.keywords.indexOf($scope.resultMap.brandList[i].text)>=0){
                return true;
           }
       }
        return false;
    }
    //搜索主页面链接
    $scope.loadKeywords=function () {
        $scope.searchMap.keywords=$location.search()['keywords'];
        $scope.search();
    }
})
 //控制层 
app.controller('goodsController' ,function($scope,$controller,$location,goodsService,uploadService,itemCatService,typeTemplateService){
	
	$controller('baseController',{$scope:$scope});//继承


    //读取列表数据绑定到表单中  
	$scope.findAll=function(){
		goodsService.findAll().success(
			function(response){
				$scope.list=response;
			}			
		);
	}    
	
	//分页
	$scope.findPage=function(page,rows){			
		goodsService.findPage(page,rows).success(
			function(response){
				$scope.list=response.rows;	
				$scope.paginationConf.totalItems=response.total;//更新总记录数
			}			
		);
	}
	
	//查询实体 
	$scope.findOne=function(){
		var id = $location.search()['id'];//获取参数值
		//alert(id);
		if (id==null){
			return ;
		}
		/*goodsService.findOne(id).success(
			function (response) {
				$scope.entity=response
			}
		)*/
		goodsService.findOne(id).success(
			function(response){
				$scope.entity= response;
				//向富文本编辑器中显示数据
				editor.html($scope.entity.tbGoodsDesc.introduction);

				//显示图片列表
				$scope.entity.tbGoodsDesc.itemImages=JSON.parse($scope.entity.tbGoodsDesc.itemImages);

				//显示扩展属性
				$scope.entity.tbGoodsDesc.customAttributeItems=JSON.parse($scope.entity.tbGoodsDesc.customAttributeItems);

				//规格
				$scope.entity.tbGoodsDesc.specificationItems=JSON.parse($scope.entity.tbGoodsDesc.specificationItems);

				//SKU列表规格列转换
				for (var i=0;i<$scope.entity.itemList.length;i++){
					$scope.entity.itemList[i].spec=JSON.parse($scope.entity.itemList[i].spec);
				}
			}
		);
	}

	$scope.checkAttributeValue=function(specName,optionName){
		var items = $scope.entity.tbGoodsDesc.specificationItems;
		var object = $scope.searchObjectByKey(items,'attributeName',specName);
		if (object==null){
			return false;
			}else {
			if (object.attributeValue.indexOf(optionName)>=0) {
				return true;

			} else {
					return false;
			}
	}}
	//保存 
	$scope.save=function(){
		$scope.entity.tbGoodsDesc.introduction=editor.html();
		var serviceObject;//服务层对象
		if($scope.entity.tbGoods.id!=null){//如果有ID
			serviceObject=goodsService.update( $scope.entity ); //修改  
		}else{
			serviceObject=goodsService.add( $scope.entity  );//增加 
		}				
		serviceObject.success(
			function(response){
				if(response.success){
					/*alert('保存成功');
					$scope.entity={};
					editor.html("");
					//重新查询 
		        	$scope.reloadList();//重新加载*/

					alert("保存成功");
					/*$scope.entity={};//每次新增完后设置为空，不然会滞留之前的信息
					//保存成功之后，清空副文本
					editor.html("");*/
					location.href="goods.html";//跳转到商品列表页

				}else{
					alert(response.message);
				}
			}		
		);
	}
	
	 
	//批量删除 
	$scope.dele=function(){			
		//获取选中的复选框			
		goodsService.dele( $scope.selectIds ).success(
			function(response){
				if(response.success){
					$scope.reloadList();//刷新列表
					$scope.selectIds=[];
				}						
			}		
		);				
	}



	$scope.searchEntity={};//定义搜索对象
	
	//搜索
	$scope.search=function(page,rows){			
		goodsService.search(page,rows,$scope.searchEntity).success(
			function(response){
				$scope.list=response.rows;	
				$scope.paginationConf.totalItems=response.total;//更新总记录数
			}			
		);
	}

	//新增
	/*$scope.add=function (entity) {
		$scope.entity.tbGoodsDesc.introduction=editor.html();
		goodsService.add($scope.entity).success(

			function (response) {
				if (response.success){
					alert(response.message);
					$scope.entity={};//每次新增完后设置为空，不然会滞留之前的信息
					$scope.reloadList();//重新加载

					//保存成功之后，清空副文本
					editor.html('');
				} else {
					alert(response.message);
				}
			}
		)
	}*/
	//上传方法
	$scope.uploadFile=function () {
		uploadService.uploadFile().success(
			function (response) {
				if (response.success){
					$scope.img_entity.url=response.message;
				}
				else {
					alert(response.message);
				}
			}
		)
	}
    //声明实体
	$scope.entity={tbGoods:{},tbGoodsDesc:{itemImages:[],customAttributeItems:[],specificationItems:[]}};
	//展示图片列表从fastdfs中获取
	$scope.imgList=function () {
		$scope.entity.tbGoodsDesc.itemImages.push($scope.img_entity);//把图片实体封装到entity.tbGoodsDesc.itemImages集合中.
	}

	//删除图片
	$scope.removeImg=function (index) {
		$scope.entity.tbGoodsDesc.itemImages.splice(index,1);
	}

	//展示列表
	$scope.selectItemCat1List=function () {
		itemCatService.findByParentId(0).success(
			function (response) {
				$scope.itemCat1List=response;
			}
		)
	}
	//2级下拉列表
	/*$scope.onchange=function () {
		itemCatService.findByParentId($scope.entity.tbGoods.category1Id).success(
			function (response) {
				$scope.itemCat2List=response;
				$scope.entity.tbGoods.category2Id=null;
				$scope.entity.tbGoods.category3Id=null;
			}
		)
	}*/
	$scope.$watch('entity.tbGoods.category1Id',function (newValue, oldValue) {
		itemCatService.findByParentId(newValue).success(
			function (response) {
				$scope.itemCat2List=response;
				$scope.itemCat3List={};
				/*$scope.entity.tbGoods.category2Id=response;
				$scope.entity.tbGoods.category3Id={};*/
			}
		)
	})

	$scope.$watch('entity.tbGoods.category2Id',function (newValue, oldValue) {
		itemCatService.findByParentId(newValue).success(
			function (response) {
				$scope.itemCat3List=response;
			}
		)
	})
	//模板id
	$scope.$watch('entity.tbGoods.category3Id',function (newValue, oldValue) {
		itemCatService.findOne(newValue).success(
			function (response) {
				$scope.entity.tbGoods.typeTemplateId=response.typeId;
			}
		)
	})

	$scope.typeTemplate={brandIds:[]};
	$scope.$watch('entity.tbGoods.typeTemplateId',function (newValue, oldValue) {
		typeTemplateService.findOne(newValue).success(
			function (response) {
				$scope.typeTemplate.brandIds=JSON.parse(response.brandIds);
				if ($location.search()['id'==null]) {
					$scope.entity.tbGoodsDesc.customAttributeItems=JSON.parse(response.customAttributeItems)
				}

			}
		)
		typeTemplateService.findSpecList(newValue).success(
			function (response) {
				$scope.specList=response;
			}
		)
	})

	$scope.updateSpecAtrribute=function ($event,name,value) {
		var object=$scope.searchObjectByKey($scope.entity.tbGoodsDesc.specificationItems,"attributeName",name);
		if (object!=null){
			if ($event.target.checked) {
				object.attributeValue.push(value);
			}else {
				object.attributeValue.splice(object.attributeValue.indexOf(value),1);
			}
			if (object.attributeValue.length==0){
				$scope.entity.tbGoodsDesc.specificationItems.splice($scope.entity.tbGoodsDesc.specificationItems.indexOf(object),1);
			}
		}else {
			$scope.entity.tbGoodsDesc.specificationItems.push({"attributeName":name,"attributeValue":[value]});
		}
	}

	$scope.createItemList=function () {
		$scope.entity.itemList=[{spec:{},price:0,num:99999,status:'0',isDefault:'0'}];
		var items=$scope.entity.tbGoodsDesc.specificationItems;
		for (var i=0;i<items.length;i++){
			$scope.entity.itemList=addcolumn($scope.entity.itemList,items[i].attributeName,items[i].attributeValue);
		}

	}
	addcolumn=function (list, columnName, columnValue) {
		var newList = [];
		for (var i = 0; i < list.length; i++) {
			var oldRow = list[i];//["attribute":"网络","attributeValue":"3G"],,,,{"机身内存":"16G","网络":"联通3G"}
			for (var j = 0; j < columnValue.length; j++) {
				var newRow=JSON.parse(JSON.stringify(oldRow));//深克隆
				newRow.spec[columnName]=columnValue[j];
				newList.push(newRow);
			}
		}
		return newList;
	}

	$scope.status=['未审核','已审核','审核未通过','关闭'];

	$scope.itemCatList=[];//商品分类列表
	//加载商品分类列表
	$scope.findItemCatList=function () {
		itemCatService.findAll().success(
			function (response) {
				for (var i=0; i<response.length; i++) {
					$scope.itemCatList[response[i].id]=response[i].name;
				}
			}
		);
	}
});

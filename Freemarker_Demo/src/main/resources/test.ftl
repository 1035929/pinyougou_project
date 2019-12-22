<html>
<head>
    <title>demo</title>
    <meta charset="utf-8">
</head>
<body>
<#--注释-->
<!--html注释-->
${name},你好。${message}
<#--定义简单类型-->
<#assign linkman="顾与南歌">
联系人:${linkman}
<#--定义对象类型-->
<#assign info={"mobile":"123456",'adress':'湖北武汉'}>
电话:${info.mobile}  地址:${info.adress}
<#include "head.ftl">
<#if success=true>
    你已通过实名认证
    <#else >
    你未通过实名认证
</#if>
------商品价格表------<br>
<#list goodsList as goods>
    ${goods_index+1} 商品名称:${goods.name} 价格:${goods.price}<br>
</#list>
共：${goodsList?size}条记录
<br>
<#assign text="{'bank':'工商银行','account':'10101920201920212'}" />
<#assign data=text?eval />
开户行：${data.bank} 账号：${data.account}
<br>
${today?date}<br>
${today?time}<br>
${today?datetime}<br>
<br>
${today?string("yyyy年MM月")}<br>
${point?c}
${point}

<br>
<#if  aaa??>
    变量存在
    <#else >
    变量不存在
</#if>
<br>
${aa!'----'}
</body>
</html>
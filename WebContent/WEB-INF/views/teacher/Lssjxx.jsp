<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../views/common/include.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv=Cache-Control content=no-cache />
</head>
<body tableClass="lssjxx">
 <div id="search" style="margin-bottom: 10px; width: 100%">
  <input id="ss" style="width: 50%"></input>
  <div id="mm" style="width: 50%">
   <div data-options="name:'all',iconCls:'icon-ok'">查询所有</div>
   <div data-options="name:'LSXM'">老师姓名</div>
   <div data-options="name:'KCMC'">课程名称</div>
  </div>
 </div>
 <div id="dataGrid" class="easyui-datagrid" style="width: 100%; height: 450px"></div>
 <div id="dialog" style="display: none;">
  <form action="" method="post">
   <div>
    <h1 align="center" style="">上架详细信息</h1>
    <hr />
   </div>
   <div>
    <table align="center">
     <input id="ID" name="ID" type="hidden" isSubmit="1" />
     <input id="LSID" name="LSID" type="hidden" isSubmit="1" />
     <input id="LSXM" name="LSXM" type="hidden" isSubmit="1" />
     <input id="KCID" name="KCID" type="hidden" isSubmit="1" />
     <tr>
     </tr>
     <tr>
      <td><label>课程名称:</label> <input id="KCMC" name="KCMC" class="easyui-textbox" data-options="required:true" isSubmit="1"/></td>
      <td><label>上架价格:</label> <input id="SJJG" name="SJJG" class="easyui-textbox" data-options="required:true,validType:['intOrFloat','length[0,20]']" isSubmit="1"/></td>
     </tr>
     <tr>
      <td><label>原价格:</label> <input id="YJG" name="YJG" class="easyui-textbox" data-options="required:true,validType:['intOrFloat','length[0,20]']" isSubmit="1"/></td>
     <td><label>返现比例:</label> <input id="FXBL" name="FXBL" class="easyui-textbox" data-options="required:true,validType:['intOrFloat','length[0,20]']" isSubmit="1" />%</td>
       </tr>
     <tr>
     <td><label>立减金额:</label> <input id="LJJE" name="LJJE" class="easyui-textbox" data-options="required:true,validType:['intOrFloat','length[0,20]']" isSubmit="1"/></td>
    <td><label>上架状态:</label> <input id="SJZT" name="SJZT" class="easyui-combobox" data-options="required:true"  isSubmit="1" /></td>
     </tr>
     <tr>
      <td align="center" colspan="3"><a id="sub" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-save'">保存</a> <a id="cols" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-no'">关闭</a>
     </tr>
    </table>
   </div>
  </form>
 </div>
</body>
<script type="text/javascript">
	
</script>
<script type="text/javascript" src="${cxt}/js/view/teacher/Lssjxx.js"></script>
</html>


<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../views/common/include.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv=Cache-Control content=no-cache />
</head>
<body tableClass="file">
 <div id="search" style="margin-bottom: 10px; width: 100%">
  <input id="ss" style="width: 50%"></input>
  <div id="mm" style="width: 50%">
   <div data-options="name:'all',iconCls:'icon-ok'">查询所有</div>
   <div data-options="name:'Id'">Id</div>
   <div data-options="name:'Name'">Name</div>
   <div data-options="name:'Url'">Url</div>
   <div data-options="name:'Type'">Type</div>
   <div data-options="name:'Size'">Size</div>
   <div data-options="name:'Createdat'">Createdat</div>
   <div data-options="name:'Updateat'">Updateat</div>
   
  </div>
 </div>
 <div id="dataGrid" class="easyui-datagrid" style="width: 100%; height: 450px"></div>
 <div id="dialog" style="display: none;">
  <form action="" method="post">
   <div>
    <h1 align="center" style="">File详细信息</h1>
    <hr />
   </div>
   <div>
    <table align="center">
    <input id="ID" name="ID" type="hidden" isSubmit="1" />
      <tr>
           <td><font color="red">*</font><label>ID:</label>
               <input id="ID"  name="ID"  class="easyui-textbox" required="true" validType="username" isSubmit="1" style="width: 280px; height: 23px;" />
            </td>
          
      </tr>
      <tr>
           <td><font color="red">*</font><label>NAME:</label>
               <input id="NAME"  name="NAME"  class="easyui-textbox" required="true" validType="username" isSubmit="1" style="width: 280px; height: 23px;" />
            </td>
          
      </tr>
      <tr>
           <td><font color="red">*</font><label>URL:</label>
               <input id="URL"  name="URL"  class="easyui-textbox" required="true" validType="username" isSubmit="1" style="width: 280px; height: 23px;" />
            </td>
          
      </tr>
      <tr>
           <td><font color="red">*</font><label>TYPE:</label>
               <input id="TYPE"  name="TYPE"  class="easyui-textbox" required="true" validType="username" isSubmit="1" style="width: 280px; height: 23px;" />
            </td>
          
      </tr>
      <tr>
           <td><font color="red">*</font><label>SIZE:</label>
               <input id="SIZE"  name="SIZE"  class="easyui-textbox" required="true" validType="username" isSubmit="1" style="width: 280px; height: 23px;" />
            </td>
          
      </tr>
      <tr>
          <td><font color="red">*</font><label>CREATEDAT:</label>
          <input id="CREATEDAT" name="CREATEDAT" isSubmit="1" class="easyui-datetimebox" required="true" style="width: 280px; height: 23px;" />
          </td>
      </tr>
      <tr>
          <td><font color="red">*</font><label>UPDATEAT:</label>
          <input id="UPDATEAT" name="UPDATEAT" isSubmit="1" class="easyui-datetimebox" required="true" style="width: 280px; height: 23px;" />
          </td>
      </tr>
     <tr>
      <td align="center" colspan="3"><a id="sub" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-save'">保存</a> <a id="cols" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-no'">关闭</a>
     </tr>
    </table>
   </div>
  </form>
 </div>
 <div id="view" style="display: none;">
  <div>
   <h1 align="center" style="">File详细信息</h1>
   <hr />
  </div>
  <div>
   <table align="center">
      <tr>
           <td><font color="red">*</font><label>ID:</label>
              
               <input id="ID"  name="ID"  readonly="readonly" isView="1" style="width: 280px; height: 23px;" />
            </td>
          
      </tr>
      <tr>
           <td><font color="red">*</font><label>NAME:</label>
              
               <input id="NAME"  name="NAME"  readonly="readonly" isView="1" style="width: 280px; height: 23px;" />
            </td>
          
      </tr>
      <tr>
           <td><font color="red">*</font><label>URL:</label>
              
               <input id="URL"  name="URL"  readonly="readonly" isView="1" style="width: 280px; height: 23px;" />
            </td>
          
      </tr>
      <tr>
           <td><font color="red">*</font><label>TYPE:</label>
              
               <input id="TYPE"  name="TYPE"  readonly="readonly" isView="1" style="width: 280px; height: 23px;" />
            </td>
          
      </tr>
      <tr>
           <td><font color="red">*</font><label>SIZE:</label>
              
               <input id="SIZE"  name="SIZE"  readonly="readonly" isView="1" style="width: 280px; height: 23px;" />
            </td>
          
      </tr>
      <tr>
          <td><label>CREATEDAT:</label>
          <input id="CREATEDAT" name="CREATEDAT"  readonly="readonly" isView="1" style="width: 280px; height: 23px;" />
          </td>
      </tr>
      <tr>
          <td><label>UPDATEAT:</label>
          <input id="UPDATEAT" name="UPDATEAT"  readonly="readonly" isView="1" style="width: 280px; height: 23px;" />
          </td>
      </tr>
    <tr>
     <td align="center" colspan="3"><a id="col" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-no'">关闭</a></td>
    </tr>
   </table>
  </div>
 </div>
</body>
<script type="text/javascript" src="${cxt}/js/view/teacher/File.js"></script>
<script type="text/javascript">
	
</script>
</html>

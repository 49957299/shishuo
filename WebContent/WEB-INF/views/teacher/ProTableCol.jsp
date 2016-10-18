
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../views/common/include.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv=Cache-Control content=no-cache />
</head>
<body tableClass="proTableCol">
 <div id="search" style="margin-bottom: 10px; width: 100%">
  <input id="ss" style="width: 50%"></input>
  <div id="mm" style="width: 50%">
   <div data-options="name:'all',iconCls:'icon-ok'">查询所有</div>
   <div data-options="name:'Id'">Id</div>
   <div data-options="name:'Classes'">Classes</div>
   <div data-options="name:'ColName'">ColName</div>
   <div data-options="name:'NoteName'">NoteName</div>
   <div data-options="name:'DataType'">DataType</div>
   <div data-options="name:'DataOptions'">DataOptions</div>
   <div data-options="name:'DataLength'">DataLength</div>
   <div data-options="name:'DataPrecision'">DataPrecision</div>
   <div data-options="name:'DataScale'">DataScale</div>
   <div data-options="name:'Nullable'">Nullable</div>
   <div data-options="name:'PkSeq'">PkSeq</div>
   <div data-options="name:'DicCode'">DicCode</div>
   <div data-options="name:'HiddenType'">HiddenType</div>
   <div data-options="name:'ObjectName'">ObjectName</div>
   <div data-options="name:'IsSearch'">IsSearch</div>
   <div data-options="name:'SearchOperator'">SearchOperator</div>
   <div data-options="name:'DicType'">DicType</div>
   
  </div>
 </div>
 <div id="dataGrid" class="easyui-datagrid" style="width: 100%; height: 450px"></div>
 <div id="dialog" style="display: none;">
  <form action="" method="post">
   <div>
    <h1 align="center" style="">ProTableCol详细信息</h1>
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
           <td><font color="red">*</font><label>CLASSES:</label>
               <input id="CLASSES"  name="CLASSES"  class="easyui-textbox" required="true" validType="username" isSubmit="1" style="width: 280px; height: 23px;" />
            </td>
          
      </tr>
      <tr>
           <td><font color="red">*</font><label>COLNAME:</label>
               <input id="COLNAME"  name="COLNAME"  class="easyui-textbox" required="true" validType="username" isSubmit="1" style="width: 280px; height: 23px;" />
            </td>
          
      </tr>
      <tr>
           <td><font color="red">*</font><label>NOTENAME:</label>
               <input id="NOTENAME"  name="NOTENAME"  class="easyui-textbox" required="true" validType="username" isSubmit="1" style="width: 280px; height: 23px;" />
            </td>
          
      </tr>
      <tr>
           <td><font color="red">*</font><label>DATATYPE:</label>
               <input id="DATATYPE"  name="DATATYPE"  class="easyui-textbox" required="true" validType="username" isSubmit="1" style="width: 280px; height: 23px;" />
            </td>
          
      </tr>
      <tr>
           <td><font color="red">*</font><label>DATAOPTIONS:</label>
               <input id="DATAOPTIONS"  name="DATAOPTIONS"  class="easyui-textbox" required="true" validType="username" isSubmit="1" style="width: 280px; height: 23px;" />
            </td>
          
      </tr>
      <tr>
           <td><font color="red">*</font><label>DATALENGTH:</label>
               <input id="DATALENGTH"  name="DATALENGTH"  class="easyui-textbox" required="true" validType="username" isSubmit="1" style="width: 280px; height: 23px;" />
            </td>
          
      </tr>
      <tr>
           <td><font color="red">*</font><label>DATAPRECISION:</label>
               <input id="DATAPRECISION"  name="DATAPRECISION"  class="easyui-textbox" required="true" validType="username" isSubmit="1" style="width: 280px; height: 23px;" />
            </td>
          
      </tr>
      <tr>
           <td><font color="red">*</font><label>DATASCALE:</label>
               <input id="DATASCALE"  name="DATASCALE"  class="easyui-textbox" required="true" validType="username" isSubmit="1" style="width: 280px; height: 23px;" />
            </td>
          
      </tr>
      <tr>
           <td><font color="red">*</font><label>NULLABLE:</label>
               <input id="NULLABLE"  name="NULLABLE"  class="easyui-textbox" required="true" validType="username" isSubmit="1" style="width: 280px; height: 23px;" />
            </td>
          
      </tr>
      <tr>
           <td><font color="red">*</font><label>PKSEQ:</label>
               <input id="PKSEQ"  name="PKSEQ"  class="easyui-textbox" required="true" validType="username" isSubmit="1" style="width: 280px; height: 23px;" />
            </td>
          
      </tr>
      <tr>
           <td><font color="red">*</font><label>DICCODE:</label>
               <input id="DICCODE"  name="DICCODE"  class="easyui-textbox" required="true" validType="username" isSubmit="1" style="width: 280px; height: 23px;" />
            </td>
          
      </tr>
      <tr>
           <td><font color="red">*</font><label>HIDDENTYPE:</label>
               <input id="HIDDENTYPE"  name="HIDDENTYPE"  class="easyui-textbox" required="true" validType="username" isSubmit="1" style="width: 280px; height: 23px;" />
            </td>
          
      </tr>
      <tr>
           <td><font color="red">*</font><label>OBJECTNAME:</label>
               <input id="OBJECTNAME"  name="OBJECTNAME"  class="easyui-textbox" required="true" validType="username" isSubmit="1" style="width: 280px; height: 23px;" />
            </td>
          
      </tr>
      <tr>
           <td><font color="red">*</font><label>ISSEARCH:</label>
               <input id="ISSEARCH"  name="ISSEARCH"  class="easyui-textbox" required="true" validType="username" isSubmit="1" style="width: 280px; height: 23px;" />
            </td>
          
      </tr>
      <tr>
           <td><font color="red">*</font><label>SEARCHOPERATOR:</label>
               <input id="SEARCHOPERATOR"  name="SEARCHOPERATOR"  class="easyui-textbox" required="true" validType="username" isSubmit="1" style="width: 280px; height: 23px;" />
            </td>
          
      </tr>
      <tr>
           <td><font color="red">*</font><label>DICTYPE:</label>
               <input id="DICTYPE"  name="DICTYPE"  class="easyui-textbox" required="true" validType="username" isSubmit="1" style="width: 280px; height: 23px;" />
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
   <h1 align="center" style="">ProTableCol详细信息</h1>
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
           <td><font color="red">*</font><label>CLASSES:</label>
              
               <input id="CLASSES"  name="CLASSES"  readonly="readonly" isView="1" style="width: 280px; height: 23px;" />
            </td>
          
      </tr>
      <tr>
           <td><font color="red">*</font><label>COLNAME:</label>
              
               <input id="COLNAME"  name="COLNAME"  readonly="readonly" isView="1" style="width: 280px; height: 23px;" />
            </td>
          
      </tr>
      <tr>
           <td><font color="red">*</font><label>NOTENAME:</label>
              
               <input id="NOTENAME"  name="NOTENAME"  readonly="readonly" isView="1" style="width: 280px; height: 23px;" />
            </td>
          
      </tr>
      <tr>
           <td><font color="red">*</font><label>DATATYPE:</label>
              
               <input id="DATATYPE"  name="DATATYPE"  readonly="readonly" isView="1" style="width: 280px; height: 23px;" />
            </td>
          
      </tr>
      <tr>
           <td><font color="red">*</font><label>DATAOPTIONS:</label>
              
               <input id="DATAOPTIONS"  name="DATAOPTIONS"  readonly="readonly" isView="1" style="width: 280px; height: 23px;" />
            </td>
          
      </tr>
      <tr>
           <td><font color="red">*</font><label>DATALENGTH:</label>
              
               <input id="DATALENGTH"  name="DATALENGTH"  readonly="readonly" isView="1" style="width: 280px; height: 23px;" />
            </td>
          
      </tr>
      <tr>
           <td><font color="red">*</font><label>DATAPRECISION:</label>
              
               <input id="DATAPRECISION"  name="DATAPRECISION"  readonly="readonly" isView="1" style="width: 280px; height: 23px;" />
            </td>
          
      </tr>
      <tr>
           <td><font color="red">*</font><label>DATASCALE:</label>
              
               <input id="DATASCALE"  name="DATASCALE"  readonly="readonly" isView="1" style="width: 280px; height: 23px;" />
            </td>
          
      </tr>
      <tr>
           <td><font color="red">*</font><label>NULLABLE:</label>
              
               <input id="NULLABLE"  name="NULLABLE"  readonly="readonly" isView="1" style="width: 280px; height: 23px;" />
            </td>
          
      </tr>
      <tr>
           <td><font color="red">*</font><label>PKSEQ:</label>
              
               <input id="PKSEQ"  name="PKSEQ"  readonly="readonly" isView="1" style="width: 280px; height: 23px;" />
            </td>
          
      </tr>
      <tr>
           <td><font color="red">*</font><label>DICCODE:</label>
              
               <input id="DICCODE"  name="DICCODE"  readonly="readonly" isView="1" style="width: 280px; height: 23px;" />
            </td>
          
      </tr>
      <tr>
           <td><font color="red">*</font><label>HIDDENTYPE:</label>
              
               <input id="HIDDENTYPE"  name="HIDDENTYPE"  readonly="readonly" isView="1" style="width: 280px; height: 23px;" />
            </td>
          
      </tr>
      <tr>
           <td><font color="red">*</font><label>OBJECTNAME:</label>
              
               <input id="OBJECTNAME"  name="OBJECTNAME"  readonly="readonly" isView="1" style="width: 280px; height: 23px;" />
            </td>
          
      </tr>
      <tr>
           <td><font color="red">*</font><label>ISSEARCH:</label>
              
               <input id="ISSEARCH"  name="ISSEARCH"  readonly="readonly" isView="1" style="width: 280px; height: 23px;" />
            </td>
          
      </tr>
      <tr>
           <td><font color="red">*</font><label>SEARCHOPERATOR:</label>
              
               <input id="SEARCHOPERATOR"  name="SEARCHOPERATOR"  readonly="readonly" isView="1" style="width: 280px; height: 23px;" />
            </td>
          
      </tr>
      <tr>
           <td><font color="red">*</font><label>DICTYPE:</label>
              
               <input id="DICTYPE"  name="DICTYPE"  readonly="readonly" isView="1" style="width: 280px; height: 23px;" />
            </td>
          
      </tr>
    <tr>
     <td align="center" colspan="3"><a id="col" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-no'">关闭</a></td>
    </tr>
   </table>
  </div>
 </div>
</body>
<script type="text/javascript" src="${cxt}/js/view/teacher/ProTableCol.js"></script>
<script type="text/javascript">
	
</script>
</html>

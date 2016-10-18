var url="toLoadHuman";
client = new Resource("A6992068603519", "A6992068603519",url);

function addVal(){
	var DW="";
	var DWBH="";
	$('#dialog').find('[isSubmit="1"]').each(function() {
		if($(this).attr("id")=="DW"){
			DW=getObjectValue($(this));
		}
       if($(this).attr("id")=="DWBH"){
			DWBH=getObjectValue($(this));
		}
		
	})
	$.ajax({
			type:"post",
			url:'/web/toLoadHuman/addVal',
			data:{DWBH:DWBH},
			success:function(data){
				if(data==DW){
					save();
				}else{
				$.messager.alert("提示","单位编号和单位不匹配，请确认！");
					
				}}
	})
	

}
function add(){
	dialog();
}
function dialog(){
	 $('#importDialog').dialog({   
         title: '请选择文件...',   
         width: 400,   
         height: 200,   
         closed: false,   
         cache: false,   
         modal: true ,
         buttons:[{
        	    text:'确定',
        	    handler:function(){
        	    	$('#importdate').form('submit', {  
        	    		                method: "POST",
        	    		                url:'/web/toLoadHuman/importdate',
        	    		                success: function (res) {
        	    		                	 $.messager.alert("提示","人员数据导入成功！");
        	    		                     $('#importDialog').dialog('close');
        	    		                     find();
        	    		                }
        	    		             });
        	    }
        	   },{
        	    text:'取消',
        	    handler:function(){
        	    	$('#importDialog').dialog('close');	 
        	    }
        	   }]
     });   
	 
}

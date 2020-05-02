<%--
  Created by IntelliJ IDEA.
  User: sunys
  Date: 2020/5/2
  Time: 下午5:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<div class="baocuo_content_text">
    <div class="baocuo_mesg">
        ${fn:escapeXml(errormsg)}
    </div>
</div>
<div class="machincodeinfo">
    <div class="machincodetitle">Machine Code:</div>
    <div>
        <span class="machincode">${fn:escapeXml(machineCode)}</span>
    </div>
</div>
</body>
</html>

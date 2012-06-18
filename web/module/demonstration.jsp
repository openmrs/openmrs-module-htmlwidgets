<%@ include file="/WEB-INF/template/include.jsp" %>
<%@ include file="/WEB-INF/template/headerMinimal.jsp" %>
<%@ taglib prefix="wgt" uri="/WEB-INF/view/module/htmlwidgets/resources/htmlwidgets.tld" %>
<openmrs:htmlInclude file="/scripts/jquery/jquery-1.3.2.min.js" />

<script type="text/javascript" charset="utf-8">
	
	jQuery(document).ready(function() {

		jQuery('.defaultWidget').hide();

		<c:if test="${!empty property}">
			jQuery('#defaultWidget${property}').show();
		</c:if>

		jQuery('#propertySelector').change(function(event){
			jQuery('.defaultWidget').hide();
			var val = jQuery(this).val();
			jQuery('#defaultWidget'+val).show();
		});
		
	});
</script>

<form>
	<h3>Choose from the following to render a widget of the appropriate type:</h3>
	<select name="property" id="propertySelector">
		<option value=""></option>
		<c:forEach items="${availableProperties}" var="p">
			<option value="${p.key}" <c:if test="${p.key == property}">selected</c:if>>${p.value}</option>
		</c:forEach>
	</select>
</form>

<c:forEach items="${availableProperties}" var="p">

	<div id="defaultWidget${p.key}" class="defaultWidget">
		<br/>
		<form method="get" action="demonstration.form">
			<input type="hidden" name="property" value="${p.key}"/>
			Format:  <input type="text" name="format" value="${format}" size="50"/><br/>
			Attributes:  <input type="text" name="attributes" value="${attributes}" size="50"/><br/>
			<wgt:widget id="defaultField${p.key}" name="${p.key}" object="${demo}" property="${p.key}" format="${format}" attributes="${attributes}"/>
			<input type="submit" id="defaultFieldTest${p.key}" value="Test"/>
		</form>
		<c:if test="${property == p.key}">
			<br/>
			<h4>Server got a value of:</h4>
			<c:choose>
				<c:when test="${empty submittedValue}">
					Empty
				</c:when>
				<c:otherwise>
					Type: ${submittedValueType}<br/>
					Value: ${submittedValue}
				</c:otherwise>
			</c:choose>
		</c:if>
	</div>

</c:forEach>


<%@ include file="/WEB-INF/template/footer.jsp" %>
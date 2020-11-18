<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="table-footer">
	<div class="jump-page">
    	<button id="first-page">
        	<i class="fa fa-angle-double-left"></i>
    	</button>
    	<button id="pre-page">
        	<i class="fa fa-angle-left"></i>
    	</button>
		<span class="page-description">
			<span>第</span>
			<input id="current-page" type="text" placeholder="">
			<span>页，共</span>
			<span id="total-page"></span>
			<span>页</span>
		</span>
	    <button class="pre-page" id="next-page">
	        <i class="fa fa-angle-right"></i>
	    </button>
	    <button class="first-page" id="last-page">
	        <i class="fa fa-angle-double-right"></i>
	    </button>
	    <button class="refresh-page" id="refresh-page">
	        <i class="fa fa-refresh"></i>
	    </button>
	</div>
	<div class="select-page">
		<span class="page-description">
			<span>显示</span>
			<span id="first-index"></span>
			<span>到</span>
			<span id="last-index"></span>
			<span>条，共</span>
			<span id="total-item"></span>
			<span>条，</span>
			<input id="per-page" type="text" placeholder="">
			<span>条/页</span>
		</span>
    </div>
</div>
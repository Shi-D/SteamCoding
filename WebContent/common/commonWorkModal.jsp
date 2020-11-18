<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!-- 管理员，老师，学生 -->

<div class="modal" id="delete-work-modal" data-backdrop="static">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<p>删除作品</p>
			</div>
			<div class="modal-body">
				<p>
					确认删除作品：<span style="color: red;" id="delete-work-name"></span> ?
				</p>
			</div>
			<div class="modal-footer">
				<button class="btn btn-outline-secondary" id="sureToDelete_cancel"
					data-dismiss="modal">取消</button>
				<button class="btn btn-primary" id="sureToDelete"
					data-toggle="modal">确定</button>
			</div>
		</div>
	</div>
</div>

<div class="modal" id="cancel-publish-modal" data-backdrop="static">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<p>撤销已发布作品</p>
			</div>
			<div class="modal-body">
				<p>
					确认撤销作品：<span style="color: red;" id="cancel-work-name"></span>的发布?
				</p>
			</div>
			<div class="modal-footer">
				<button class="btn btn-outline-secondary" id="sureToCancel_cancel"
					data-dismiss="modal">取消</button>
				<button class="btn btn-primary" id="sureToCancel"
					data-toggle="modal">确定</button>
			</div>
		</div>
	</div>
</div>

<div class="modal" id="publish-work-modal" data-backdrop="static">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<p>发布作品</p>
			</div>
			<div class="modal-body">
				<div class="form-group">
					<label id="star" for="workName">*</label><label>请输入作品名称:</label><br>
					<input id="workName" class="form-control" name="cantbeNull"
						value="">
					<div class="invalid-feedback">作品名称不能为空</div>
				</div>
				<div class="form-group">
					<label for="workDescription">请输入作品介绍:</label><br>
					<textarea id="workDescription" class="form-control"
						name="introduction" rows="4" data-value=""></textarea>
					<div class="invalid-feedback">字数超过限制</div>
					<div style="color: gray; font-size: 12px; float: right">
						<span id="count">0</span>/150
					</div>
				</div>
			</div>
			<div class="modal-footer">
				<button class="btn btn-outline-secondary" id="cancelToPublish"
					data-dismiss="modal">取消</button>
				<button class="btn btn-primary" id="sureToPublish"
					data-toggle="modal">确定</button>
			</div>
		</div>
	</div>
</div>

<!-- 超级管理员 -->
<c:if test="${sessionScope.role==0}">

</c:if>

<!-- 管理员 -->
<c:if test="${sessionScope.role==1}">

</c:if>


<!-- 老师 -->
<c:if test="${sessionScope.role==2}">

</c:if>


<!-- 学生 -->
<c:if test="${sessionScope.role==3}">

</c:if>

package com.steam.action.homeworkmanagement;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.framework.authority.entity.User;
import com.framework.authority.service.UserService;
import com.framework.common.SystemContext;
import com.framework.constants.SteamConstants;
import com.framework.system.common.base.action.BaseGridAction;
import com.framework.utils.FILEUtils;
import com.framework.utils.URLUtils;
import com.steam.entity.CourseWork;
import com.steam.entity.Homework;
import com.steam.service.ClassesService;
import com.steam.service.HomeworkService;
import com.steam.service.WorkService;

public class HomeworkInfoAction extends BaseGridAction {

	private static final long serialVersionUID = -3059299856386929281L;

	@Autowired
	HomeworkService homeworkService;
	@Autowired
	ClassesService classesService;
	@Autowired
	UserService userService;
	@Autowired
	WorkService workService;

	private String studentIds;
	private String evaluation;
	private Integer score;

	private String homeworkIds;
	private Integer homeworkId;
	private Integer copyHomeworkId;
	private Integer workId;
	private String homeworkName;
	private String homeworkContent;
	private Date deadline;
	private File homeworkFile;
	private String fileName;
	private String classIds;
	private String deleteClassIds;
	private String addClassIds;
	private Integer pageNo = 1;
	private Integer pageSize = 20;
	private Boolean search = true;

	private Map<String, Object> basicInfo = new HashMap<String, Object>();

	// 打开页面 检索所有作业
	@SuppressWarnings("unchecked")
	public String queryHomework() throws ParseException {
		User user = SystemContext.getCurrentUser();
		results = new ArrayList<Map<String, Object>>();
		this.getPager().setPageNo(pageNo);
		this.getPager().setPageSize(pageSize);
		this.getPager().setSearch(search);
//		List<Map<String,Object>> sorter = this.getSorters();
//		this.getPager().
		String hql = "";
		if (user.getUserRole() == 2) {
			hql = "select homeworkId as homeworkId, homeworkName as homeworkName, creationTime as creationTime,deadline as deadline,className as classesName,classId as classId from HomeworkInfo where userId ="
					+ user.getId();
			homeworkService.find(this.getPager(), hql, this.getFilter());
			basicInfo.put("totalCount", this.getPager().getTotalCount());
			basicInfo.put("totalPage", this.getPager().getTotalPage());
			results = (List<Map<String, Object>>) this.getPager().getDataset();
			int len = results.size();
			Homework homework;
			for (int i = 0; i < len; i++) {
				homework = homeworkService.get(
						Homework.class,
						Integer.parseInt(results.get(i).get("homeworkId")
								.toString()));
				results.get(i).put("homeworkContent",
						homework.getHomeworkContent());
				results.get(i).put("sourceLink", homework.getSourceLink());
				results.get(i).put("fileName",homework.getFileName());
				results.get(i).put(
						"submitedStudents",
						homeworkService.countSubmitedStudentByHomework(homework
								.getHomeworkId()));
				results.get(i).put(
						"totalStudents",
						homeworkService.countStudentsByHomework(homework
								.getHomeworkId()));
			}
		}
		/*
		 * 1.查看学生在哪些班级2.根据班级ID查看作业ID3.返回作业实体
		 */
		if (user.getUserRole() == 3) {
			String qHomeworkIds = homeworkService
					.queryHomeworkInClass(classesService
							.queryClassesByStudentId(user.getId()));
			if (qHomeworkIds != null) {
				hql = "select homeworkId as homeworkId, homeworkName as homeworkName, creationTime as creationTime,userId as userId,deadline as deadline from Homework where homeworkId in ("
						+ qHomeworkIds + ")";
				homeworkService.find(this.getPager(), hql, this.getFilter());
				basicInfo.put("totalCount", this.getPager().getTotalCount());
				basicInfo.put("totalPage", this.getPager().getTotalPage());
				results = (List<Map<String, Object>>) this.getPager()
						.getDataset();
				int len = results.size();
				Date date = new Date();
				SimpleDateFormat format = new SimpleDateFormat(
						"yyyy-M-dd HH:mm:ss");
				List<Map<String, Object>> tmp;
				for (int i = 0; i < len; i++) {
					// 如果已经提交过 那么返回true 如果没有提交过那么返回false
					results.get(i).put(
							"isSubmit",
							homeworkService.checkSubmitStudent(results.get(i)
									.get("homeworkId").toString(), user.getId()
									.toString()));
					tmp = homeworkService.queryScoreAndEvaluation(results
							.get(i).get("homeworkId").toString(), user.getId()
							.toString());
					if (tmp != null) {
						results.get(i).put("score", tmp.get(0).get("score"));
						results.get(i).put("evaluation",
								tmp.get(0).get("evaluation"));
					} else {
						results.get(i).put("score", null);
						results.get(i).put("evaluation", null);
					}
					results.get(i).put(
							"teacherName",
							userService.get(
									User.class,
									Integer.valueOf(results.get(i)
											.get("userId").toString()))
									.getUserName());
									
					
					if (date.after(format.parse(results.get(i).get("deadline")
							.toString()))) {
						results.get(i).put("submitable", false);
					} else{
						if(tmp != null){
							if((tmp.get(0).get("score") != null))
								results.get(i).put("submitable", false);
							else
								results.get(i).put("submitable", true);
						}else{
							results.get(i).put("submitable", true);
						}
					}
						
				}
			}
		}
		results.add(basicInfo);
		return "result>json";
	}

	// 增加作业
	public String addHomework() {
		results = new ArrayList<Map<String, Object>>();
		User user = SystemContext.getCurrentUser();
		String path = null;
		if (user.getUserRole() == 2) {
			try{
				if (copyHomeworkId != null){
					Homework homework = homeworkService.get(Homework.class,copyHomeworkId);
					this.setFileName(homework.getFileName());
					File file = new File(SteamConstants.ABSOLUTE_URL_WITHOUT_ACCOUNT + homework.getSourceLink());
					this.setHomeworkFile(file);
				}
				if (fileName != null) {
					String uniqueFileName = new Date().getTime() + fileName;
					path = "account/" + user.getUsername() + "/homework/"
							+ uniqueFileName;	
					FILEUtils.saveUploadFile(this.getHomeworkFile(),
									URLUtils.generateURLforHomework(user
											.getUsername()), uniqueFileName);
					
					System.out.println("*****fileupload.....");
				}
				homeworkService.publishHomeworkInClass(homeworkService
						.addHomework(this.getHomeworkName(),
								this.getDeadline(), this.getHomeworkContent(),
								path, user.getId(),this.getFileName()), this.getClassIds());
				basicInfo.put("result", "添加成功");
			}catch(Exception e){
				basicInfo.put("result","添加失败");
			}
			results.add(basicInfo);
		}
		return "result>json";
	}
	// 教师删除发布的作业
	public String deleteHomework() {
		results = new ArrayList<Map<String, Object>>();
		User user = SystemContext.getCurrentUser();
		Homework homework;
		String[] deleteHomeworkId = this.getHomeworkIds().split(",");
		int len = deleteHomeworkId.length;
		for (int i = 0; i < len; i++) {
			homework = homeworkService.get(Homework.class,
					Integer.valueOf(deleteHomeworkId[i]));
			if (homework.getUserId() == user.getId()) {// 判断登陆者是否是上传者
				System.out.println("answer++++++++++++++++++"
						+ homework.getSourceLink());
				if (homework.getSourceLink() != null
						&& !homework.getSourceLink().equals("")) {
					System.out.println(URLUtils.generateURLforHomework(user
							.getUsername()) + homework.getSourceLink());
					new File(
							URLUtils.generateURLforHomework(user.getUsername())
									+ homework.getSourceLink()).delete(); // 如果该作业存在资源文件，则删除。
				}
			}
		}
		homeworkService.deleteHomework(this.getHomeworkIds());
		basicInfo.put("result", "删除成功");
		results.add(basicInfo);
		return "result>json";
	}

	// 教师查看学生提交的作业 放进来提交作品的链接 如果有作品的话
	@SuppressWarnings("unchecked")
	public String querySubmitedHomework() {
		User user = SystemContext.getCurrentUser();
		results = new ArrayList<Map<String, Object>>();
		this.getPager().setPageNo(pageNo);
		this.getPager().setPageSize(pageSize);
		this.getPager().setSearch(search);
		if (user.getUserRole() == 2) {
			String hql = "select userId as userId, userName as userName,homeworkId as homeworkId, sourceLink as sourceLink, creationTime as creationTime, userName as userName, evaluation as evaluation,className as className, score as score, workId as workId from HomeworkStudentView where homeworkId ="
					+ this.getHomeworkId();
			homeworkService.find(this.getPager(), hql, this.getFilter());
			basicInfo.put("totalCount", this.getPager().getTotalCount());
			basicInfo.put("totalPage", this.getPager().getTotalPage());
			results = (List<Map<String, Object>>) this.getPager().getDataset();
			int len = results.size();
			CourseWork work;
			for (int i = 0; i < len; i++) {
				if (results.get(i).get("workId") != null) {
					work = workService.getWork(Integer.valueOf(results.get(i)
							.get("workId").toString()));
					results.get(i).put("workLink",
							work.getWorkLink() + work.getWorkName());
				} else
					results.get(i).put("workLink", null);
			}
			results.add(basicInfo);
		}
		return "result>json";
	}

	// 批阅作业
	public String evaluateSubmitedHomework() {
		results = new ArrayList<Map<String, Object>>();
		User user = SystemContext.getCurrentUser();
		if (user.getUserRole() == 2) {
			try {
				homeworkService.updateSubmitedHomework(this.getStudentIds(),
						this.getHomeworkId(), this.getEvaluation(),
						this.getScore());
				basicInfo.put("result", "批阅成功");
			} catch (Exception e) {
				System.out.println(e);
				basicInfo.put("result", "批阅失败");
			}
			results.add(basicInfo);
			return "result>json";
		}
		basicInfo.put("result", "没有权限");
		return "result>json";
	}

	// 提交作品可以提交workId
	public String submitHomework() {
		results = new ArrayList<Map<String, Object>>();
		User user = SystemContext.getCurrentUser();
		if (homeworkFile != null) {
			if (homeworkService.checkSubmitStudent(this.getHomeworkId().toString(), user.getId().toString()) == 1) { // 已经提交过作业
				// 删除资源文件
				String path = homeworkService.getSubmitSourceLink(this.getHomeworkId(), user.getId());
				//System.out.println("path is " + SteamConstants.ABSOLUTE_URL_WITHOUT_ACCOUNT + path);
				if (path != null)
					new File(SteamConstants.ABSOLUTE_URL_WITHOUT_ACCOUNT + path).delete();
				homeworkService.deleteSubmiteHomework(this.getHomeworkId(),user.getId());
			}
			String path = "account/" + user.getUsername() + "/homework/" + this.getFileName();
			try {
				if (FILEUtils.saveUploadFile(this.getHomeworkFile(),URLUtils.generateURLforHomework(user.getUsername()),this.getFileName())) {
					//System.out.println("*****fileupload.....");
					//上传文件类型的作业
					homeworkService.sumbitHomework(user.getId(),this.getHomeworkId(), path, this.getWorkId());
					basicInfo.put("result", "提交成功");
				} else {
					basicInfo.put("result", "提交失败");
				}
			} catch (IOException e) {
				basicInfo.put("result", "提交失败");
			}
		} else {
			if (homeworkService.checkSubmitStudent(this.getHomeworkId().toString(), user.getId().toString()) == 1) { // 已经提交过作业
				homeworkService.updateSubmitedHomework(user.getId(),this.getHomeworkId(), this.getWorkId());
			} else {
				//上传作品类型的作业
				homeworkService.sumbitHomework(user.getId(),this.getHomeworkId(), this.getWorkId());
			}
			basicInfo.put("result", "提交成功");
		}
		results.add(basicInfo);
		return "result>json";
	}

	public String getDetailHomework() throws ParseException {
		results = new ArrayList<Map<String, Object>>();
		User user = SystemContext.getCurrentUser();
		if (homeworkService.checkStudentHaveHoemwork(user.getId(), this.getHomeworkId())) {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-M-dd HH:mm:ss");
			Homework homework = homeworkService.get(Homework.class, this.getHomeworkId());
			
			basicInfo.put("result", homework);
			//根据homework id 和 user id查询自己的作品
			List<Map<String, Object>> tmp = homeworkService.queryScoreAndEvaluation(this.getHomeworkId().toString(), user.getId().toString());
			if(tmp != null){
				if(!tmp.isEmpty()){
					basicInfo.put("score", tmp.get(0).get("score"));
					basicInfo.put("evaluation", tmp.get(0).get("evaluation"));
				}else{
					basicInfo.put("isSubmit", homeworkService.checkSubmitStudent(this.getHomeworkId().toString(), user.getId().toString()));
				}
			}else{
				basicInfo.put("isSubmit", homeworkService.checkSubmitStudent(this.getHomeworkId().toString(), user.getId().toString()));
			}
							
			if ((new Date().after(format.parse(homework.getDeadline().toString())))){
				basicInfo.put("submitable", false);
			} else{
				if(tmp != null){
					if((tmp.get(0).get("score") != null))
						basicInfo.put("submitable", false);
					else
						basicInfo.put("submitable", true);
				}else{
						basicInfo.put("submitable", true);
				}
			}
			
			
			//List<Map<String,Object>> sourseLinks = homeworkService.getResultByHQL(hql);
			
			List<Map<String,Object>> sourseLinks = homeworkService.queryHomeworkByWordIdandUserId(this.getHomeworkId(), user.getId());
			
			if(!sourseLinks.isEmpty()){
				if(sourseLinks.get(0) != null){
					basicInfo.put("sourceLink",sourseLinks.get(0).get("sourceLink"));
				}else{
					basicInfo.put("sourceLink",null);
				}
				if(sourseLinks.get(0).get("workId") != null){
					CourseWork work = workService.getWork(Integer.valueOf(sourseLinks.get(0).get("workId").toString()));
					basicInfo.put("workLink",work.getWorkLink() + work.getWorkName());
					basicInfo.put("workId",sourseLinks.get(0).get("workId"));
					basicInfo.put("workName",work.getCourseWorkName());
				}else{
					basicInfo.put("workLink",null);
					basicInfo.put("workId",null);
					basicInfo.put("workName",null);
				}
			}else{
				basicInfo.put("sourceLink",null);
			}
			

			results.add(basicInfo);
		}
		return "result>json";
	}

	// 查询可以发布的班级
	public String queryClassesByUserId() {
		User user = SystemContext.getCurrentUser();
		if (user.getUserRole() == 2)
			results = classesService.queryClassesByTeacherId(user.getId());
		return "result>json";
	}

	public String queryCoursework() {
		User user = SystemContext.getCurrentUser();
		results = workService.queryWork(user.getId());
		return "result>json";
	}

	// 更新作业
	public String updateHomework() {
		results = homeworkService.updateHomeworkService(this.getHomeworkId(),
				this.getHomeworkContent(), this.getHomeworkName(),
				this.getHomeworkFile(), this.getFileName(), this.getDeadline(),this.getDeleteClassIds(),this.getAddClassIds());
		return "result>json";
	}


	public String getStudentIds() {
		return studentIds;
	}

	public void setStudentIds(String studentIds) {
		this.studentIds = studentIds;
	}

	public String getEvaluation() {
		return evaluation;
	}

	public void setEvaluation(String evaluation) {
		this.evaluation = evaluation;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public Integer getHomeworkId() {
		return homeworkId;
	}

	public void setHomeworkId(Integer homeworkId) {
		this.homeworkId = homeworkId;
	}

	public String getHomeworkIds() {
		return homeworkIds;
	}

	public void setHomeworkIds(String homeworkIds) {
		this.homeworkIds = homeworkIds;
	}

	public String getClassIds() {
		return classIds;
	}

	public void setClassIds(String classIds) {
		this.classIds = classIds;
	}

	public File getHomeworkFile() {
		return homeworkFile;
	}

	public void setHomeworkFile(File homeworkFile) {
		this.homeworkFile = homeworkFile;
	}

	public String getHomeworkName() {
		return homeworkName;
	}

	public void setHomeworkName(String homeworkName) {
		this.homeworkName = homeworkName;
	}

	public Date getDeadline() {
		return deadline;
	}

	public void setDeadline(Date deadline) {
		this.deadline = deadline;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Integer getPageNo() {
		return pageNo;
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Boolean getSearch() {
		return search;
	}

	public void setSearch(Boolean search) {
		this.search = search;
	}

	public String getHomeworkContent() {
		return homeworkContent;
	}

	public void setHomeworkContent(String homeworkContent) {
		this.homeworkContent = homeworkContent;
	}

	public Integer getWorkId() {
		return workId;
	}

	public void setWorkId(Integer workId) {
		this.workId = workId;
	}

	public Integer getCopyHomeworkId() {
		return copyHomeworkId;
	}

	public void setCopyHomeworkId(Integer copyHomeworkId) {
		this.copyHomeworkId = copyHomeworkId;
	}

	public String getDeleteClassIds() {
		return deleteClassIds;
	}

	public void setDeleteClassIds(String deleteClassIds) {
		this.deleteClassIds = deleteClassIds;
	}

	public String getAddClassIds() {
		return addClassIds;
	}

	public void setAddClassIds(String addClassIds) {
		this.addClassIds = addClassIds;
	}
	

}

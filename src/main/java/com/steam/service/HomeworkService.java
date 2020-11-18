package com.steam.service;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.framework.authority.entity.User;
import com.framework.common.SystemContext;
import com.framework.constants.SteamConstants;
import com.framework.system.common.base.service.impl.BaseServiceImpl;
import com.framework.system.common.entity.Results;
import com.framework.utils.FILEUtils;
import com.framework.utils.URLUtils;
import com.steam.entity.Homework;

@Service
public class HomeworkService extends BaseServiceImpl<Homework> {
	@Autowired
	ClassesService classesService;

	public Integer addHomework(String homeworkName, Date deadline,
			String homeworkContent, String sourceLink, Integer userId,
			String fileName) {
		Homework homework = new Homework();
		homework.setHomeworkName(homeworkName);
		homework.setCreationTime(new Date());
		homework.setHomeworkContent(homeworkContent);
		homework.setDeadline(deadline);
		if (sourceLink != null) {
			homework.setFileName(fileName);
			homework.setSourceLink(sourceLink);
		} else {
			homework.setFileName(null);
			homework.setSourceLink(null);
		}
		homework.setUserId(userId);
		this.save(homework);
		return homework.getHomeworkId();
	}

	public String queryHomeworkInClass(String classIds) {
		if (classIds == null)
			return null;
		String sql = "SELECT HOMEWORK_ID as homeworkId FROM STEAM_HOMEWORK_CLASS WHERE CLASS_ID IN( "
				+ classIds + " )";
		List<Map<String, Object>> homeworkClass = this.getResultBySQL(sql);
		int len = homeworkClass.size();
		if (len == 0)
			return null;
		if (len == 1)
			return homeworkClass.get(0).get("homeworkId").toString();
		String homeworkIds = "";
		homeworkIds += homeworkClass.get(0).get("homeworkId");
		for (int i = 1; i < len; i++) {
			homeworkIds += ",";
			homeworkIds += homeworkClass.get(i).get("homeworkId");
		}
		return homeworkIds;
	}

	public List<Map<String, Object>> queryScoreAndEvaluation(String homeworkId,
			String userId) {
		String sql = "SELECT EVALUATION as evaluation, SCORE as score FROM STEAM_HOMEWORK_STUDENT WHERE HOMEWORK_ID =? AND USER_ID =?";
		List<Map<String, Object>> scoreAndEvaluation = this.getResultBySQL(sql,
				homeworkId, userId);
		int len = scoreAndEvaluation.size();
		if (len == 0)
			return null;
		return scoreAndEvaluation;
	}

	public void publishHomeworkInClass(Integer homeworkId, String classIds) {
		String[] classId = classIds.split(",");
		int len = classId.length;
		String sql = "";
		for (int i = 0; i < len; i++) {
			sql = "insert into STEAM_HOMEWORK_CLASS ( HOMEWORK_ID,CLASS_ID ) values ("
					+ homeworkId + "," + classId[i] + ")";
			this.executeSQLUpdate(sql);
		}
	}
	//上面是增加，这个是删除
	public void deleteHomeworkInClass(Integer homeworkId, String classIds){
		String[] classId = classIds.split(",");
		int len = classId.length;
		String sql = "";
		for (int i = 0; i < len; i++) {
			sql = "DELETE FROM STEAM_HOMEWORK_CLASS WHERE HOMEWORK_ID = ? AND CLASS_ID = ?";
			this.executeSQLUpdate(sql,homeworkId,classId[i]);
		}
	}

	// 暂时无效
	public List<Map<String, Object>> querySubmitedHomework(Integer homeworkId) {
		String sql = "SELECT USER_ID as userId, USER_NAME as userName,HOMEWORK_ID as homeworkId, SOURCE_LINK as sourceLink, CREATION_TIME as creationTime, USER_NAME as userName, EVALUATION as evaluation, SCORE as score FROM HOMEWORK_STUDENT_VIEW WHERE HOMEWORK_ID ="
				+ homeworkId + " ORDER BY CREATION_TIME DESC";
		return this.getResultBySQL(sql);
	}

	public Integer countStudentsByHomework(Integer homeworkId) {
		String sql = "SELECT SUM(STUDENT_NUMBER) AS studentNumber FROM HOMEWORK_TEACHER_CLASS_VIEW WHERE HOMEWORK_ID = ? GROUP BY HOMEWORK_ID";
		Object tmp = getResultBySQL(sql, homeworkId).get(0)
				.get("studentNumber");
		if (tmp == null)
			return 0;
		return Integer.valueOf(tmp.toString());
	}

	public Integer countSubmitedStudentByHomework(Integer homeworkId) {
		String sql = "SELECT COUNT(*) FROM STEAM_HOMEWORK_STUDENT WHERE HOMEWORK_ID = ? GROUP BY HOMEWORK_ID";
		if (getTotalCountBySQL(sql, homeworkId) == null)
			return 0;
		return this.getTotalCountBySQL(sql, homeworkId);
	}

	public void deleteHomework(String homeworkIds) {
		String sql = "DELETE FROM STEAM_HOMEWORK WHERE HOMEWORK_ID IN ("
				+ homeworkIds + ")";
		this.executeSQLUpdate(sql);
	}

	public void updateSubmitedHomework(String userIds, Integer homeworkId,
			String evaluation, Integer score) {
		String sql = "";
		String[] userId = userIds.split(",");
		for (String e : userId) {
			sql = "UPDATE STEAM_HOMEWORK_STUDENT SET EVALUATION = '"
					+ evaluation + "',SCORE = " + score + " WHERE USER_ID = "
					+ e + " AND HOMEWORK_ID =" + homeworkId;
			System.out.println(sql);
			this.executeSQLUpdate(sql);
		}
	}

	public void sumbitHomework(Integer userId, Integer homeworkId,
			String sourceLink, Integer workId) {
		String sql = "INSERT INTO STEAM_HOMEWORK_STUDENT (USER_ID, HOMEWORK_ID, SOURCE_LINK, COURSEWORK_ID) VALUES (?,?,?,?)";
		this.executeSQLUpdate(sql, userId, homeworkId, sourceLink, workId);
	}

	public void sumbitHomework(Integer userId, Integer homeworkId,
			Integer workId) {
		String sql = "INSERT INTO STEAM_HOMEWORK_STUDENT (USER_ID, HOMEWORK_ID, COURSEWORK_ID) VALUES (?,?,?)";
		this.executeSQLUpdate(sql, userId, homeworkId, workId);
	}

	public void updateSubmitedHomework(Integer userId, Integer homeworkId,
			Integer workId) {
		String sql = "UPDATE  STEAM_HOMEWORK_STUDENT SET  COURSEWORK_ID="
				+ workId + "WHERE USER_ID = " + userId + " AND HOMEWORK_ID = "
				+ homeworkId;
		this.executeSQLUpdate(sql);
	}

	public Boolean checkStudentHaveHoemwork(Integer studentId,
			Integer homeworkId) {
		String classes = classesService.queryClassesByStudentId(studentId);
		if (classes != null) {
			String sql = "SELECT COUNT(*) FROM HOMEWORK_TEACHER_CLASS_VIEW WHERE HOMEWORK_ID =  ? AND CLASS_ID IN  ("
					+ classes + ")";
			if (this.getTotalCountBySQL(sql, homeworkId) > 0)
				return true;
		}
		return false;
	}

	public Integer checkSubmitStudent(String homeworkId, String userId) {
		String sql = "SELECT COUNT(*) FROM STEAM_HOMEWORK_STUDENT WHERE HOMEWORK_ID =  ? AND USER_ID = ?";
		if (this.getTotalCountBySQL(sql, homeworkId, userId) > 0)
			return 1;
		return 0;
	}

	public void deleteSubmiteHomework(Integer homeworkId, Integer userId) {
		String sql = "DELETE FROM STEAM_HOMEWORK_STUDENT WHERE HOMEWORK_ID =  ? AND USER_ID = ?";
		this.executeSQLUpdate(sql, homeworkId, userId);
	}

	public String getSubmitSourceLink(Integer homeworkId, Integer userId) {
		String sql = "SELECT SOURCE_LINK as sourceLink FROM STEAM_HOMEWORK_STUDENT WHERE HOMEWORK_ID =  ? AND USER_ID = ?";
		List<Map<String, Object>> tmp = this.getResultBySQL(sql, homeworkId,
				userId);
		System.out.println(tmp);
		if (tmp.size() < 1 || tmp.get(0).get("sourceLink")==null)
			return null;
		return tmp.get(0).get("sourceLink").toString();
	}
	
	public List<Map<String, Object>> queryHomeworkByWordIdandUserId(Integer homeworkId, Integer userId){
		String hql = "select sourceLink as sourceLink, workId as workId from HomeworkStudentView where homeworkId = ? and userId = ? ";
		List<Map<String, Object>> homeworkClass = this.getResultByHQL(hql, homeworkId, userId);
		return homeworkClass;
	}
	
	public List<Map<String, Object>> updateHomeworkService(Integer homeworkId,
			String homeworkContent, String homeworkName, File homeworkFile,
			String fileName, Date deadline, String deleteClassIds,String addClassIds) {
		Results result = new Results();
		Homework homework = this.get(Homework.class,homeworkId);
		try {
			User user = SystemContext.getCurrentUser();
			String path = null;
			if (fileName != null && homeworkFile != null) {
				String uniqueFileName = new Date().getTime() + fileName;
				path = "account/" + user.getUsername() + "/homework/"
						+ uniqueFileName;
				FILEUtils.saveUploadFile(homeworkFile,
						URLUtils.generateURLforHomework(user.getUsername()),
						uniqueFileName);
				System.out.println("*****fileupload.....");
			}else if(fileName != null){
				path = homework.getSourceLink();
			}else if(fileName == null){
				String originalPath = homework.getSourceLink();
				if(originalPath != null && originalPath.length() > 0){
					FILEUtils.deleteFile(SteamConstants.ABSOLUTE_URL_WITHOUT_ACCOUNT+"/"+originalPath);
				}
			}
			String hql = "update Homework set deadline = ?,fileName = ?,sourceLink = ?,homeworkName = ?,homeworkContent = ? where homeworkId = ?";
			this.executeHQLUpdate(hql, deadline, fileName, path, homeworkName,homeworkContent, homeworkId);
			if(addClassIds != null && addClassIds.length() != 0){this.publishHomeworkInClass(homeworkId,addClassIds);}
			if(deleteClassIds != null && deleteClassIds.length() != 0){this.deleteHomeworkInClass(homeworkId,deleteClassIds);}
			result.setResultValue("修改成功");
		} catch (Exception e) {
			e.printStackTrace();
			result.setResultValue("修改失败");
		}
		return result.getResult();
	}
}

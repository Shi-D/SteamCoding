package com.steam.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.framework.authority.entity.User;
import com.framework.system.common.base.service.impl.BaseServiceImpl;
import com.framework.system.common.entity.Results;
import com.steam.entity.Parameter;

@Service
public class ParameterService extends BaseServiceImpl<Parameter>  {

	public Integer addParameterService(String parameterComment, String parameterValue,
			String parameterName, Integer parameterSequence, Integer parameterSerialNo, 
			Integer parameterTypeCode, String parameterTypeName, 
			String parameterUrl) throws Exception {
		Parameter parameter = new Parameter();
		parameter.setParameterComment(parameterComment);
		parameter.setParameterValue(parameterValue);
		parameter.setParameterName(parameterName);
		parameter.setParameterSequence(parameterSequence);
		parameter.setParameterSerialNo(parameterSerialNo);
		parameter.setParameterTypeCode(parameterTypeCode);
		parameter.setParameterTypeName(parameterTypeName);
		parameter.setParameterUrl(parameterUrl);
		this.save(parameter);
		return parameter.getId();
	}
	
	public void deleteParametersService(String parameterIds) throws Exception {
//		String hql = "delete from Parameter where id in(" + 3 + ")";
		System.out.println(parameterIds);
		String hql = "delete from Parameter where id in(" + parameterIds + ")";
		this.executeHQLUpdate(hql);
	}
	
	public List<Map<String, Object>> updateParameterService(Integer id,String parameterComment,String parameterValue,
			String parameterName, Integer parameterSequence, Integer parameterSerialNo, Integer parameterTypeCode,
			String parameterTypeName, String parameterUrl) {
		Results result = new Results();
		try {
			String sql = "UPDATE T_SYSTEM_PARAMETER SET PARAMETER_COMMENT = '" + parameterComment
					+ "', PARAMETER_DEFAULT_VALUE ='" + parameterValue
					+ "',PARAMETER_NAME = '" + parameterName
					+ "',PARAMETER_SEQUENCE = " + parameterSequence
					+ ",PARAMETER_SERIAL_NO = " + parameterSerialNo
					+ ",PARAMETER_TYPE_CODE = " + parameterTypeCode
					+ ",PARAMETER_TYPE_NAME = '" + parameterTypeName
					+ "',PARAMETER_URL = '" + parameterUrl
					+ "'  WHERE ID = " + id;
			this.executeSQLUpdate(sql);
			result.setResultValue("修改成功");
		} catch (Exception e) {
			e.printStackTrace();
			result.setResultValue("修改失败");
		}
		return result.getResult();
	}
	
	public List<Map<String, Object>> queryParameterService() {
		String sql = "SELECT ID AS parameterId, PARAMETER_NAME AS parameterName, PARAMETER_DEFAULT_VALUE AS parameterValue,"
				+ "PARAMETER_COMMENT AS parameterComment, PARAMETER_SEQUENCE AS parameterSequence FROM T_SYSTEM_PARAMETER";
		return this.getResultBySQL(sql);
	}
	
}
